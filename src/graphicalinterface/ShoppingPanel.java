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

public class ShoppingPanel extends JPanel implements ActionListener {
    private JButton addButton, removeButton,
            exportButton, addFromRecipeButton;

    DefaultTableModel shoppingModel;
    JTable shoppingTable;

    String selectedQty;
    Object selectedIngredient;

    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    ArrayList<IngredientQty> inventoryList;

    public ShoppingPanel(ArrayList<Recipe> recipeList, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList) {
        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]", "50[grow, fill][][][][]50"));
        this.recipeList = recipeList;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;

        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        exportButton = new JButton("ESPORTA");
        addFromRecipeButton = new JButton("AGGIUNGI DA RICETTA");

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        exportButton.addActionListener(this);
        addFromRecipeButton.addActionListener(this);

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
                    JOptionPane.showMessageDialog(null, "there are " + selectedQty + " of " + selectedIngredient + " on the shopping list");
                }
            }
        });

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        exportButton.setPreferredSize(new Dimension(175, 50));
        addFromRecipeButton.setPreferredSize(new Dimension(175, 50));

        add(scrollPanel, "span 1 5, grow");
        add(new JLabel(""));
        add(addButton, "right");
        add(addFromRecipeButton, "right");
        add(removeButton, "right");
        add(exportButton, "right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {

        }
        if (e.getSource() == removeButton) {
            if (selectedIngredient != null) {
                int row = shoppingTable.getSelectedRow();
                int selectedId = (int) shoppingTable.getValueAt(row, 0);
                shoppingList.remove(IngredientQty.findIngredient(shoppingList, (int) selectedId));
                shoppingModel.removeRow(row);
            } else
                return;

        }
        if (e.getSource() == exportButton) {

        }
        if (e.getSource() == addFromRecipeButton) {

        }

    }
}
