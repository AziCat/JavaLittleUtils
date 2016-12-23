package yan.creator.factory;

public class MailSender implements ISender {

	@Override
	public void send() {
		System.out.println("MailSender send!");

	}

}
