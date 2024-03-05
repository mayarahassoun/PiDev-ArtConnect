package esprit_pidev.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    final String URL = "jdbc:mysql://localhost:3306/projet";
    final String USER = "root";
    final  String pwd = "";
    private Connection cnx ;
    private  static MyDB instance;
    public MyDB(){
        try {
            cnx = DriverManager.getConnection(URL, USER, pwd);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public static MyDB getInstance(){
        if (instance== null){
            instance = new MyDB();
        }return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
