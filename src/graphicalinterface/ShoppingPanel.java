package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import launchcode.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ShoppingPanel extends JPanel implements ActionListener, KeyListener, TablePanel {
    private JButton addButton, removeButton,
            exportButton, addFromRecipeButton, searchButton;

    DefaultTableModel shoppingModel;
    JTable shoppingTable;

    private JTextField searchBar;

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

        shoppingTable = new JTable();
        JScrollPane scrollPanel = new JScrollPane(shoppingTable);
        redrawTable(shoppingList);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Spesa"));

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

    static String getShoppingIngredient(ArrayList<IngredientQty> shoppingList) {
        String out = new String();
        for (IngredientQty i : shoppingList) {
            out += i.getQty() + i.stringType() + " " + i.getName() + '\n';

        }
        return out;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            new ExistingIngredientPanel(shoppingList, this, ingredientList);
        }
        if (e.getSource() == removeButton) {
            if (shoppingTable.getSelectedRow()>= 0 && shoppingTable.getSelectedRow()<shoppingTable.getRowCount()) {
                int row = shoppingTable.getSelectedRow();
                int selectedId = (int) shoppingTable.getValueAt(row, 0);
                shoppingList.remove(IngredientQty.findIngredientQty(shoppingList, selectedId));
                redrawTable(shoppingList);
                shoppingTable.clearSelection();
            }
        }
        if (e.getSource() == exportButton) {
            new ExportFrame(shoppingList);


        }
        if (e.getSource() == addFromRecipeButton) {
            new AddFromRecipeFrame(recipeList, shoppingList, ingredientList, this, -1, 1);

        }
        if (e.getSource() == searchButton) {

            String searchedThing = searchBar.getText();
            ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(IngredientQty.findSearchedIngredientQty(searchedThing, shoppingList));

            redrawTable(searchedIngredientsList);
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
                ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(IngredientQty.findSearchedIngredientQty(searchedThing, shoppingList));

                redrawTable(searchedIngredientsList);

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void redrawTable(ArrayList<IngredientQty> ingredientQtyList) {
        Object[][] ingredientsMatrix = IngredientQty.toMatrix(ingredientQtyList);

        DefaultTableModel ingredientsModel = new DefaultTableModel(ingredientsMatrix, new String[]{"ID", "Nome", "Qty"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1)
                    return false;
                else
                    return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (ingredientQtyList.isEmpty()) {
                    return Object.class;
                }
                return getValueAt(0, columnIndex).getClass();
            }
        };

        shoppingTable.setModel(ingredientsModel);

        shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        ingredientsModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (shoppingTable.getSelectedRow() >= 0 && shoppingTable.getSelectedRow() < shoppingTable.getRowCount()) {
                    int selectedRow = shoppingTable.getSelectedRow();
                    shoppingTable.clearSelection();
                    String newQty = (String) shoppingTable.getValueAt(selectedRow, 2);
                    int Qty = Main.strtoint(newQty);
                    IngredientQty tmpIngredientQty = IngredientQty.findIngredientQty(ingredientQtyList, (int) shoppingTable.getValueAt(selectedRow, 0));
                    if (Qty > 0)
                        tmpIngredientQty.setQty(Qty);
                    shoppingTable.setValueAt(tmpIngredientQty.getQty() + " " + tmpIngredientQty.stringType(), selectedRow, 2);
                }
            }
        });
    }
}
