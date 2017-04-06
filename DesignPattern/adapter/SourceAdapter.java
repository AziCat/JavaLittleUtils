package yan.creator.adapter;

public class SourceAdapter extends Source implements ITargetAdapter {

	@Override
	public void sourceMethod() {
		//直接调用父类的原方法
		super.sourceMethod();
	}

	@Override
	public void extendMethod() {
		System.out.println("调用适配器的扩展方法");
	}

}
