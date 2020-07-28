package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddFromRecipeFrame extends JFrame implements ActionListener {

    private JPanel mainpanel, boxPanel;

    private JButton addButton;

    private JScrollPane scrollPanel;

    private ArrayList<Recipe> recipeList;
    private ArrayList<IngredientQty> shoppingList;
    private ArrayList<Ingredient> ingredientList;
    private TablePanel callerPanel;
    private int recipeID;
    private int portions;

    private JComboBox recipeName;
    private JTextField portionField;

    private String[] recipeStrings;

    private Recipe selectedRecipe;
    private ArrayList<JCheckBox> boxList;
    private ArrayList<JTextField> fieldList;

    public AddFromRecipeFrame(ArrayList<Recipe> recipeList, ArrayList<IngredientQty> shoppingList, ArrayList<Ingredient> ingredientList, TablePanel callerPanel, int recipeID, int portions) {
        super("Importa da Ricetta");

        try {
            setIconImage(ImageIO.read(new File("icons/icon.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        this.recipeList = recipeList;
        this.shoppingList = shoppingList;
        this.ingredientList = ingredientList;
        this.callerPanel = callerPanel;
        this.recipeID = recipeID;
        this.portions = portions;

        mainpanel = new JPanel(new MigLayout("fill, wrap 3", "[][30][grow,fill]", "[][][grow,fill][]"));
        boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[15]10", ""));
        addButton = new JButton("AGGIUNGI");

        addButton.setPreferredSize(new Dimension(150, 50));
        scrollPanel = new JScrollPane(boxPanel);

        scrollPanel.setBorder(BorderFactory.createTitledBorder("Lista Ingredienti"));

        addButton.addActionListener(this);

        recipeStrings = new String[recipeList.size()];

        for (int i = 0; i < recipeList.size(); ++i)
            recipeStrings[i] = recipeList.get(i).getName() + " [" + recipeList.get(i).getId() + "]";

        recipeName = new JComboBox(recipeStrings);

        AutoCompletion.enable(recipeName);

        if (recipeID == -1)
            recipeName.setSelectedIndex(-1);
        else {
            for (int s = 0; s < recipeStrings.length; ++s) {
                if (recipeID == idFromString(recipeStrings[s])) {
                    try {
                        boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[15]10", ""));
                        scrollPanel.setViewportView(boxPanel);
                        selectedRecipe = Recipe.findRecipe(recipeList, recipeID);
                        boxList = new ArrayList<>();
                        fieldList = new ArrayList<>();

                        for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                            boxList.add(new JCheckBox(selectedRecipe.getIngredients().get(i).getName()));
                            boxList.get(i).setSelected(true);
                            fieldList.add(new JTextField());
                            fieldList.get(i).setText(String.valueOf(selectedRecipe.getIngredients().get(i).getQty() * portions));

                            boxPanel.add(boxList.get(i));
                            boxPanel.add(fieldList.get(i), "grow");
                            boxPanel.add(new JLabel(selectedRecipe.getIngredients().get(i).stringType()));
                        }
                    } catch (ArrayIndexOutOfBoundsException exception) {
                        exception.printStackTrace();
                    }
                    recipeName.setSelectedIndex(s);
                    revalidate();
                    repaint();
                    break;
                }
            }
        }

        recipeName.addActionListener(this);
        recipeName.setEditable(true);

        portionField = new JTextField(String.valueOf(portions));

        mainpanel.add(new JLabel("Ricetta:"));
        mainpanel.add(recipeName, "span 2 1, grow");
        mainpanel.add(new JLabel("Porzioni:"));
        mainpanel.add(portionField, "grow");
        mainpanel.add(new JLabel());
        mainpanel.add(scrollPanel, "span 3 1, grow");
        mainpanel.add(addButton, "span 3 1, grow");

        setContentPane(mainpanel);
        setLocation(380, 210);
        setSize(300, 500);
        setVisible(true);

        AddFromRecipeFrame self = this;

        portionField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                boolean correct = true;
                try {
                    self.setPortions(Integer.parseInt(portionField.getText()));
                } catch (NumberFormatException exception) {
                    self.setPortions(1);
                    correct = false;
                }
                if (correct && self.getPortions() > 0 && recipeName.getSelectedIndex() >= 0 && recipeName.getSelectedIndex() < recipeStrings.length) {
                    Recipe selectedRecipe = Recipe.findRecipe(recipeList, idFromString(recipeStrings[recipeName.getSelectedIndex()]));
                    for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                        fieldList.get(i).setText(String.valueOf(selectedRecipe.getIngredients().get(i).getQty() * self.getPortions()));
                    }
                }
            }
        });
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
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
                boxPanel = new JPanel(new MigLayout("wrap 3, gapy 0", "5[130, grow, fill]10[50, fill]10[15]10", ""));
                scrollPanel.setViewportView(boxPanel);
                selectedRecipe = Recipe.findRecipe(recipeList, idFromString(recipeStrings[recipeName.getSelectedIndex()]));

                boxList = new ArrayList<>();
                fieldList = new ArrayList<>();

                for (int i = 0; i < selectedRecipe.getIngredients().size(); ++i) {
                    boxList.add(new JCheckBox(selectedRecipe.getIngredients().get(i).getName()));
                    boxList.get(i).setSelected(true);
                    fieldList.add(new JTextField());
                    fieldList.get(i).setText(String.valueOf(selectedRecipe.getIngredients().get(i).getQty() * portions));

                    boxPanel.add(boxList.get(i));
                    boxPanel.add(fieldList.get(i), "grow");
                    boxPanel.add(new JLabel(selectedRecipe.getIngredients().get(i).stringType()));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                //Do Nothing
            }
            revalidate();
            repaint();
        }

        if (e.getSource() == addButton) {
            if(selectedRecipe == null){
                JOptionPane.showMessageDialog(null, "Inserisci una ricetta valida");
                return;
            }
            try {
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
                        if (boxList.get(i).isSelected()) {
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
                    }

                    if (callerPanel != null)
                        callerPanel.redrawTable(shoppingList);

                    dispose();
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                JOptionPane.showMessageDialog(null, "Inserisci una ricetta valida");
            }
        }
    }
}
