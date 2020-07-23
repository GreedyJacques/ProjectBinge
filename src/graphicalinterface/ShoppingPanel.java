package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ShoppingPanel extends JPanel implements ActionListener, KeyListener {
    private JButton addButton, removeButton,
            exportButton, addFromRecipeButton, searchButton;

    DefaultTableModel shoppingModel;
    JTable shoppingTable;

    private JTextField searchBar;

    String selectedQty;
    Object selectedIngredient;

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    public ShoppingPanel(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList) {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]", "50[]20[grow, fill][]150[][][][]50"));
        this.recipeList = recipeList;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;

        searchBar = new JTextField("");

        searchButton = new JButton("CERCA");
        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        exportButton = new JButton("ESPORTA");
        addFromRecipeButton = new JButton("AGGIUNGI DA RICETTA");

        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        exportButton.addActionListener(this);
        addFromRecipeButton.addActionListener(this);
        searchBar.addKeyListener(this);

        Object[][] shoppingMatrix = IngredientQty.toMatrix(shoppingList);

        shoppingModel = new DefaultTableModel(shoppingMatrix, new String[]{"ID", "Nome", "Qty"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1)
                    return false;
                else
                    return true;
            }
        };

        shoppingTable = new JTable(shoppingModel);
        JScrollPane scrollPanel = new JScrollPane(shoppingTable);

        shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Spesa"));

        ListSelectionModel selectionModel = shoppingTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!selectionModel.isSelectionEmpty()) {
                    int row = shoppingTable.getSelectedRow();
                    selectedIngredient = shoppingModel.getValueAt(row, 1);
                    selectedQty = (String) shoppingModel.getValueAt(row, 2);
                }
            }
        });

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        exportButton.setPreferredSize(new Dimension(175, 50));
        addFromRecipeButton.setPreferredSize(new Dimension(175, 50));
        searchButton.setPreferredSize(new Dimension(75, 30));

        add(new JLabel("Cerca:"), "right");
        add(searchBar, "");
        add(searchButton, "left");
        add(scrollPanel, "span 2 6, grow");
        add(new JLabel(""));
        add(new JLabel(""));
        add(addButton, "right");
        add(addFromRecipeButton, "right");
        add(removeButton, "right");
        add(exportButton, "right");

        shoppingTable.setAutoCreateRowSorter(true);
    }

    static ArrayList<IngredientQty> findSearchedIngredientsQty(String searchedThing, ArrayList<IngredientQty> filteredIngredientsList) {
        ArrayList<IngredientQty> out = new ArrayList<>();
        for (IngredientQty r : filteredIngredientsList) {
            if (r.getName().toLowerCase().contains(searchedThing.toLowerCase()))
                out.add(r);
        }
        return out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            new ExistingIngredientPanel(shoppingList, shoppingTable, ingredientList);
        }
        if (e.getSource() == removeButton) {
            if (selectedIngredient != null) {
                int row = shoppingTable.getSelectedRow();
                int selectedId = (int) shoppingTable.getValueAt(row, 0);
                shoppingList.remove(IngredientQty.findIngredientQty(shoppingList, selectedId));
                shoppingModel.removeRow(row);
                selectedIngredient = null;
            }
        }
        if (e.getSource() == exportButton) {

        }
        if (e.getSource() == addFromRecipeButton) {

        }
        if (e.getSource() == searchButton) {

            String searchedThing = searchBar.getText();
            ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(findSearchedIngredientsQty(searchedThing, shoppingList));

            Object[][] searchedIngredientsMatrix = IngredientQty.toMatrix(searchedIngredientsList);

            DefaultTableModel searchedIngredientsModel = new DefaultTableModel(searchedIngredientsMatrix, new String[]{"ID", "Nome", "Qty"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            shoppingTable.setModel(searchedIngredientsModel);


            shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(750);
            shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(750);


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
                ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(findSearchedIngredientsQty(searchedThing, shoppingList));

                Object[][] searchedIngredientsMatrix = IngredientQty.toMatrix(searchedIngredientsList);

                DefaultTableModel searchedIngredientsModel = new DefaultTableModel(searchedIngredientsMatrix, new String[]{"ID", "Nome", "Qty"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                shoppingTable.setModel(searchedIngredientsModel);


                shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(750);
                shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(750);

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
