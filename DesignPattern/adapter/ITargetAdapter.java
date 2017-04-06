package yan.creator.adapter;
/**
 * 适配器接口
 * @author yanjunhao
 *
 */
public interface ITargetAdapter {
	/**
	 * 目标类原方法
	 */
	public void sourceMethod();
	/**
	 * 扩展方法
	 */
	public void extendMethod();
}
