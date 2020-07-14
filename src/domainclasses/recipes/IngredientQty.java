package domainclasses.recipes;

public class IngredientQty extends Ingredient {
    int qty;

    public IngredientQty() {
        super();
        this.qty = 0;
    }

    public IngredientQty(Ingredient ingredient, int qty) {
        super(ingredient.getId(), ingredient.getName(), ingredient.getType());
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
        return "IngredientQty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", qty=" + qty +
                '}';
    }
}