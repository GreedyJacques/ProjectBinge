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

public class ExistingIngredientPanel extends JFrame implements ActionListener {

    private JPanel mainpanel;

    private JButton newButton, addButton;

    private JComboBox ingredientName;
    private JLabel ingredientKcal;
    private JTextField ingredientQty;

    String[] ingredientStrings;

    private ArrayList<IngredientQty> ingredientQtylist;
    private JTable ingredientQtyTable;
    private ArrayList<Ingredient> ingredientList;

    public ExistingIngredientPanel(ArrayList<IngredientQty> ingredientQtylist, JTable ingredientQtyTable, ArrayList<Ingredient> ingredientList) {
        super("Aggiungi Ingrediente");

        this.ingredientQtylist = ingredientQtylist;
        this.ingredientQtyTable = ingredientQtyTable;
        this.ingredientList = ingredientList;

        mainpanel = new JPanel(new MigLayout("fill, wrap 2", "[grow, fill][]", "[][][][][][][][]"));
        newButton = new JButton("NUOVO");
        addButton = new JButton("AGGIUNGI");

        addButton.setPreferredSize(new Dimension(50, 50));

        newButton.addActionListener(this);
        addButton.addActionListener(this);

        ingredientStrings = new String[ingredientList.size()];

        for (int i = 0; i < ingredientList.size(); ++i)
            ingredientStrings[i] = ingredientList.get(i).getName() + " [" + ingredientList.get(i).getId() + "]";

        ingredientName = new JComboBox(ingredientStrings);
        ingredientQty = new JTextField();

        AutoCompletion.enable(ingredientName);

        ingredientKcal = new JLabel();

        ingredientName.addActionListener(this);
        ingredientName.setEditable(true);

        mainpanel.add(new JLabel("Nome:"), "bottom");
        mainpanel.add(newButton, "left");
        mainpanel.add(ingredientName, "span 2 1");
        mainpanel.add(new JLabel("Kcal:"), "span 2 1");
        mainpanel.add(ingredientKcal, "span 2 1");
        mainpanel.add(new JLabel("Quantità:"), "span 2 1");
        mainpanel.add(ingredientQty, "span 2 1");
        mainpanel.add(addButton, "span 2 1");


        setContentPane(mainpanel);
        setLocation(380, 210);
        setSize(300, 300);
        setVisible(true);
    }

    static int idFromString(String string) {
        int out = 0;
        StringBuffer id = new StringBuffer();

        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '[') {
                for (int k = i + 1; k < string.length() - 1; ++k) {
                    id.append(string.charAt(k));
                }
                break;
            }
        }

        out = Integer.parseInt(id.toString());

        return out;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ingredientName) {
            Ingredient selectedIngredient = Ingredient.findIngredient(ingredientList,idFromString(ingredientStrings[ingredientName.getSelectedIndex()]));
            ingredientKcal.setText(selectedIngredient.getKcal() + " kCal per " + selectedIngredient.stringType());
        }

        if (e.getSource() == newButton) {
            int newIngredientID = (Ingredient.getMaxId(ingredientList)) + 1;
            Ingredient newIngredient = new Ingredient(newIngredientID, "", 0, -1);
            IngredientQty newIngredientQty = new IngredientQty(newIngredient, 0);
            new NewIngredientPanel(newIngredientQty, ingredientQtylist, ingredientQtyTable, ingredientList, this);
        }
    }
}


