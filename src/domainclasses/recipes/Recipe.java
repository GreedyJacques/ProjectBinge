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
        this.id = -1;
        this.name = "New Recipe";
        this.ingredients = new ArrayList<IngredientQty>();
        this.procedure = "Procedure goes here";
        this.preptime = 0;
        this.cooktime = 0;
    }

    public Recipe(int id) {
        this.id = id;
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

    public static Object[][] toMatrix(ArrayList<Recipe> recipes) {
        Object[][] out = new Object[recipes.size()][5];

        for (int i = 0; i < recipes.size(); ++i) {
            out[i][0] = recipes.get(i).getId();
            out[i][1] = recipes.get(i).getName();
            out[i][2] = 1000;
            out[i][3] = recipes.get(i).getPreptime();
            out[i][4] = recipes.get(i).getCooktime();
        }

        return out;
    }

    public static int getMaxId(ArrayList<Recipe> recipes) {
        int out = 0;

        for (Recipe r : recipes) {
            if (r.getId() > out)
                out = r.getId();
        }

        return out;
    }

    public static Recipe findRecipe(ArrayList<Recipe> recipes, int id){
        for(Recipe r : recipes){
            if(r.getId() == id)
                return r;
        }
        return new Recipe();
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