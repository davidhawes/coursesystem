package dataaccess;

import java.sql.*;

public class MysqlDatabaseConnection {
    private static Connection con = null;

    private MysqlDatabaseConnection(){}

    public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException {
        if(con!=null){
            return con;
        } else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pwd);
            return con;
        }
    }
}
