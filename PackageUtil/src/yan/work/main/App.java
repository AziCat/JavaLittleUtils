package yan.work.main;

import yan.work.utils.FileUtil;

public class App {
	public static void main(String[] args) {
		for(String path:FileUtil.getPackageFileList()){
			try {
				FileUtil.createFile(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
