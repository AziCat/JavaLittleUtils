package test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Test {

	/**
	 * �ж��ļ��ı����ʽ
	 * 
	 * @param fileName
	 *            :file
	 * @return �ļ������ʽ
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
		String dirPath = "D:\\code\\1����������\\�㶫����\\�㶫���첹������\\db\\xz\\10initdata_2016";
		File dir = new File(dirPath);
		System.out.println("�ܹ���" + dir.list().length + "���ļ�");

		if (dir.exists() && dir.list() != null && dir.list().length > 0) {
			for (String filename : dir.list()) {
				System.out.println("�ļ�����" + filename + "�������ʽΪ��"
						+ codeString(dirPath + "\\" + filename));
			}
		}
	}
}