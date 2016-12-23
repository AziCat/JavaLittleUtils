package yan.creator.factory;
/**
 * sender工厂
 * @author yanjunhao
 *
 */
public class SenderFactory {
	public static ISender produceMail(){
		return new MailSender();
	}
	public static ISender produceSms(){
		return new SmsSender();
	}
}
