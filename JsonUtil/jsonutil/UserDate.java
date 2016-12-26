package yan.study.jsonutil;

import java.util.Date;

/**
 * 测试bean
 * @author yanjunhao
 *
 */
public class UserDate {
	private String _name;
    private String email;
    private boolean isDeveloper;
    private int age;
    private Date registerDate;
    
    
	public UserDate(String _name, String email, boolean isDeveloper, int age, Date registerDate) {
		super();
		this._name = _name;
		this.email = email;
		this.isDeveloper = isDeveloper;
		this.age = age;
		this.registerDate = registerDate;
	}
	public String get_name() {
		return _name;
	}
	public void set_name(String _name) {
		this._name = _name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isDeveloper() {
		return isDeveloper;
	}
	public void setDeveloper(boolean isDeveloper) {
		this.isDeveloper = isDeveloper;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
    
    
}
