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

public class InventoryPanel extends JPanel implements ActionListener, KeyListener, TablePanel {

    private JButton addButton, removeButton, importButton, searchButton;

    JTable inventoryTable;

    private JScrollPane scrollPanel;
    private JTextField searchBar;

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    public InventoryPanel(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList) {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]", "50[]20[grow, fill][][][][]50"));
        this.recipeList = recipeList;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;

        searchBar = new JTextField("");

        searchButton = new JButton("CERCA");
        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        importButton = new JButton("IMPORTA DA SPESA");

        searchButton.addActionListener(this);
        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        importButton.setPreferredSize(new Dimension(175, 50));
        searchButton.setPreferredSize(new Dimension(75, 30));

        searchBar.addKeyListener(this);

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        importButton.addActionListener(this);

        inventoryTable = new JTable();

        redrawTable(inventoryList);

        scrollPanel = new JScrollPane(inventoryTable);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Inventario"));

        add(new JLabel("Cerca:"), "right");
        add(searchBar, "");
        add(searchButton, "left");
        add(scrollPanel, "span 2 5, grow");
        add(new JLabel(""));
        add(new JLabel(""));
        add(addButton, "right");
        add(removeButton, "right");
        add(importButton, "right");

        inventoryTable.setAutoCreateRowSorter(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            new ExistingIngredientPanel(inventoryList, this, ingredientList);
        }

        if (e.getSource() == removeButton) {
            if (inventoryTable.getSelectedRow()>= 0 && inventoryTable.getSelectedRow()<inventoryTable.getRowCount()) {
                int row = inventoryTable.getSelectedRow();
                int selectedId = (int) inventoryTable.getValueAt(row, 0);
                inventoryList.remove(IngredientQty.findIngredientQty(inventoryList, selectedId));
                redrawTable(inventoryList);
                inventoryTable.clearSelection();
            }
        }
        if (e.getSource() == searchButton) {
            String searchedThing = searchBar.getText();
            ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(IngredientQty.findSearchedIngredientQty(searchedThing, inventoryList));

            redrawTable(searchedIngredientsList);
        }

        if(e.getSource()==importButton){
            new ImportShoppingFrame(shoppingList,inventoryList,ingredientList,this);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getSource() == searchBar) {
            String searchedThing = searchBar.getText();
            ArrayList<IngredientQty> searchedIngredientsList = new ArrayList<>(IngredientQty.findSearchedIngredientQty(searchedThing, inventoryList));

            redrawTable(searchedIngredientsList);
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

        inventoryTable.setModel(ingredientsModel);

        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        ingredientsModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (inventoryTable.getSelectedRow() >= 0 && inventoryTable.getSelectedRow() < inventoryTable.getRowCount()) {
                    int selectedRow = inventoryTable.getSelectedRow();
                    inventoryTable.clearSelection();
                    String newQty = (String) inventoryTable.getValueAt(selectedRow, 2);
                    int Qty = Main.strtoint(newQty);
                    IngredientQty tmpIngredientQty = IngredientQty.findIngredientQty(ingredientQtyList, (int) inventoryTable.getValueAt(selectedRow, 0));
                    if (Qty > 0)
                        tmpIngredientQty.setQty(Qty);
                    inventoryTable.setValueAt(tmpIngredientQty.getQty() + " " + tmpIngredientQty.stringType(), selectedRow, 2);
                }
            }
        });
    }
}

