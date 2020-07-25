package graphicalinterface;

import dataaccess.DBManager;
import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;

public class MainFrame extends JFrame implements ActionListener, ChangeListener {

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    JTabbedPane mainpanel;
    RecipePanel recipepanel;
    ShoppingPanel shoppingpanel;
    JPanel inventorypanel;

    protected DBManager db;
    protected ResultSet rsRecipeList;
    protected ResultSet rsRecipeIngredientList;
    protected ResultSet rsShoppingList;
    protected ResultSet rsInventoryList;
    protected ResultSet rsIngredientList;

    public MainFrame() throws SQLException {
        super("Project Binge");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        try {
            db = new DBManager(DBManager.JDBCDriverSQLite, DBManager.JDBCURLSQLite);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        try {
            rsRecipeList = db.executeQuery("SELECT * FROM RecipeList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE RecipeList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "procedure VARCHAR(131072), " +
                    "preptime INTEGER, " +
                    "cooktime INTEGER)");
            rsRecipeList = db.executeQuery("SELECT * FROM RecipeList");
        }

        recipeList = new ArrayList<>();
        int recipeListIndex = 0;

        while (rsRecipeList.next()) {
            recipeList.add(new Recipe(rsRecipeList.getInt(1)));
            recipeList.get(recipeListIndex).setName(rsRecipeList.getString(2));
            recipeList.get(recipeListIndex).setProcedure(rsRecipeList.getString(3));
            recipeList.get(recipeListIndex).setPreptime(rsRecipeList.getInt(4));
            recipeList.get(recipeListIndex).setCooktime(rsRecipeList.getInt(5));
            try {
                rsRecipeIngredientList = db.executeQuery("SELECT * FROM Recipe" + recipeList.get(recipeListIndex).getId());
            } catch (SQLException exception) {
                db.executeUpdate("CREATE TABLE Recipe" + recipeList.get(recipeListIndex).getId() + " (" +
                        "id INTEGER PRIMARY KEY, " +
                        "name VARCHAR(256), " +
                        "type INTEGER, " +
                        "kcal DOUBLE, " +
                        "qty INTEGER)");
                rsRecipeIngredientList = db.executeQuery("SELECT * FROM Recipe" + recipeList.get(recipeListIndex).getId());
            }
            while (rsRecipeIngredientList.next()) {
                recipeList.get(recipeListIndex).getIngredients().add(new IngredientQty(new Ingredient(rsRecipeIngredientList.getInt(1),
                        rsRecipeIngredientList.getString(2), rsRecipeIngredientList.getInt(3),
                        rsRecipeIngredientList.getDouble(4)), rsRecipeIngredientList.getInt(5)));
            }
        }

        shoppingList = new ArrayList<>();

        try {
            rsShoppingList = db.executeQuery("SELECT * FROM ShoppingList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE ShoppingList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
            rsShoppingList = db.executeQuery("SELECT * FROM ShoppingList");
        }

        while (rsShoppingList.next()) {
            shoppingList.add(new IngredientQty(new Ingredient(rsShoppingList.getInt(1), rsShoppingList.getString(2),
                    rsShoppingList.getInt(3), rsShoppingList.getDouble(4)), rsShoppingList.getInt(5)));
        }

        inventoryList = new ArrayList<>();

        try {
            rsInventoryList = db.executeQuery("SELECT * FROM InventoryList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE InventoryList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
            rsInventoryList = db.executeQuery("SELECT * FROM InventoryList");
        }

        while (rsInventoryList.next()) {
            inventoryList.add(new IngredientQty(new Ingredient(rsInventoryList.getInt(1), rsInventoryList.getString(2),
                    rsInventoryList.getInt(3), rsInventoryList.getDouble(4)), rsInventoryList.getInt(5)));
        }

        ingredientList = new ArrayList<>();

        try {
            rsIngredientList = db.executeQuery("SELECT * FROM IngredientList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE IngredientList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE)");
            rsIngredientList = db.executeQuery("SELECT * FROM IngredientList");
        }

        while (rsIngredientList.next()) {
            ingredientList.add(new Ingredient(rsIngredientList.getInt(1), rsIngredientList.getString(2),
                    rsIngredientList.getInt(3), rsIngredientList.getDouble(4)));
        }

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
        setLocation(350, 180);
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280, 720));
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("ci sono");

                for (Recipe r : recipeList) {
                    try {
                        db.executeUpdate("DROP TABLE IF EXISTS Recipe" + r.getId());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
                try {
                    db.executeUpdate("DROP TABLE RecipeList");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("DROP TABLE ShoppingList");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("DROP TABLE InventoryList");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("DROP TABLE IngredientList");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                try {
                    db.executeUpdate("CREATE TABLE RecipeList (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name VARCHAR(256), " +
                            "procedure VARCHAR(131072), " +
                            "preptime INTEGER, " +
                            "cooktime INTEGER)");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("CREATE TABLE ShoppingList (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name VARCHAR(256), " +
                            "type INTEGER, " +
                            "kcal DOUBLE, " +
                            "qty INTEGER)");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("CREATE TABLE InventoryList (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name VARCHAR(256), " +
                            "type INTEGER, " +
                            "kcal DOUBLE, " +
                            "qty INTEGER)");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                try {
                    db.executeUpdate("CREATE TABLE IngredientList (" +
                            "id INTEGER PRIMARY KEY, " +
                            "name VARCHAR(256), " +
                            "type INTEGER, " +
                            "kcal DOUBLE)");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                Formatter formatter;

                for (Recipe r : recipeList) {
                    formatter = new Formatter(new StringBuilder());
                    formatter.format("INSERT INTO RecipeList (id, name, procedure, preptime, cooktime) VALUES ('%d' ,'%s', '%s', '%d', '%d')",
                            r.getId(), r.getName(), r.getProcedure(), r.getPreptime(), r.getCooktime());
                    try {
                        db.executeUpdate(formatter.toString());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    try {
                        db.executeUpdate("CREATE TABLE Recipe" + r.getId() + " (" +
                                "id INTEGER PRIMARY KEY, " +
                                "name VARCHAR(256), " +
                                "type INTEGER, " +
                                "kcal DOUBLE, " +
                                "qty INTEGER)");
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                    for (IngredientQty i : r.getIngredients()) {
                        formatter = new Formatter(new StringBuilder());
                        formatter.format("INSERT INTO Recipe'%d' (id, name, type, kcal, qty) VALUES ('%d', '%s', '%d', '%f', '%d')",
                                r.getId(), i.getId(), i.getName(), i.getType(), i.getKcal(), i.getQty());
                        try {
                            db.executeUpdate(formatter.toString());
                        } catch (SQLException exception) {
                            exception.printStackTrace();
                        }
                    }
                }

                for (IngredientQty i : shoppingList) {
                    formatter = new Formatter(new StringBuilder());
                    formatter.format("INSERT INTO ShoppingList (id, name, type, kcal, qty) VALUES ('%d', '%s', '%d', '%f', '%d')",
                            i.getId(), i.getName(), i.getType(), i.getKcal(), i.getQty());
                    try {
                        db.executeUpdate(formatter.toString());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }

                for (IngredientQty i : inventoryList) {
                    formatter = new Formatter(new StringBuilder());
                    formatter.format("INSERT INTO InventoryList (id, name, type, kcal, qty) VALUES ('%d', '%s', '%d', '%f', '%d')",
                            i.getId(), i.getName(), i.getType(), i.getKcal(), i.getQty());
                    try {
                        db.executeUpdate(formatter.toString());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }

                for (Ingredient i : ingredientList) {
                    formatter = new Formatter(new StringBuilder());
                    formatter.format("INSERT INTO IngredientList (id, name, type, kcal) VALUES ('%d', '%s', '%d', '%f')",
                            i.getId(), i.getName(), i.getType(), i.getKcal());
                    try {
                        db.executeUpdate(formatter.toString());
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }

                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == mainpanel) {
            int tab = mainpanel.getSelectedIndex();
            if (tab == 0)
                mainpanel.setComponentAt(0, new RecipePanel(recipeList, ingredientList, shoppingList, inventoryList));
            if (tab == 1)
                mainpanel.setComponentAt(1, new ShoppingPanel(recipeList, ingredientList, shoppingList, inventoryList));
            if (tab == 2)
                mainpanel.setComponentAt(2, new InventoryPanel(recipeList, ingredientList, shoppingList, inventoryList));
        }
    }
}