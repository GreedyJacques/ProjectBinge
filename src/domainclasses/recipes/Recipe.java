package domainclasses.recipes;

import java.util.ArrayList;

public class Recipe {
    int id;
    String name;
    ArrayList<IngredientQty> ingredients;
    String procedure;
    int preptime;
    int cooktime;

    public Recipe() {
        this.id = -1; //TODO
        this.name = "New Recipe";
        this.ingredients = new ArrayList<IngredientQty>();
        this.procedure = "Procedure goes here";
        this.preptime = 0;
        this.cooktime = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<IngredientQty> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<IngredientQty> ingredients) {
        this.ingredients = ingredients;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public int getPreptime() {
        return preptime;
    }

    public void setPreptime(int preptime) {
        this.preptime = preptime;
    }

    public int getCooktime() {
        return cooktime;
    }

    public void setCooktime(int cooktime) {
        this.cooktime = cooktime;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients=" + ingredients.size() +
                ", preptime=" + preptime +
                ", cooktime=" + cooktime +
                '}';
    }
}