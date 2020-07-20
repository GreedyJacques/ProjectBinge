package graphicalinterface;

import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class RecipeDetailPanel extends JFrame {

    private boolean newrecipe;
    private Recipe recipe;
    private ArrayList<IngredientQty> ingredientListNew;
    private ArrayList<Recipe> recipeList;
    private JTable recipeTable;

    public RecipeDetailPanel(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable) {
        super(recipe.getName());
        this.recipe = recipe;
        this.newrecipe = newrecipe;
        this.recipeList = recipeList;
        this.recipeTable = recipeTable;

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
