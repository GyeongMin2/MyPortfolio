package net.haebup.utils.DatabaseUtil;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnPool {
	public Connection con;

	// 연결
    public static Connection getConnection() throws SQLException {
        try {
			Context initCtx = new InitialContext();
			Context ctx = (Context) initCtx.lookup("java:comp/env");
			DataSource ds = (DataSource) ctx.lookup("jdbc_haebup");
            return ds.getConnection();
        } catch (NamingException e) {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }
    //쓰지마샘 ㅅㄱ 
//    public  static Connection getDirectConnection() throws SQLException{
//        String url = "jdbc:mysql://localhost:3306/"; // DB URL
//        String username = "root"; // DB 사용자명
//        String password = "1234"; // DB 비밀번호
//
//        try {
//            return java.sql.DriverManager.getConnection(url, username, password);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw new SQLException(e);
//        }
//    }
}
