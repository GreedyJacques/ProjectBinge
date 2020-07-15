package launchcode;

import domainclasses.recipes.*;
import graphicalinterface.*;

public class Main {
    public static void main(String[] args){

        Ingredient a = new Ingredient();
        Ingredient b = new Ingredient("Nutella",2,5);

        IngredientQty c = new IngredientQty();
        IngredientQty d = new IngredientQty(b,500);

        Recipe e = new Recipe();

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(e);

        new MainFrame();
    }


}
