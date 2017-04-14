package yan.study.javassist;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class JavassistTest {
	private static IConditionCompare method ;
	public static void main(String[] args) {
		Map<String,Object> condictionMap = new HashMap<String, Object>();
		condictionMap.put("WSCODE", "123");
		test(condictionMap);
		test(condictionMap);
	}
	
	public static void test(Map<String,Object> condictionMap) {
		try {
			Date s = new Date();
			
			if(null==method){
				ClassPool pool = ClassPool.getDefault();
				CtClass cc = pool.makeClass("yan.study.javassist.Compare");
				cc.setInterfaces(new CtClass[]{pool.get("yan.study.javassist.IConditionCompare")});
				CtMethod m = new CtMethod(CtClass.booleanType, "compare", new CtClass[]{pool.get("java.util.Map")}, cc);
				m.setBody(" {if(\"123\".equals($1.get(\"WSCODE\"))){return true;};return false;} ");
				cc.addMethod(m);
				method = (IConditionCompare)cc.toClass().newInstance();
			}
			Boolean result = method.compare(condictionMap);
			Date e = new Date();
			System.out.println(result);
			System.out.println(e.getTime()-s.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
