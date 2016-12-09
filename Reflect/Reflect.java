package test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			V_ST_RY_ZP entity = new V_ST_RY_ZP();
			Class clazz = entity.getClass();
			PropertyDescriptor pd = new PropertyDescriptor("RYZP_DZWJNR", clazz);
			Method m = pd.getWriteMethod();
			System.out.println(m.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
