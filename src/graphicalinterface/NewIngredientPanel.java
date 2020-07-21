package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewIngredientPanel extends JFrame implements ActionListener {
    private JPanel mainpanel, typePanel;

    private JButton addButton;

    private JTextField ingredientName, ingredientQty, ingredientKcal;

    private ButtonGroup typeGroup;

    private JRadioButton mlButton, gButton, pzButton;

    private IngredientQty newIngredientQty;
    private ArrayList<IngredientQty> ingredientQtylist;
    private JTable ingredientQtyTable;
    private ArrayList<Ingredient> ingredientList;
    private ExistingIngredientPanel callerFrame;

    public NewIngredientPanel(IngredientQty newIngredientQty, ArrayList<IngredientQty> ingredientQtylist, JTable ingredientQtyTable, ArrayList<Ingredient> ingredientList, ExistingIngredientPanel callerFrame) {
        super("Nuovo Ingrediente");

        this.newIngredientQty = newIngredientQty;
        this.ingredientQtylist = ingredientQtylist;
        this.ingredientQtyTable = ingredientQtyTable;
        this.ingredientList = ingredientList;
        this.callerFrame = callerFrame;

        mainpanel = new JPanel(new MigLayout("fill, wrap 1", "[grow, fill]", "20[][][][][][][][]"));
        addButton = new JButton("AGGIUNGI");

        typePanel = new JPanel(new MigLayout("fill,wrap 3", "0[]0[]0[]0", "0[]0"));

        mlButton = new JRadioButton("ml");
        gButton = new JRadioButton("g");
        pzButton = new JRadioButton("pz.");

        typeGroup = new ButtonGroup();
        typeGroup.add(mlButton);
        typeGroup.add(gButton);
        typeGroup.add(pzButton);

        typePanel.add(mlButton);
        typePanel.add(gButton);
        typePanel.add(pzButton);

        addButton.setPreferredSize(new Dimension(175, 50));

        addButton.addActionListener(this);

        ingredientName = new JTextField();
        ingredientKcal = new JTextField();
        ingredientQty = new JTextField();

        mainpanel.add(new JLabel("Nome:"));
        mainpanel.add(ingredientName, "");
        mainpanel.add(new JLabel("Tipo:"));
        mainpanel.add(typePanel, "");
        mainpanel.add(new JLabel("Quantità:"));
        mainpanel.add(ingredientQty, "");
        mainpanel.add(new JLabel("Kcal:"));
        mainpanel.add(ingredientKcal, "");
        mainpanel.add(addButton, "right");

        setContentPane(mainpanel);
        setLocation(400, 230);
        setSize(300, 300);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            for(Ingredient i : ingredientList) {
                if(ingredientName.getText().equals(i.getName())){
                    JOptionPane.showMessageDialog(null, "L'ingrediente " + i.getName() + " esiste gia'");
                    newIngredientQty.setName(null);
                }
            }

            if (newIngredientQty.getName() != null && ingredientName.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Inserisci il nome");
                newIngredientQty.setName(null);
            }
            else if (newIngredientQty.getName() != null)
                newIngredientQty.setName(ingredientName.getText());

            if (mlButton.isSelected())
                newIngredientQty.setType(1);
            else if (gButton.isSelected())
                newIngredientQty.setType(2);
            else if (pzButton.isSelected())
                newIngredientQty.setType(3);
            else
                JOptionPane.showMessageDialog(null, "Seleziona il tipo");

            try {
                newIngredientQty.setQty(Integer.parseInt(ingredientQty.getText()));
            } catch (NumberFormatException exception1) {
                JOptionPane.showMessageDialog(null, "Inserisci la quantita'");
            }

            try {
                newIngredientQty.setKcal(Double.parseDouble(ingredientKcal.getText()));
            } catch (NumberFormatException exception2) {
                JOptionPane.showMessageDialog(null, "Inserisci le calorie per " + newIngredientQty.stringType());
            }

            if (newIngredientQty.getName() != null && newIngredientQty.getType() != 0 && newIngredientQty.getQty() >= 0 && newIngredientQty.getKcal() >= 0) {
                ingredientList.add(new Ingredient(newIngredientQty.getId(),newIngredientQty.getName(),newIngredientQty.getType(),newIngredientQty.getKcal()));
                ingredientQtylist.add(newIngredientQty);

                Object[] newIngredientQtyRow = {newIngredientQty.getId(),newIngredientQty.getName(),newIngredientQty.getQty() + " " + newIngredientQty.stringType(), newIngredientQty.getKcal(), newIngredientQty.getType()};

                DefaultTableModel model = (DefaultTableModel) ingredientQtyTable.getModel();

                model.addRow(newIngredientQtyRow);

                callerFrame.dispose();
                dispose();
            }
            else
                newIngredientQty.setName("");
        }

    }
}
