package yan.creator.singleton;
/**
 * 单例模式
 * @author yanjunhao
 *
 */
public class Singleton {
	//唯一实例
	private static Singleton instance = null;
	
	//私有构造方法
	private Singleton(){
		
	}
	
	/**
	 * 获取实例
	 * @return
	 */
	public static Singleton getInstance(){
		//懒加载，如果唯一实例为空，才去创建
		if(null==instance){
			synchronized(Singleton.class){//加锁
				if(null==instance){
					instance = new Singleton();
				}
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println(Singleton.getInstance());
		System.out.println(Singleton.getInstance());
	}
	
}
