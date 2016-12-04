package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Test {

	/**
	 * 判断文件的编码格式
	 * 
	 * @param fileName
	 *            :file
	 * @return 文件编码格式
	 * @throws Exception
	 */
	public static String codeString(String fileName) {
		String code = null;

		try {
			BufferedInputStream bin = new BufferedInputStream(
					new FileInputStream(fileName));
			int p = (bin.read() << 8) + bin.read();

			switch (p) {
			case 0xefbb:
				code = "UTF-8";
				break;
			case 0xfffe:
				code = "Unicode";
				break;
			case 0xfeff:
				code = "UTF-16BE";
				break;
			default:
				code = "GBK";
			}

			bin.close();

		} catch (Exception e) {
			e.printStackTrace();
			code = "exception";
		}

		return code;
	}

	public static void main(String[] args) {
		String dirPath = "D:\\code\\1、工作代码\\广东刑侦\\广东刑侦补采条线\\db\\xz\\10initdata_2016";
		File dir = new File(dirPath);
		System.out.println("总共有" + dir.list().length + "个文件");

		if (dir.exists() && dir.list() != null && dir.list().length > 0) {
			for (String filename : dir.list()) {
				System.out.println("文件名：" + filename + "，编码格式为："
						+ codeString(dirPath + "\\" + filename));
			}
		}
	}
}