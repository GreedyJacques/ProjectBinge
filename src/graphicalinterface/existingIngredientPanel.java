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

public class existingIngredientPanel extends JFrame implements ActionListener {

    private JPanel mainpanel;

    private JButton newButton;

    private JTextField ingredientName, ingredientQty;


    private IngredientQty newIngredientQty;
    private ArrayList<IngredientQty> ingredientQtylist;
    private JTable ingredientQtyTable;
    private ArrayList<Ingredient> ingredientList;

    public existingIngredientPanel(IngredientQty newIngredientQty, ArrayList<IngredientQty> ingredientQtylist, JTable ingredientQtyTable, ArrayList<Ingredient> ingredientList) {
        super("Ingrediente");

        this.newIngredientQty = newIngredientQty;
        this.ingredientQtylist = ingredientQtylist;
        this.ingredientQtyTable = ingredientQtyTable;
        this.ingredientList = ingredientList;

        mainpanel = new JPanel(new MigLayout("fill, wrap 1", "[grow, fill]", "20[][][][][][][]"));
        newButton = new JButton("NUOVO");




        newButton.setPreferredSize(new Dimension(175, 50));

        newButton.addActionListener(this);

        ingredientName = new JTextField();
        ingredientQty = new JTextField();

        mainpanel.add(newButton,"top right");
        mainpanel.add(new JLabel("nome:"));
        mainpanel.add(ingredientName, "");
        mainpanel.add(new JLabel("Tipo:"));
        mainpanel.add(new JLabel("") );
        mainpanel.add(new JLabel("Kcal:"));
        mainpanel.add(new JLabel("") );
        mainpanel.add(new JLabel("Quantità:"));
        mainpanel.add(ingredientQty, "");

        setContentPane(mainpanel);
        setLocation(400, 230);
        setSize(400, 300);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newButton) {

            int newIngredientID = (IngredientQty.getMaxId(ingredientQtylist)) + 1;
            Ingredient newIngredient = new Ingredient(newIngredientID, "", 0, -1);
            IngredientQty newIngredientQty = new IngredientQty(newIngredient, 0);
            new NewIngredientPanel(newIngredientQty, ingredientQtylist, ingredientQtyTable, ingredientList);

        }

    }
}


