package launchcode;

import domainclasses.recipes.*;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello World!");
        System.out.println("Git vim test");
        System.out.println("ciao");

        Ingredient a = new Ingredient();
        Ingredient b = new Ingredient("Nutella",1,500);

        IngredientQty c = new IngredientQty();
        IngredientQty d = new IngredientQty(b,500);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
