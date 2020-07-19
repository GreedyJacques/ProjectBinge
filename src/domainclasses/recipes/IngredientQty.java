package domainclasses.recipes;

import java.util.ArrayList;

public class IngredientQty extends Ingredient {
    int qty;

    public IngredientQty() {
        super();
        this.qty = 0;
    }

    public IngredientQty(Ingredient ingredient, int qty) {
        super(ingredient.getId(), ingredient.getName(), ingredient.getType(), ingredient.getKcal());
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotKcal() {
        return qty * kcal;
    }

    public static Object[][] toMatrix(ArrayList<IngredientQty> ingredients) {
        Object[][] out = new Object[ingredients.size()][5];

        for (int i = 0; i < ingredients.size(); ++i) {
            out[i][0] = ingredients.get(i).getId();
            out[i][1] = ingredients.get(i).getName();
            out[i][2] = ingredients.get(i).getQty() + " " + ingredients.get(i).stringType();
            out[i][3] = ingredients.get(i).getKcal();
            out[i][4] = ingredients.get(i).getType();
        }

        return out;
    }

    public static IngredientQty findIngredient(ArrayList<IngredientQty> ingredients, int id) {
        for (IngredientQty r : ingredients) {
            if (r.getId() == id)
                return r;
        }
        return new IngredientQty();
    }

    @Override
    public String toString() {
        return "IngredientQty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", kcal=" + kcal +
                "(" + getTotKcal() + ")" +
                ", qty=" + qty + stringType() +
                '}';
    }
}