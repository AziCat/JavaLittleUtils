package yan.creator.adapter1;
/**
 * 对象适配器
 * @author yanjunhao
 *
 */
public class SourceAdapter  implements ITargetAdapter {
	//持有目标类对象实例
	private Source source;
	//构造函数
	public SourceAdapter(Source source){
		this.source = source;
	}
	@Override
	public void sourceMethod() {
		//直接调用父类的原方法
		this.source.sourceMethod();
	}

	@Override
	public void extendMethod() {
		System.out.println("调用适配器的扩展方法");
	}

}
