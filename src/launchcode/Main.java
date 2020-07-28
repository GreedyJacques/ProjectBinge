package launchcode;

import dataaccess.DBManager;
import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import graphicalinterface.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) {
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


    public static void saveData(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList, DBManager db, boolean close, JDialog savingDialog){
        long saveStartTime = System.nanoTime();

        for (Recipe r : recipeList) {
            try {
                db.executeUpdate("DROP TABLE IF EXISTS Recipe" + r.getId());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        try {
            db.executeUpdate("DROP TABLE RecipeList");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("DROP TABLE ShoppingList");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("DROP TABLE InventoryList");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("DROP TABLE IngredientList");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        try {
            db.executeUpdate("CREATE TABLE RecipeList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "procedure VARCHAR(131072), " +
                    "preptime INTEGER, " +
                    "cooktime INTEGER)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("CREATE TABLE ShoppingList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("CREATE TABLE InventoryList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            db.executeUpdate("CREATE TABLE IngredientList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE)");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        Formatter formatter;

        System.out.println("Saving Recipe List...(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");

        for (Recipe r : recipeList) {
            formatter = new Formatter(new StringBuilder());
            formatter.format("INSERT INTO RecipeList (id, name, procedure, preptime, cooktime) VALUES (%d ,'%s', '%s', %d, %d)",
                    r.getId(), r.getName().replace("'","''"), r.getProcedure().replace("'","''"), r.getPreptime(), r.getCooktime());
            try {
                db.executeUpdate(formatter.toString());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            try {
                db.executeUpdate("CREATE TABLE Recipe" + r.getId() + " (" +
                        "id INTEGER PRIMARY KEY, " +
                        "name VARCHAR(256), " +
                        "type INTEGER, " +
                        "kcal DOUBLE, " +
                        "qty INTEGER)");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            for (IngredientQty i : r.getIngredients()) {
                formatter = new Formatter(new StringBuilder());
                formatter.format("INSERT INTO Recipe%d (id, name, type, kcal, qty) VALUES (%d, '%s', %d, %f, %d)",
                        r.getId(), i.getId(), i.getName().replace("'","''"), i.getType(), i.getKcal(), i.getQty());
                try {
                    db.executeUpdate(formatter.toString());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
            System.out.println("Saved Recipe " + r.getId() + " Ingredients(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");
        }

        System.out.println("Saving Shopping List...(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");

        for (IngredientQty i : shoppingList) {
            formatter = new Formatter(new StringBuilder());
            formatter.format("INSERT INTO ShoppingList (id, name, type, kcal, qty) VALUES (%d, '%s', %d, %f, %d)",
                    i.getId(), i.getName().replace("'","''"), i.getType(), i.getKcal(), i.getQty());
            try {
                db.executeUpdate(formatter.toString());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("Saving Inventory List...(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");

        for (IngredientQty i : inventoryList) {
            formatter = new Formatter(new StringBuilder());
            formatter.format("INSERT INTO InventoryList (id, name, type, kcal, qty) VALUES (%d, '%s', %d, %f, %d)",
                    i.getId(), i.getName().replace("'","''"), i.getType(), i.getKcal(), i.getQty());
            try {
                db.executeUpdate(formatter.toString());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("Saving Ingredient List...(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");

        for (Ingredient i : ingredientList) {
            formatter = new Formatter(new StringBuilder());
            formatter.format("INSERT INTO IngredientList (id, name, type, kcal) VALUES (%d, '%s', %d, %f)",
                    i.getId(), i.getName().replace("'","''"), i.getType(), i.getKcal());
            try {
                db.executeUpdate(formatter.toString());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        System.out.println("Closing...(" + (System.nanoTime()-saveStartTime)/1000000 + "ms)");

        try {
            db.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        if(close)
            System.exit(0);
        else
            savingDialog.dispose();
    }
}
