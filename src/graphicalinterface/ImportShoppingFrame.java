package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImportShoppingFrame extends JFrame implements ActionListener {

    private JPanel mainpanel;

    private JRadioButton emptyButton, mantainButton;
    private ButtonGroup optionGroup;

    private JButton transferButton;
    private JPanel boxPanel;
    private JScrollPane scrollPanel;

    private ArrayList<IngredientQty> shoppingList;
    private ArrayList<IngredientQty> inventoryList;
    private ArrayList<Ingredient> ingredientList;
    private ArrayList<IngredientQty> boxIngredientQtyList;
    private ArrayList<JCheckBox> boxList;
    private ArrayList<JTextField> fieldList;

    public ImportShoppingFrame(ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList, ArrayList<Ingredient> ingredientList) {
        super("Importa da Lista della Spesa");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;
        this.ingredientList = ingredientList;

        mainpanel = new JPanel(new MigLayout("fill, wrap 2", "[][]", "[grow,fill][][]"));
        boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[]20", ""));
        transferButton = new JButton("TRASFERISCI SPESA");
        transferButton.setPreferredSize(new Dimension(150, 75));
        scrollPanel = new JScrollPane(boxPanel);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Spesa"));

        emptyButton = new JRadioButton("<html>Svuota la Lista <br>della Spesa");
        mantainButton = new JRadioButton("<html>Mantieni Elementi <br>Non Selezionati");
        optionGroup = new ButtonGroup();
        optionGroup.add(emptyButton);
        optionGroup.add(mantainButton);

        mantainButton.setSelected(true);
        transferButton.addActionListener(this);

        mainpanel.add(scrollPanel, "span 2 1, grow");
        mainpanel.add(emptyButton);
        mainpanel.add(mantainButton);
        mainpanel.add(transferButton, "span 2, grow");

        boxIngredientQtyList = new ArrayList<>();
        boxList = new ArrayList<>();
        fieldList = new ArrayList<>();

        for(int i = 0; i<shoppingList.size(); ++i) {
            boxList.add(new JCheckBox(shoppingList.get(i).getName()));
            fieldList.add(new JTextField());
            fieldList.get(i).setText(String.valueOf(shoppingList.get(i).getQty()));
            boxIngredientQtyList.add(shoppingList.get(i));

            boxPanel.add(boxList.get(i));
            boxPanel.add(fieldList.get(i), "grow");
            boxPanel.add(new JLabel(shoppingList.get(i).stringType()));
        }

        setContentPane(mainpanel);
        setLocation(400, 230);
        setSize(300, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transferButton) {

            for (IngredientQty s : shoppingList) {

            }


            dispose();
        }

    }
}
