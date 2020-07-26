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

import static launchcode.Main.saveData;

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
    protected ResultSet rs;

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

        JDialog loadingDialog = new JDialog();
        loadingDialog.setVisible(true);
        loadingDialog.setLocation(800, 400);
        loadingDialog.setAlwaysOnTop(true);
        loadingDialog.setSize(250, 100);
        JLabel loadingLabel = new JLabel("Caricamento dei Dati...");
        loadingLabel.setHorizontalAlignment(JLabel.CENTER);
        loadingLabel.setVerticalAlignment(JLabel.CENTER);
        loadingDialog.add(loadingLabel);


        long loadStartTime = System.nanoTime();

        System.out.println("Loading Recipe List...(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");

        try {
            rs = db.executeQuery("SELECT * FROM RecipeList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE RecipeList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "procedure VARCHAR(131072), " +
                    "preptime INTEGER, " +
                    "cooktime INTEGER)");
            rs = db.executeQuery("SELECT * FROM RecipeList");
        }

        recipeList = new ArrayList<>();

        while (rs.next()) {
            recipeList.add(new Recipe(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4), rs.getInt(5)));

        }

        System.out.println("Loading Recipe Ingredients...(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");

        for (Recipe r : recipeList) {
            try {
                rs = db.executeQuery("SELECT * FROM Recipe" + r.getId());
            } catch (SQLException exception) {
                db.executeUpdate("CREATE TABLE Recipe" + r.getId() + " (" +
                        "id INTEGER PRIMARY KEY, " +
                        "name VARCHAR(256), " +
                        "type INTEGER, " +
                        "kcal DOUBLE, " +
                        "qty INTEGER)");
                rs = db.executeQuery("SELECT * FROM Recipe" + r.getId());
            }
            while (rs.next()) {
                r.getIngredients().add(new IngredientQty(new Ingredient(rs.getInt(1),
                        rs.getString(2), rs.getInt(3),
                        rs.getDouble(4)), rs.getInt(5)));
            }
            System.out.println("Loaded Recipe " + r.getId() + " Ingredients(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");
        }

        System.out.println("Loading Shopping List...(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");

        try {
            rs = db.executeQuery("SELECT * FROM ShoppingList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE ShoppingList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
            rs = db.executeQuery("SELECT * FROM ShoppingList");
        }

        shoppingList = new ArrayList<>();

        while (rs.next()) {
            shoppingList.add(new IngredientQty(new Ingredient(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getDouble(4)), rs.getInt(5)));
        }

        System.out.println("Loading Inventory List...(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");

        try {
            rs = db.executeQuery("SELECT * FROM InventoryList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE InventoryList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE, " +
                    "qty INTEGER)");
            rs = db.executeQuery("SELECT * FROM InventoryList");
        }

        inventoryList = new ArrayList<>();

        while (rs.next()) {
            inventoryList.add(new IngredientQty(new Ingredient(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getDouble(4)), rs.getInt(5)));
        }

        System.out.println("Loading Ingredient List...(" + (System.nanoTime() - loadStartTime) / 1000000 + "ms)");

        try {
            rs = db.executeQuery("SELECT * FROM IngredientList");
        } catch (SQLException exception) {
            db.executeUpdate("CREATE TABLE IngredientList (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name VARCHAR(256), " +
                    "type INTEGER, " +
                    "kcal DOUBLE)");
            rs = db.executeQuery("SELECT * FROM IngredientList");
        }

        ingredientList = new ArrayList<>();

        while (rs.next()) {
            ingredientList.add(new Ingredient(rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getDouble(4)));
        }

        loadingDialog.dispose();

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

                JDialog savingDialog = new JDialog();
                savingDialog.setLocation(800, 400);
                savingDialog.setAlwaysOnTop(true);
                savingDialog.setSize(250, 100);
                JLabel savingLabel = new JLabel("Salvataggio dei Dati...");
                savingLabel.setHorizontalAlignment(JLabel.CENTER);
                savingLabel.setVerticalAlignment(JLabel.CENTER);
                savingDialog.add(savingLabel);
                savingDialog.setVisible(true);

                Runnable saveRunnableClose = new Runnable() {
                    @Override
                    public void run() {
                        saveData(recipeList, ingredientList, shoppingList, inventoryList, db, true);
                    }
                };

                SwingUtilities.invokeLater(saveRunnableClose);
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