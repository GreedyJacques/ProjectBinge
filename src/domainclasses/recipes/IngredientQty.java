package domainclasses.recipes;

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

    @Override
    public String toString() {
        String unit = "pz.";
        if (this.type == 1)
            unit = "mL";
        if (this.type == 2)
            unit = "g";
        return "IngredientQty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", kcal=" + kcal +
                "(" + kcal * qty + ")" +
                ", qty=" + qty + unit +
                '}';
    }
}