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
import java.util.ArrayList;

public class InventoryPanel extends JPanel implements ActionListener {

    private JButton addButton, removeButton, importButton;

    DefaultTableModel inventoryModel;
    JTable inventoryTable;

    String selectedQty;
    Object selectedIngredient;

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    public InventoryPanel(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList) {
        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]", "50[grow, fill][][][]50"));
        this.recipeList = recipeList;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;

        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        importButton = new JButton("IMPORTA DA SPESA");

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        importButton.setPreferredSize(new Dimension(175, 50));

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        importButton.addActionListener(this);

        Object[][] inventoryMatrix = IngredientQty.toMatrix(inventoryList);

        inventoryModel = new DefaultTableModel(inventoryMatrix, new String[]{"ID", "Nome", "Qty"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1)
                    return false;
                else
                    return true;
            }
        };

        inventoryTable = new JTable(inventoryModel);
        JScrollPane scrollPanel = new JScrollPane(inventoryTable);

        inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Inventario"));

        ListSelectionModel selectionModel = inventoryTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!selectionModel.isSelectionEmpty()) {
                    int row = inventoryTable.getSelectedRow();
                    selectedIngredient = inventoryModel.getValueAt(row, 1);
                    selectedQty = (String) inventoryModel.getValueAt(row, 2);
                    JOptionPane.showMessageDialog(null, "there are " + selectedQty + " of " + selectedIngredient + " on the Inventory");
                }
            }
        });

        add(scrollPanel, "span 1 4, grow");
        add(new JLabel(""));
        add(addButton, "right");
        add(removeButton, "right");
        add(importButton, "right");

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            int newIngredientID = (IngredientQty.getMaxId(inventoryList)) + 1;
            Ingredient newIngredient = new Ingredient(newIngredientID, "", 0, -1);
            IngredientQty newIngredientQty = new IngredientQty(newIngredient, 0);
            new existingIngredientPanel(newIngredientQty, inventoryList, inventoryTable, ingredientList);

            }

            if (e.getSource() == removeButton) {
                if (selectedIngredient != null) {
                    int row = inventoryTable.getSelectedRow();
                    int selectedId = (int) inventoryTable.getValueAt(row, 0);
                    inventoryList.remove(IngredientQty.findIngredient(inventoryList, (int) selectedId));
                    inventoryModel.removeRow(row);
                } else
                    return;
            }
        }
    }

