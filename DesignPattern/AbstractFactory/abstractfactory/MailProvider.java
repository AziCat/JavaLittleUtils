package yan.creator.abstractfactory;

public class MailProvider implements IProvider{
	@Override
	public ISender produce() {
		// TODO Auto-generated method stub
		return new MailSender();
	}
}
