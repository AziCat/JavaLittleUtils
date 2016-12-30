package main.java.yan.working.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * jdbc工具类
 * 
 * @author yanjunhao
 * @date 2016年6月15日, AM 11:06:30
 * 
 */
public class JDBCUtil {
	private static Connection conn = null;

	@SuppressWarnings("static-access")
	public static Connection getConntion() {
		try {
			if (null == conn || conn.isClosed()) {
				ConfigUtil config = new ConfigUtil();
				Class.forName(config.driverName);
				conn = DriverManager.getConnection(config.url, config.userName,config.password);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return conn;
	}
	public static void main(String[] args) {
		System.out.println(getConntion());
	}
	/**
	 * 关闭资源
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(PreparedStatement pst) {
		if (null != pst) {
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void close(ResultSet rs,PreparedStatement pst,Connection conn) {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
