package yan.creator.factory;
/**
 * 工厂测试类
 * @author yanjunhao
 *
 */
public class FactoryTest {
	public static void main(String[] args) {
		ISender mailSender = SenderFactory.produceMail();
		mailSender.send();
	}
}
