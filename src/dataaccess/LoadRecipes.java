package dataaccess;
import java.lang.*;
import  java.sql.*;

public class LoadRecipes {


    public LoadRecipes(){
        try {
            Class boh = Class.forName("org.sqlite.jdbc");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Connection c = null;
        try {
            c = DriverManager.getConnection("jdbc:sqlite:Ricette.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        try {
            Statement statement = c.createStatement();

            statement.executeUpdate("CREATE TABLE Ricette");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

}
