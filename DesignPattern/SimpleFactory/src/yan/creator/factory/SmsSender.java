package yan.creator.factory;

public class SmsSender implements ISender {

	@Override
	public void send() {
		System.out.println("SmsSender send!");

	}

}
