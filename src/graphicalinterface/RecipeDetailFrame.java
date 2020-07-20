package graphicalinterface;

import domainclasses.recipes.Recipe;
import javax.swing.*;
import java.util.ArrayList;

public class RecipeDetailFrame extends JFrame {
    public RecipeDetailFrame(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable) {
        super(recipe.getName());

        /* JFrame methods called */
        if (newrecipe)
            setContentPane(new RecipeDetailPanelModifiable(recipe, true, recipeList, recipeTable, this));
        else
            setContentPane(new RecipeDetailPanelUnmodifiable(recipe, recipeList, recipeTable, this));
        setLocation(400, 230);
        setSize(1000, 600);
        setVisible(true);
    }
}
