package org.wso2.repository.device.util;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DB {

    public static Connection getConnection() throws Exception{
        /*InitialContext context = new InitialContext();
        String strDataSource = getProperty("datasource.name");
        DataSource dataSource = (DataSource) context.lookup(strDataSource);
        return dataSource.getConnection();*/

        Class.forName("com.mysql.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/UCSC", "root", "");


return  con;

    }


    private static String getProperty(String value) {
        String dataSource = null;
        try {
            Properties properties = new Properties();
            properties.load(DB.class
                    .getResourceAsStream("/datasource.properties"));
            dataSource = properties.getProperty(value);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}