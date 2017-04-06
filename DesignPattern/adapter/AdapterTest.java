package yan.creator.adapter;
/**
 * 适配器测试类
 * @author yanjunhao
 *
 */
public class AdapterTest {
	public static void main(String[] args) {
		ITargetAdapter target = new SourceAdapter();
		target.sourceMethod();//目标类原方法
		target.extendMethod();//适配器的扩展方法
	}
}
