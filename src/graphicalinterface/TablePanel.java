package graphicalinterface;

import domainclasses.recipes.IngredientQty;

import java.util.ArrayList;

public interface TablePanel {
    public void redrawTable(ArrayList<IngredientQty> ingredientQtyList);
}
