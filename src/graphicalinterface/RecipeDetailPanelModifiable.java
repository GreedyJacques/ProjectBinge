package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import launchcode.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecipeDetailPanelModifiable extends JPanel implements ActionListener, TablePanel {

    private JTextField nameModifiable; //1
    private JLabel portionsModifiable; //3
    private JTable ingredientsTableModifiable; //5
    private JScrollPane ingredientsModifiable; //5
    private JTextArea procedureTextModifiable; //6
    private JScrollPane procedureModifiable; //6
    private JButton acceptButton; //8
    private JButton cancelButton; //9
    private JButton addIngredientButton; //10
    private JButton removeIngredientButton; //11
    private JTextField prepModifiableValue; //12
    private JPanel prepModifiable; //12
    private JTextField cookModifiableValue; //13
    private JPanel cookModifiable; //13

    private boolean newrecipe;
    private Recipe recipe;
    private ArrayList<IngredientQty> ingredientListNew;
    private ArrayList<Recipe> recipeList;
    private JTable recipeTable;
    private RecipeDetailFrame callerFrame;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;
    DefaultTableModel ingredientModelModifiable;
    ShoppingPanel shoppingPanel;

    public RecipeDetailPanelModifiable(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable, RecipeDetailFrame callerFrame, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList, ShoppingPanel shoppingPanel) {
        super(new MigLayout("fill, wrap 5", "[grow,fill][grow,fill][200,grow,fill][300,grow,fill][]", "[][][grow,fill][][][]"));


        this.recipe = recipe;
        this.newrecipe = newrecipe;
        this.recipeList = recipeList;
        this.recipeTable = recipeTable;
        this.callerFrame = callerFrame;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;
        this.shoppingPanel = shoppingPanel;

        ingredientListNew = (ArrayList) recipe.getIngredients().clone();
        ingredientsTableModifiable = new JTable();

        redrawTable(ingredientListNew);

        nameModifiable = new JTextField(recipe.getName()); //1
        portionsModifiable = new JLabel("Ingredienti per 1 porzione:"); //3
        ingredientsModifiable = new JScrollPane(ingredientsTableModifiable); //5
        procedureTextModifiable = new JTextArea(recipe.getProcedure()); //6
        procedureModifiable = new JScrollPane(procedureTextModifiable); //6
        acceptButton = new JButton("CONFERMA"); //8
        cancelButton = new JButton("ANNULLA"); //9
        addIngredientButton = new JButton("AGGIUNGI"); //10
        removeIngredientButton = new JButton("RIMUOVI"); //11
        prepModifiable = new JPanel(new MigLayout("", "0[]0[30]0[]0", "0[]0")); //12
        cookModifiable = new JPanel(new MigLayout("", "0[]0[30]0[]0", "0[]0")); //13

        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);
        addIngredientButton.addActionListener(this);
        removeIngredientButton.addActionListener(this);

        acceptButton.setMinimumSize(new Dimension(175, 50));
        cancelButton.setMinimumSize(new Dimension(175, 50));
        addIngredientButton.setMinimumSize(new Dimension(50, 25));
        removeIngredientButton.setMinimumSize(new Dimension(50, 25));

        add(nameModifiable, "span 4 1,grow");
        add(new JLabel(""));
        add(portionsModifiable, "span 4 1,grow");
        add(new JLabel(""));
        add(ingredientsModifiable, "span 2 3, grow");
        add(procedureModifiable, "span 2 3, grow");
        add(new JLabel(""));
        add(acceptButton);
        add(cancelButton);
        add(addIngredientButton);
        add(removeIngredientButton);
        add(prepModifiable);
        add(cookModifiable);
        add(new JLabel(""));

        prepModifiable.add(new JLabel("T. Preparazione: "));
        prepModifiableValue = new JTextField(String.valueOf(recipe.getPreptime()));
        prepModifiable.add(prepModifiableValue, "grow");
        prepModifiable.add(new JLabel(" min"));

        cookModifiable.add(new JLabel("T. Cottura: "));
        cookModifiableValue = new JTextField(String.valueOf(recipe.getCooktime()));
        cookModifiable.add(cookModifiableValue, "grow");
        cookModifiable.add(new JLabel(" min"));

        procedureTextModifiable.setLineWrap(true);
        procedureTextModifiable.setWrapStyleWord(true);

        ingredientsTableModifiable.setAutoCreateRowSorter(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == acceptButton) {
            recipe.setName(nameModifiable.getText());
            recipe.setIngredients(ingredientListNew);
            recipe.setProcedure(procedureTextModifiable.getText());
            recipe.setPreptime(Integer.parseInt(prepModifiableValue.getText()));
            recipe.setCooktime(Integer.parseInt(cookModifiableValue.getText()));

            if (newrecipe)
                recipeList.add(recipe);

            Object[][] recipeMatrix = Recipe.toMatrix(recipeList);

            DefaultTableModel recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"ID", "Nome", "kCal/porz.", "T. Preparazione", "T. Cottura", "T. Totale"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            recipeTable.setModel(recipeModel);

            recipeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
            recipeTable.getColumnModel().getColumn(1).setPreferredWidth(1500);
            recipeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
            recipeTable.getColumnModel().getColumn(3).setPreferredWidth(200);
            recipeTable.getColumnModel().getColumn(4).setPreferredWidth(200);
            recipeTable.getColumnModel().getColumn(5).setPreferredWidth(200);

            callerFrame.setContentPane(new RecipeDetailPanelUnmodifiable(recipe, recipeList, recipeTable, callerFrame, ingredientList, shoppingList, shoppingPanel));
            callerFrame.revalidate();
            callerFrame.repaint();
        }
        if (e.getSource() == cancelButton) {
            if (newrecipe)
                callerFrame.dispose();
            else {
                callerFrame.setContentPane(new RecipeDetailPanelUnmodifiable(recipe, recipeList, recipeTable, callerFrame, ingredientList, shoppingList, shoppingPanel));
                callerFrame.revalidate();
                callerFrame.repaint();
            }
        }
        if (e.getSource() == addIngredientButton) {
            new ExistingIngredientPanel(ingredientListNew, this, ingredientList);
        }
        if (e.getSource() == removeIngredientButton) {
            if (ingredientsTableModifiable.getSelectedRow()>= 0 && ingredientsTableModifiable.getSelectedRow()<ingredientsTableModifiable.getRowCount()) {
                int row = ingredientsTableModifiable.getSelectedRow();
                int selectedId = (int) ingredientsTableModifiable.getValueAt(row, 0);
                ingredientListNew.remove(IngredientQty.findIngredientQty(ingredientListNew, selectedId));
                redrawTable(ingredientListNew);
                ingredientsTableModifiable.clearSelection();
            }
        }
    }

    @Override
    public void redrawTable(ArrayList<IngredientQty> ingredientQtyList) {
        Object[][] ingredientMatrixNew = IngredientQty.toMatrix(ingredientQtyList);

        ingredientModelModifiable = new DefaultTableModel(ingredientMatrixNew, new String[]{"Id", "Nome", "Qta", "kCal"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2)
                    return true;
                else
                    return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (ingredientQtyList.isEmpty()) {
                    return Object.class;
                }
                return getValueAt(0, columnIndex).getClass();
            }
        };

        ingredientsTableModifiable.setModel(ingredientModelModifiable);

        ingredientsTableModifiable.getColumnModel().getColumn(0).setPreferredWidth(40);
        ingredientsTableModifiable.getColumnModel().getColumn(1).setPreferredWidth(150);
        ingredientsTableModifiable.getColumnModel().getColumn(2).setPreferredWidth(50);
        ingredientsTableModifiable.getColumnModel().getColumn(3).setPreferredWidth(50);

        ingredientModelModifiable.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if(ingredientsTableModifiable.getSelectedRow()>= 0 && ingredientsTableModifiable.getSelectedRow()<ingredientsTableModifiable.getRowCount()){
                    int selectedRow = ingredientsTableModifiable.getSelectedRow();
                    ingredientsTableModifiable.clearSelection();
                    String newQty = (String) ingredientsTableModifiable.getValueAt(selectedRow, 2);
                    int Qty = Main.strtoint(newQty);
                    IngredientQty tmpIngredientQty = IngredientQty.findIngredientQty(ingredientQtyList, (int) ingredientsTableModifiable.getValueAt(selectedRow, 0));
                    if (Qty > 0)
                        tmpIngredientQty.setQty(Qty);
                    ingredientsTableModifiable.setValueAt(tmpIngredientQty.getQty() + " " + tmpIngredientQty.stringType(), selectedRow, 2);
                }
            }
        });
    }
}
