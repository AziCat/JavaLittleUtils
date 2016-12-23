package yan.creator.abstractfactory;
/**
 * 提供者接口
 * @author yanjunhao
 *
 */
public interface IProvider {
	/**
	 * 构建sender
	 * @return
	 */
	public ISender produce();
}
