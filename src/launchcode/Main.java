package launchcode;

import domainclasses.recipes.*;
import graphicalinterface.*;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        Ingredient a = new Ingredient();
        Ingredient b = new Ingredient("Nutella", 1, 5);


        IngredientQty c = new IngredientQty();
        IngredientQty d = new IngredientQty(b, 500);

        Recipe e = new Recipe();

        String s = "200 grammi";

        int i = strtoint(s);

        System.out.println(i);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);


        try {
        new MainFrame();}
        catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    public static int strtoint(String s) {
        int out = 0;
        int i = 0;
        char c = s.charAt(i);
        while (c >= '0' && c <= '9') {
            out *= 10;
            out += c - '0';
            i++;
            if (i == s.length())
                break;
            else
                c = s.charAt(i);
        }
        return out;
    }

}
