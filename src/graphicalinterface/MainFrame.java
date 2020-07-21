package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ActionListener, ChangeListener {

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    JTabbedPane mainpanel;
    RecipePanel recipepanel;
    ShoppingPanel shoppingpanel;
    JPanel inventorypanel;

    public MainFrame() {
        super("Project Binge");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        /*Test recipe list*/
        recipeList = new ArrayList<>();

        Recipe ar = new Recipe();
        ar.setId(25);
        ar.setName("Lasagna");
        ar.setProcedure("In realta non so come si fanno");

        Recipe br = new Recipe();
        br.setId(10);
        br.setName("Pizza");
        br.setProcedure("Questa la sa fare Laura");

        for (int i = 0; i < 1000; ++i)
            recipeList.add(new Recipe());

        recipeList.add(ar);
        recipeList.add(br);
        /*end test*/

        //Test ingredients list
        ingredientList = new ArrayList<>();

        Ingredient aing = new Ingredient();
        Ingredient bing = new Ingredient(1,"Nutella", 2, 5);
        Ingredient cing = new Ingredient(2,"Pane",2,3);
        Ingredient ding = new Ingredient(3,"Burro", 2, 7);
        Ingredient eing = new Ingredient(4,"Mele", 3,20);

        ingredientList.add(aing);
        ingredientList.add(bing);
        ingredientList.add(cing);
        ingredientList.add(ding);
        ingredientList.add(eing);

        //END Test

        //TEST Shopping
        shoppingList = new ArrayList<>();
        IngredientQty as = new IngredientQty();
        as.setQty(2);

        IngredientQty bs = new IngredientQty();
        bs.setQty(6);

        Ingredient cs = new Ingredient("Nutella", 1, 5);
        IngredientQty cs1 = new IngredientQty(cs, 300);

        shoppingList.add(as);
        shoppingList.add(bs);
        shoppingList.add(cs1);
        //END TEST

        //TEST inventory
        inventoryList = new ArrayList<>();
        IngredientQty ai = new IngredientQty();
        ai.setQty(2);

        IngredientQty bi = new IngredientQty();
        bi.setQty(6);

        Ingredient ci = new Ingredient("Nutella", 1, 5);
        IngredientQty ci1 = new IngredientQty(ci, 300);

        inventoryList.add(ai);
        inventoryList.add(bi);
        inventoryList.add(ci1);
        //END TEST

        mainpanel = new JTabbedPane();
        recipepanel = new RecipePanel(recipeList, ingredientList, shoppingList, inventoryList);

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem openFile = new JMenuItem("Apri");
        JMenuItem saveFile = new JMenuItem("Salva");

        file.add(openFile);
        file.add(saveFile);

        menuBar.add(file);

        setJMenuBar(menuBar);

        mainpanel.addTab("Ricette", recipepanel);
        mainpanel.addTab("Lista Spesa", shoppingpanel);
        mainpanel.addTab("Inventario", inventorypanel);

        mainpanel.addChangeListener(this);

        /* JFrame methods called */
        setContentPane(mainpanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(350, 180);
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==mainpanel) {
            int tab = mainpanel.getSelectedIndex();
            if (tab == 0)
                mainpanel.setComponentAt(0,new RecipePanel(recipeList, ingredientList, shoppingList, inventoryList));
            if (tab == 1)
                mainpanel.setComponentAt(1,new ShoppingPanel(recipeList, ingredientList, shoppingList, inventoryList));
            if (tab == 2)
                mainpanel.setComponentAt(2,new InventoryPanel(recipeList, ingredientList, shoppingList, inventoryList));
        }
    }
}