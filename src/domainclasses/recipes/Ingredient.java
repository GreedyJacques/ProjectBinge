package domainclasses.recipes;

public class Ingredient {
    int id;
    String name;
    int type; //1:Liquid(mL), 2:Solid(g), 3:Unitary(pz.)
    double kcal;

    public Ingredient() {
        this.id = -1; //TODO
        this.name = "New Ingredient";
        this.type = 3;
        this.kcal= 0;
    }

    public Ingredient(String name, int type, double kcal) {
        this.id = -1; //TODO
        this.name = name;
        if (type == 1 || type == 2)
            this.type = type;
        else
            this.type = 3;
        this.kcal = kcal;
    }

    public Ingredient(int id, String name, int type, double kcal) {
        this.id = id;
        this.name = name;
        if (type == 1 || type == 2)
            this.type = type;
        else
            this.type = 3;
        this.kcal = kcal;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    /*Methods Here*/

    /*To Here*/

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", kcal=" + kcal +
                '}';
    }
}