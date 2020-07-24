package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailFrame extends JFrame {
    public RecipeDetailFrame(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList) {
        super(recipe.getName());
        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
        /* JFrame methods called */
        if (newrecipe)
            setContentPane(new RecipeDetailPanelModifiable(recipe, true, recipeList, recipeTable, this, ingredientList, shoppingList));
        else
            setContentPane(new RecipeDetailPanelUnmodifiable(recipe, recipeList, recipeTable, this, ingredientList, shoppingList));
        setLocation(400, 230);
        setSize(1000, 600);
        setVisible(true);
    }
}
