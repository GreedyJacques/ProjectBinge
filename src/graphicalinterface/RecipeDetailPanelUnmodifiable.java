package graphicalinterface;

import domainclasses.recipes.Ingredient;
import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecipeDetailPanelUnmodifiable extends JPanel implements ActionListener {

    private JLabel nameUnmodifiable; //1
    private JPanel portionsUnmodifiable; //3
    private JTextField portionsUnmodifiableValue; //3
    private JScrollPane ingredientsUnmodifiable; //5
    private JTable ingredientsTableUnmodifiable; //5
    private JScrollPane procedureUnmodifiable; //6
    private JTextArea procedureTextUnmodifiable; //6
    private JButton addShoppingButton; //8
    private JButton modifyButton; //9
    private JButton removeButton; //10
    private JLabel kcalUnmodifiable; //11
    private JLabel prepUnmodifiable; //12
    private JLabel cookUnmodifiable; //13

    private Recipe recipe;
    private ArrayList<Recipe> recipeList;
    private JTable recipeTable;
    private RecipeDetailFrame callerFrame;
    ArrayList<Ingredient> ingredientList;
    ArrayList<IngredientQty> shoppingList;

    public RecipeDetailPanelUnmodifiable(Recipe recipe, ArrayList<Recipe> recipeList, JTable recipeTable, RecipeDetailFrame callerFrame, ArrayList<Ingredient> ingredientList, ArrayList<IngredientQty> shoppingList) {
        super(new MigLayout("fill, wrap 4", "[grow,fill][200,grow,fill][300,grow,fill][]", "[][][grow,fill][][][][]"));
        this.recipe = recipe;
        this.recipeList = recipeList;
        this.recipeTable = recipeTable;
        this.callerFrame = callerFrame;
        this.ingredientList = ingredientList;
        this.shoppingList = shoppingList;

        Object[][] ingredientMatrix = IngredientQty.toMatrix(recipe.getIngredients());

        DefaultTableModel ingredientModelUnmodifiable = new DefaultTableModel(ingredientMatrix, new String[]{"Id", "Nome", "Qta", "kCal"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ingredientsTableUnmodifiable = new JTable(ingredientModelUnmodifiable);

        ingredientsTableUnmodifiable.getColumnModel().getColumn(0).setPreferredWidth(40);
        ingredientsTableUnmodifiable.getColumnModel().getColumn(1).setPreferredWidth(150);
        ingredientsTableUnmodifiable.getColumnModel().getColumn(2).setPreferredWidth(50);
        ingredientsTableUnmodifiable.getColumnModel().getColumn(3).setPreferredWidth(50);

        nameUnmodifiable = new JLabel(recipe.getName(), SwingConstants.CENTER); //1
        portionsUnmodifiable = new JPanel(new MigLayout("", "0[]0[30]0[]0", "0[]0")); //3
        portionsUnmodifiableValue = new JTextField("1");//3
        ingredientsUnmodifiable = new JScrollPane(ingredientsTableUnmodifiable); //5
        procedureTextUnmodifiable = new JTextArea(recipe.getProcedure()); //6
        procedureUnmodifiable = new JScrollPane(procedureTextUnmodifiable); //6
        addShoppingButton = new JButton("AGGIUNGI A SPESA"); //8
        modifyButton = new JButton("MODIFICA"); //9
        removeButton = new JButton("RIMUOVI"); //10
        kcalUnmodifiable = new JLabel("kCal totali: " + recipe.getKcal()); //11
        prepUnmodifiable = new JLabel("T. Preparazione: " + recipe.getPreptime()); //12
        cookUnmodifiable = new JLabel("T. Cottura: " + recipe.getCooktime()); //13

        addShoppingButton.addActionListener(this);
        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);

        portionsUnmodifiableValue.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                boolean correct = true;
                int portions = 1;
                try {
                    portions = Integer.parseInt(portionsUnmodifiableValue.getText());
                } catch (NumberFormatException exception) {
                    correct = false;
                }
                if (correct && portions > 0) {
                    Object[][] ingredientMatrix = IngredientQty.toMatrix(recipe.getIngredients());

                    DefaultTableModel ingredientModelUnmodifiable = new DefaultTableModel(ingredientMatrix, new String[]{"Id", "Nome", "Qta", "kCal"}) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    ingredientsTableUnmodifiable.setModel(ingredientModelUnmodifiable);

                    for (int i = 0; i < ingredientsTableUnmodifiable.getRowCount(); ++i) {
                        ingredientsTableUnmodifiable.setValueAt((recipe.getIngredients().get(i).getQty() * portions) + " " + recipe.getIngredients().get(i).stringType(), i, 2);
                        ingredientsTableUnmodifiable.setValueAt(recipe.getIngredients().get(i).getTotKcal() * portions, i, 3);

                    }

                    ingredientsTableUnmodifiable.getColumnModel().getColumn(0).setPreferredWidth(40);
                    ingredientsTableUnmodifiable.getColumnModel().getColumn(1).setPreferredWidth(150);
                    ingredientsTableUnmodifiable.getColumnModel().getColumn(2).setPreferredWidth(50);
                    ingredientsTableUnmodifiable.getColumnModel().getColumn(3).setPreferredWidth(50);

                    kcalUnmodifiable.setText("kCal totali: " + recipe.getKcal() * portions);
                }
            }
        });

        addShoppingButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        modifyButton.setPreferredSize(new Dimension(175, 50));

        add(nameUnmodifiable, "span 3 1, grow");
        add(new JLabel(""), "wrap");
        add(portionsUnmodifiable, "span 3 1");
        add(new JLabel(""), "wrap");
        add(ingredientsUnmodifiable, "span 1 4, grow");
        add(procedureUnmodifiable, "span 2 4, grow");
        add(new JLabel(""), "wrap");
        add(addShoppingButton, "wrap");
        add(modifyButton, "wrap");
        add(removeButton, "wrap");
        add(kcalUnmodifiable);
        add(prepUnmodifiable);
        add(cookUnmodifiable);
        add(new JLabel(""));

        portionsUnmodifiable.add(new JLabel("Ingredienti per "));
        portionsUnmodifiable.add(portionsUnmodifiableValue, "grow");
        portionsUnmodifiable.add(new JLabel(" porzioni:"));

        procedureTextUnmodifiable.setEditable(false);
        procedureTextUnmodifiable.setLineWrap(true);

        ingredientsTableUnmodifiable.setAutoCreateRowSorter(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            callerFrame.setContentPane(new RecipeDetailPanelModifiable(recipe, false, recipeList, recipeTable, callerFrame, ingredientList, shoppingList));
            callerFrame.revalidate();
            callerFrame.repaint();
        }
        if (e.getSource() == removeButton) {
            int selectedId = recipe.getId();
            recipeList.remove(Recipe.findRecipe(recipeList, selectedId));

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

            callerFrame.dispose();
        }
        if (e.getSource() == addShoppingButton) {

        }
    }
}