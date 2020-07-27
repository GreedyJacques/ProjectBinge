package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
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

public class AddFromRecipeFrame extends JFrame implements ActionListener {

    private JCheckBox recipeBox;

    private JPanel mainpanel, boxPanel;

    private JButton addButton;

    private JScrollPane scrollPanel;

    private ArrayList<Recipe> recipeList;
    private ArrayList<IngredientQty> shoppingList;
    private ArrayList<Ingredient> ingredientList;
    private JTable shoppingTable;

    private JComboBox recipeName;

    private String[] recipeStrings;

    private Recipe selectedRecipe;
    private ArrayList<JCheckBox> boxList;
    private ArrayList<JTextField> fieldList;

    public AddFromRecipeFrame(ArrayList<Recipe> recipeList, ArrayList<IngredientQty> shoppingList, JTable shoppingTable, ArrayList<Ingredient> ingredientList) {
        super("Importa da Ricetta");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.recipeList = recipeList;
        this.shoppingList = shoppingList;
        this.ingredientList = ingredientList;
        this.shoppingTable = shoppingTable;

        mainpanel = new JPanel(new MigLayout("fill, wrap 1", "[]", "[][][grow,fill][]"));
        boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[]20", ""));
        addButton = new JButton("AGGIUNGI");

        addButton.setPreferredSize(new Dimension(150, 75));
        scrollPanel = new JScrollPane(boxPanel);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Ingredienti"));

        addButton.addActionListener(this);

        recipeStrings = new String[recipeList.size()];

        for (int i = 0; i < recipeList.size(); ++i)
            recipeStrings[i] = recipeList.get(i).getName() + " [" + recipeList.get(i).getId() + "]";

        recipeName = new JComboBox(recipeStrings);

        AutoCompletion.enable(recipeName);
        recipeName.setSelectedIndex(-1);


        recipeName.addActionListener(this);
        recipeName.setEditable(true);

        mainpanel.add(new JLabel("Ricetta:"));
        mainpanel.add(recipeName);
        mainpanel.add(scrollPanel);
        mainpanel.add(addButton);

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

        if (e.getSource() == recipeName) {
            try {
                boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[]20", ""));
                scrollPanel.setViewportView(boxPanel);
                selectedRecipe = Recipe.findRecipe(recipeList, idFromString(recipeStrings[recipeName.getSelectedIndex()]));

                boxList = new ArrayList<>();
                fieldList = new ArrayList<>();

                for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                    boxList.add(new JCheckBox(selectedRecipe.getIngredients().get(i).getName()));
                    fieldList.add(new JTextField());
                    fieldList.get(i).setText(String.valueOf(selectedRecipe.getIngredients().get(i).getQty()));

                    boxPanel.add(boxList.get(i));
                    boxPanel.add(fieldList.get(i), "grow");
                    boxPanel.add(new JLabel(selectedRecipe.getIngredients().get(i).stringType()));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }

            revalidate();
            repaint();

        }

        if (e.getSource() == addButton && selectedRecipe != null) {

            boolean correct = true;
            int quantity = 0;
            for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                if (boxList.get(i).isSelected()) {
                    try {
                        quantity = Integer.parseInt(fieldList.get(i).getText());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(null, "Inserisci la quantita' di " + selectedRecipe.getIngredients().get(i).getName());
                        correct = false;
                        break;
                    }
                    if (quantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Inserisci una quantita' positiva di " + selectedRecipe.getIngredients().get(i).getName());
                        correct = false;
                        break;
                    }
                }
            }
            if (correct) {
                for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                    quantity = Integer.parseInt(fieldList.get(i).getText());
                    int selectedIngredientId = selectedRecipe.getIngredients().get(i).getId();
                    boolean added = false;
                    for (IngredientQty inv : shoppingList) {
                        if (inv.getId() == selectedIngredientId) {
                            inv.setQty(inv.getQty() + quantity);
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        IngredientQty selectedIngredientQty = new IngredientQty(Ingredient.findIngredient(ingredientList, selectedIngredientId), quantity);
                        shoppingList.add(selectedIngredientQty);
                    }
                }

                Object[][] shoppingMatrix = IngredientQty.toMatrix(shoppingList);

                DefaultTableModel shoppingModel = new DefaultTableModel(shoppingMatrix, new String[]{"ID", "Nome", "Qty"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if (column == 0 || column == 1)
                            return false;
                        else
                            return true;
                    }
                };

                shoppingModel.addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        if (shoppingTable.getSelectedRow() >= 0 && shoppingTable.getSelectedRow() < shoppingTable.getRowCount()) {
                            int selectedRow = shoppingTable.getSelectedRow();
                            shoppingTable.clearSelection();
                            String newQty = (String) shoppingTable.getValueAt(selectedRow, 2);
                            int Qty = Main.strtoint(newQty);
                            IngredientQty tmpIngredientQty = IngredientQty.findIngredientQty(shoppingList, (int) shoppingTable.getValueAt(selectedRow, 0));
                            if (Qty > 0)
                                tmpIngredientQty.setQty(Qty);
                            shoppingTable.setValueAt(tmpIngredientQty.getQty() + " " + tmpIngredientQty.stringType(), selectedRow, 2);
                        }
                    }
                });

                shoppingTable.setModel(shoppingModel);

                shoppingTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                shoppingTable.getColumnModel().getColumn(1).setPreferredWidth(750);
                shoppingTable.getColumnModel().getColumn(2).setPreferredWidth(750);


                dispose();


            }


        }
    }
}
