package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import launchcode.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

public class RecipePanel extends JPanel implements ActionListener, KeyListener {

    private JButton addButton, removeButton,
            openButton, filterButton, searchButton;

    private JTextField searchBar;

    JTable recipeTable;
    DefaultTableModel recipeModel;

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;
    ShoppingPanel shoppingPanel;

    JCheckBox timeFilterBox;
    JCheckBox kcalFilterBox;
    JCheckBox ingredientsFilterBox;
    JTextField timeFilterField;
    JTextField kcalFilterField;
    JTextField ingredientsFilterField;


    public RecipePanel(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList, ShoppingPanel shoppingPanel) {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[175]", "50[][][grow,fill][][][]50"));
        this.recipeList = recipeList;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;
        this.shoppingPanel = shoppingPanel;

        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        openButton = new JButton("APRI");
        filterButton = new JButton("RESET");
        searchButton = new JButton("CERCA");

        searchBar = new JTextField("");

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        openButton.setPreferredSize(new Dimension(175, 50));
        filterButton.setPreferredSize(new Dimension(75, 30));
        searchButton.setPreferredSize(new Dimension(75, 30));

        removeButton.addActionListener(this);
        openButton.addActionListener(this);
        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        filterButton.addActionListener(this);
        searchBar.addKeyListener(this);

        recipeTable = new JTable();
        JScrollPane scrollPanel = new JScrollPane(recipeTable);

        redrawTable(recipeList);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Ricette"));

        JPanel filterPanel = new JPanel(new MigLayout("wrap 1", "0[30]0", "[][]15[][]15[][]"));
        filterPanel.setBorder(new TitledBorder("Filtri"));
        timeFilterBox = new JCheckBox("Tempo totale massimo:");
        kcalFilterBox = new JCheckBox("KCal per porzione massime:");
        ingredientsFilterBox = new JCheckBox("Ingredienti mancanti massimo:");
        timeFilterField = new JTextField();
        timeFilterField.setMaximumSize(new Dimension(35, 20));
        kcalFilterField = new JTextField();
        kcalFilterField.setMaximumSize(new Dimension(35, 20));
        ingredientsFilterField = new JTextField();
        ingredientsFilterField.setMaximumSize(new Dimension(35, 20));

        filterPanel.add(timeFilterBox);
        filterPanel.add(timeFilterField, "grow");
        filterPanel.add(kcalFilterBox);
        filterPanel.add(kcalFilterField, "grow");
        filterPanel.add(ingredientsFilterBox);
        filterPanel.add(ingredientsFilterField, "grow");

        add(new JLabel("Cerca:"), "right");
        add(searchBar, "");
        add(searchButton, "left");
        add(scrollPanel, "span 2 6, grow");
        add(filterButton, "left");
        add(filterPanel, "grow");
        add(addButton, "right");
        add(removeButton, "right");
        add(openButton, "right");

        recipeTable.setAutoCreateRowSorter(true);
    }

    static ArrayList<Recipe> findSearchedRecipes(String searchedThing, ArrayList<Recipe> filteredRecipeList, RecipePanel recipePanel) {
        ArrayList<Recipe> out = new ArrayList<>();
        for (Recipe r : filteredRecipeList) {
            if (r.getName().toLowerCase().contains(searchedThing.toLowerCase()))
                out.add(r);
        }

        Recipe r;
        Boolean remove = false;
        int time = 0, kcal = 0, ingredients = 0;

        for (Iterator<Recipe> iteratorR = out.iterator(); iteratorR.hasNext(); ) {
            r = iteratorR.next();

            if (recipePanel.timeFilterBox.isSelected()) {
                time = Main.strtoint(recipePanel.timeFilterField.getText());
                if (time <= 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci un tempo in minuti valido.");
                    return filteredRecipeList;
                }
                if (r.getCooktime() + r.getPreptime() > time)
                    remove = true;
            }

            if (recipePanel.kcalFilterBox.isSelected()) {

                kcal = Main.strtoint(recipePanel.kcalFilterField.getText());
                if (kcal <= 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci un numero di calorie valido.");
                    return filteredRecipeList;
                }
                if (r.getKcal() > kcal)
                    remove = true;
            }

            if (recipePanel.ingredientsFilterBox.isSelected()) {
                try {
                    ingredients = Main.strtoint(recipePanel.ingredientsFilterField.getText());
                } catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "Inserisci un numero di ingredienti valido.");
                }
                for (IngredientQty i : r.getIngredients()) {
                    if (!IngredientQty.hasIngredient(recipePanel.inventoryList, i)) {
                        --ingredients;
                        if (ingredients < 0) {
                            remove = true;
                            break;
                        }
                    }
                }
            }


            if (remove) {
                iteratorR.remove();
            }

            remove = false;
        }

        return out;
    }

    public void redrawTable(ArrayList<Recipe> newRecipeList) {
        Object[][] recipeMatrix = Recipe.toMatrix(newRecipeList);

        recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"ID", "Nome", "kCal/porz.", "T. Preparazione", "T. Cottura", "T. Totale"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (newRecipeList.isEmpty()) {
                    return Object.class;
                }
                return getValueAt(0, columnIndex).getClass();
            }
        };

        recipeTable.setModel(recipeModel);

        recipeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        recipeTable.getColumnModel().getColumn(1).setPreferredWidth(1500);
        recipeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        recipeTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        recipeTable.getColumnModel().getColumn(4).setPreferredWidth(200);
        recipeTable.getColumnModel().getColumn(5).setPreferredWidth(200);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String searchedThing = searchBar.getText();
            ArrayList<Recipe> searchedRecipeList = new ArrayList<>(findSearchedRecipes(searchedThing, recipeList, this));

            redrawTable(searchedRecipeList);
        }

        if (e.getSource() == filterButton) {
            redrawTable(recipeList);
        }

        if (e.getSource() == addButton) {
            Recipe newRecipe = new Recipe(Recipe.getMaxId(recipeList) + 1);
            new RecipeDetailFrame(newRecipe, true, recipeList, recipeTable, ingredientList, shoppingList, shoppingPanel);
        }

        if (e.getSource() == removeButton) {
            if (recipeTable.getSelectedRow() >= 0 && recipeTable.getSelectedRow() < recipeTable.getRowCount()) {
                int row = recipeTable.getSelectedRow();
                Object selectedId = recipeTable.getValueAt(row, 0);
                recipeList.remove(Recipe.findRecipe(recipeList, (int) selectedId));
                redrawTable(recipeList);
                recipeTable.clearSelection();
            }
        }

        if (e.getSource() == openButton) {
            if (recipeTable.getSelectedRow() >= 0 && recipeTable.getSelectedRow() < recipeTable.getRowCount())
                new RecipeDetailFrame(Recipe.findRecipe(recipeList, (int) recipeTable.getValueAt(recipeTable.getSelectedRow(), 0)), false, recipeList, recipeTable, ingredientList, shoppingList, shoppingPanel);
            else
                return;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == searchBar) {
            String searchedThing = searchBar.getText();

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                ArrayList<Recipe> searchedRecipeList = new ArrayList<>(findSearchedRecipes(searchedThing, recipeList, this));

                redrawTable(searchedRecipeList);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
