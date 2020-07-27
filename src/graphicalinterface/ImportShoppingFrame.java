package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import launchcode.Main;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
    private TablePanel callerPanel;
    private ArrayList<JCheckBox> boxList;
    private ArrayList<JTextField> fieldList;

    public ImportShoppingFrame(ArrayList<IngredientQty> shoppingList, ArrayList<IngredientQty> inventoryList, ArrayList<Ingredient> ingredientList, TablePanel callerPanel) {
        super("Importa da Lista della Spesa");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.shoppingList = shoppingList;
        this.inventoryList = inventoryList;
        this.ingredientList = ingredientList;
        this.callerPanel = callerPanel;

        mainpanel = new JPanel(new MigLayout("fill, wrap 2", "[][]", "[grow,fill][][]"));
        boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[15]10", ""));
        transferButton = new JButton("TRASFERISCI SPESA");
        transferButton.setPreferredSize(new Dimension(150, 50));
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

        boxList = new ArrayList<>();
        fieldList = new ArrayList<>();

        for (int i = 0; i < shoppingList.size(); ++i) {
            boxList.add(new JCheckBox(shoppingList.get(i).getName()));
            boxList.get(i).setSelected(true);
            fieldList.add(new JTextField());
            fieldList.get(i).setText(String.valueOf(shoppingList.get(i).getQty()));

            boxPanel.add(boxList.get(i));
            boxPanel.add(fieldList.get(i), "grow");
            boxPanel.add(new JLabel(shoppingList.get(i).stringType()));
        }

        setContentPane(mainpanel);
        setLocation(400, 230);
        setSize(300, 600);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == transferButton) {

            boolean correct = true;
            int quantity = 0;
            for (int i = 0; i < shoppingList.size(); ++i) {
                if (boxList.get(i).isSelected()) {
                    try {
                        quantity = Integer.parseInt(fieldList.get(i).getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Inserisci la quantita' di " + shoppingList.get(i).getName());
                        correct = false;
                        break;
                    }
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Inserisci una quantita' positiva di " + shoppingList.get(i).getName());
                        correct = false;
                        break;
                    }
                }
            }
            if (correct) {
                for (int i = 0; i < shoppingList.size(); ++i) {
                    if (boxList.get(i).isSelected()) {
                        quantity = Integer.parseInt(fieldList.get(i).getText());
                        int selectedIngredientId = shoppingList.get(i).getId();
                        boolean added = false;
                        for (IngredientQty inv : inventoryList) {
                            if (inv.getId() == selectedIngredientId) {
                                inv.setQty(inv.getQty() + quantity);
                                added = true;
                                break;
                            }
                        }
                        if (!added) {
                            IngredientQty selectedIngredientQty = new IngredientQty(Ingredient.findIngredient(ingredientList, selectedIngredientId), quantity);
                            inventoryList.add(selectedIngredientQty);
                        }
                    }
                }

                callerPanel.redrawTable(inventoryList);

                if (emptyButton.isSelected())
                    shoppingList.removeAll(shoppingList);
                else {
                    for (int i = 0; i < shoppingList.size(); ++i) {
                        if (boxList.get(i).isSelected()) {
                            if (Integer.parseInt(fieldList.get(i).getText()) >= shoppingList.get(i).getQty()) {
                                shoppingList.get(i).setQty(0);
                            } else {
                                shoppingList.get(i).setQty(shoppingList.get(i).getQty() - Integer.parseInt(fieldList.get(i).getText()));
                            }
                        }
                    }
                    for (int i = shoppingList.size() - 1; i >= 0; --i) {
                        if (shoppingList.get(i).getQty() == 0)
                            shoppingList.remove(i);
                    }
                }
                dispose();
            }
        }
    }
}
