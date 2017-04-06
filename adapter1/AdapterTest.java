package yan.creator.adapter1;
/**
 * 适配器测试类
 * @author yanjunhao
 *
 */
public class AdapterTest {
	public static void main(String[] args) {
		Source source = new Source();
		ITargetAdapter target = new SourceAdapter(source);
		target.sourceMethod();//目标类原方法
		target.extendMethod();//适配器的扩展方法
	}
}
