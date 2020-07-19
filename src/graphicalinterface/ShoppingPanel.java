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
            modifyButton, addFromRecipeButton;

    private ArrayList<IngredientQty> ingredientQtyList;

    DefaultTableModel ingredientQtyModel;

    String selectedQty;
    Object selectedIngredient;

    JTable ingredientQtyTable;

    public ShoppingPanel() {


        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]", "50[grow, fill][][][][]50"));


        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        modifyButton = new JButton("MODIFICA");
        addFromRecipeButton = new JButton("AGGIUNGI DA RICETTA");

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);
        addFromRecipeButton.addActionListener(this);

        //TEST
        ingredientQtyList = new ArrayList<>();
        IngredientQty a = new IngredientQty();
        a.setQty(2);

        IngredientQty b = new IngredientQty();
        b.setQty(6);

        Ingredient c = new Ingredient("Nutella",1,5);
        IngredientQty c1=new IngredientQty(c,300);

        ingredientQtyList.add(a);
        ingredientQtyList.add(b);
        ingredientQtyList.add(c1);
        //END TEST


        Object[][] ingredientQtyMatrix = IngredientQty.toMatrix(ingredientQtyList);

        ingredientQtyModel = new DefaultTableModel(ingredientQtyMatrix, new String[]{"ID", "Nome", "Qty"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1)
                    return false;
                else
                    return true;
            }
        };

        ingredientQtyTable = new JTable(ingredientQtyModel);
        JScrollPane scrollPanel = new JScrollPane(ingredientQtyTable);


        ingredientQtyTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        ingredientQtyTable.getColumnModel().getColumn(1).setPreferredWidth(750);
        ingredientQtyTable.getColumnModel().getColumn(2).setPreferredWidth(750);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Spesa"));

        ListSelectionModel selectionModel = ingredientQtyTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!selectionModel.isSelectionEmpty()) {
                    int row = ingredientQtyTable.getSelectedRow();
                    selectedIngredient = ingredientQtyModel.getValueAt(row, 1);
                    selectedQty = (String) ingredientQtyModel.getValueAt(row, 2);
                    JOptionPane.showMessageDialog(null, "there are " + selectedQty + " of " + selectedIngredient + " on the shopping list");
                }
            }
        });

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        modifyButton.setPreferredSize(new Dimension(175, 50));
        addFromRecipeButton.setPreferredSize(new Dimension(175, 50));

        add(scrollPanel, "span 1 5, grow");
        add(new JLabel(""));
        add(addButton, "right");
        add(addFromRecipeButton, "right");
        add(removeButton, "right");
        add(modifyButton, "right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {

        }
        if (e.getSource() == removeButton) {
            if (selectedIngredient != null) {
                int row = ingredientQtyTable.getSelectedRow();
                int selectedId = (int) ingredientQtyTable.getValueAt(row, 0);
                ingredientQtyList.remove(IngredientQty.findIngredient(ingredientQtyList, (int) selectedId));
                ingredientQtyModel.removeRow(row);
            } else
                return;

        }
        if (e.getSource() == modifyButton) {

        }
        if (e.getSource() == addFromRecipeButton) {

        }

    }
}
