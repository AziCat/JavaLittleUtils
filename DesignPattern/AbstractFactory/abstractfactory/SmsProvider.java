package yan.creator.abstractfactory;

public class SmsProvider implements IProvider{
	@Override
	public ISender produce() {
		// TODO Auto-generated method stub
		return new SmsSender();
	}
}
