package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import javax.swing.*;
import java.util.ArrayList;

public class RecipeDetailFrame extends JFrame {
    public RecipeDetailFrame(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList) {
        super(recipe.getName());

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
