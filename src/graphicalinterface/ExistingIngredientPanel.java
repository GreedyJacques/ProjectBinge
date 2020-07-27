package graphicalinterface;


import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ExistingIngredientPanel extends JFrame implements ActionListener {

    private JPanel mainpanel;

    private JButton newButton, addButton;

    private JComboBox ingredientName;
    private JLabel ingredientKcal;
    private JTextField ingredientQty;

    String[] ingredientStrings;

    private ArrayList<IngredientQty> ingredientQtylist;
    private TablePanel callerPanel;
    private ArrayList<Ingredient> ingredientList;

    public ExistingIngredientPanel(ArrayList<IngredientQty> ingredientQtylist, TablePanel callerPanel, ArrayList<Ingredient> ingredientList) {
        super("Aggiungi Ingrediente");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.ingredientQtylist = ingredientQtylist;
        this.callerPanel = callerPanel;
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

        ingredientName.setSelectedIndex(-1);

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
            try {
                Ingredient selectedIngredient = Ingredient.findIngredient(ingredientList, idFromString(ingredientStrings[ingredientName.getSelectedIndex()]));
                ingredientKcal.setText(selectedIngredient.getKcal() + " kCal per " + selectedIngredient.stringType());
            } catch (ArrayIndexOutOfBoundsException exception) {
                //Do Nothing
            }
        }

        if (e.getSource() == addButton) {
            try {
                int quantity = 0;
                boolean correct = true;
                try {
                    quantity = Integer.parseInt(ingredientQty.getText());
                } catch (NumberFormatException exception1) {
                    JOptionPane.showMessageDialog(null, "Inserisci la quantita'");
                    correct = false;
                }
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(null, "Inserisci una quantita' positiva");
                    correct = false;
                }
                if (correct) {
                    Ingredient selectedIngredient = Ingredient.findIngredient(ingredientList, idFromString(ingredientStrings[ingredientName.getSelectedIndex()]));
                    boolean added = false;
                    for (IngredientQty i : ingredientQtylist) {
                        if (i.getId() == selectedIngredient.getId()) {
                            i.setQty(i.getQty() + quantity);
                            added = true;

                            int row = 0;

                            callerPanel.redrawTable(ingredientQtylist);

                            dispose();

                            break;
                        }
                    }
                    if (!added) {
                        IngredientQty selectedIngredientQty = new IngredientQty(selectedIngredient, quantity);
                        ingredientQtylist.add(selectedIngredientQty);

                        callerPanel.redrawTable(ingredientQtylist);

                        dispose();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                JOptionPane.showMessageDialog(null, "Inserisci un ingrediente valido");
            }
        }

        if (e.getSource() == newButton) {
            int newIngredientID = (Ingredient.getMaxId(ingredientList)) + 1;
            Ingredient newIngredient = new Ingredient(newIngredientID, "", 0, -1);
            IngredientQty newIngredientQty = new IngredientQty(newIngredient, 0);
            new NewIngredientPanel(newIngredientQty, ingredientQtylist, callerPanel, ingredientList, this);
        }
    }
}