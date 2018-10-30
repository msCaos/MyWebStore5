package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class C3P0Utils {
    private static DataSource dataSource ;

    static {
        dataSource = new ComboPooledDataSource("mysql");
    }

    public static Connection getConnection(){

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    public static DataSource getDataSource(){
        return dataSource ;
    }

    public static void realse(ResultSet resSet, PreparedStatement pst, Connection conn){
        if (resSet!=null){
            try {
                resSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pst!=null){
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                DbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
