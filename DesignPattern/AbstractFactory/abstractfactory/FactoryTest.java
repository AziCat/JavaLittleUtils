package yan.creator.abstractfactory;
/**
 * 工厂测试类
 * @author yanjunhao
 *
 */
public class FactoryTest {
	public static void main(String[] args) {
		IProvider smsProvider = new SmsProvider();
		ISender smsSender = smsProvider.produce();
		smsSender.send();
	}
}
