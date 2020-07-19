package graphicalinterface;

import domainclasses.recipes.IngredientQty;
import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class RecipeDetailPanel extends JFrame implements ActionListener {

    private JPanel mainpanelModifiable, mainpanelUnmodifiable;

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
    private ArrayList<Recipe> recipeList;
    private JTable recipeTable;

    public RecipeDetailPanel(Recipe recipe, boolean newrecipe, ArrayList<Recipe> recipeList, JTable recipeTable) {
        super(recipe.getName());
        this.recipe = recipe;
        this.newrecipe = newrecipe;
        this.recipeList = recipeList;
        this.recipeTable = recipeTable;

        mainpanelUnmodifiable = new JPanel(new MigLayout("fill, wrap 4", "[grow,fill][200,grow,fill][300,grow,fill][]", "[][][grow,fill][][][][]"));
        mainpanelModifiable = new JPanel(new MigLayout("fill, wrap 5", "[grow,fill][grow,fill][200,grow,fill][300,grow,fill][]", "[][][grow,fill][][][]"));

        Object[][] ingredientMatrix = IngredientQty.toMatrix(recipe.getIngredients());

        /*Unmodifiable Start*/

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

        addShoppingButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        modifyButton.setPreferredSize(new Dimension(175, 50));

        mainpanelUnmodifiable.add(nameUnmodifiable, "span 3 1, grow");
        mainpanelUnmodifiable.add(new JLabel(""), "wrap");
        mainpanelUnmodifiable.add(portionsUnmodifiable, "span 3 1");
        mainpanelUnmodifiable.add(new JLabel(""), "wrap");
        mainpanelUnmodifiable.add(ingredientsUnmodifiable, "span 1 4, grow");
        mainpanelUnmodifiable.add(procedureUnmodifiable, "span 2 4, grow");
        mainpanelUnmodifiable.add(new JLabel(""), "wrap");
        mainpanelUnmodifiable.add(addShoppingButton, "wrap");
        mainpanelUnmodifiable.add(modifyButton, "wrap");
        mainpanelUnmodifiable.add(removeButton, "wrap");
        mainpanelUnmodifiable.add(kcalUnmodifiable);
        mainpanelUnmodifiable.add(prepUnmodifiable);
        mainpanelUnmodifiable.add(cookUnmodifiable);
        mainpanelUnmodifiable.add(new JLabel(""));

        portionsUnmodifiable.add(new JLabel("Ingredienti per "));
        portionsUnmodifiable.add(portionsUnmodifiableValue, "grow");
        portionsUnmodifiable.add(new JLabel(" porzioni:"));

        procedureTextUnmodifiable.setEditable(false);
        procedureTextUnmodifiable.setLineWrap(true);

        /*Unmodifiable end*/

        /*Modifiable start*/

        DefaultTableModel ingredientModelModifiable = new DefaultTableModel(ingredientMatrix, new String[]{"Id", "Nome", "Qta", "kCal"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 2)
                    return true;
                else
                    return false;
            }
        };

        ingredientsTableModifiable = new JTable(ingredientModelModifiable);

        ingredientsTableModifiable.getColumnModel().getColumn(0).setPreferredWidth(40);
        ingredientsTableModifiable.getColumnModel().getColumn(1).setPreferredWidth(150);
        ingredientsTableModifiable.getColumnModel().getColumn(2).setPreferredWidth(50);
        ingredientsTableModifiable.getColumnModel().getColumn(3).setPreferredWidth(50);

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

        acceptButton.setPreferredSize(new Dimension(175, 50));
        cancelButton.setPreferredSize(new Dimension(175, 50));
        addIngredientButton.setPreferredSize(new Dimension(50, 25));
        removeIngredientButton.setPreferredSize(new Dimension(50, 25));

        mainpanelModifiable.add(nameModifiable, "span 4 1,grow");
        mainpanelModifiable.add(new JLabel("2"));
        mainpanelModifiable.add(portionsModifiable, "span 4 1,grow");
        mainpanelModifiable.add(new JLabel("4"));
        mainpanelModifiable.add(ingredientsModifiable, "span 2 3, grow");
        mainpanelModifiable.add(procedureModifiable, "span 2 3, grow");
        mainpanelModifiable.add(new JLabel("7"));
        mainpanelModifiable.add(acceptButton);
        mainpanelModifiable.add(cancelButton);
        mainpanelModifiable.add(addIngredientButton);
        mainpanelModifiable.add(removeIngredientButton);
        mainpanelModifiable.add(prepModifiable);
        mainpanelModifiable.add(cookModifiable);
        mainpanelModifiable.add(new JLabel("14"));

        prepModifiable.add(new JLabel("T. Preparazione: "));
        prepModifiableValue = new JTextField(String.valueOf(recipe.getPreptime()));
        prepModifiable.add(prepModifiableValue, "grow");
        prepModifiable.add(new JLabel(" min"));

        cookModifiable.add(new JLabel("T. Cottura: "));
        cookModifiableValue = new JTextField(String.valueOf(recipe.getCooktime()));
        cookModifiable.add(cookModifiableValue, "grow");
        cookModifiable.add(new JLabel(" min"));

        procedureTextModifiable.setLineWrap(true);

        /*Modifiable end*/

        /* JFrame methods called */
        if (newrecipe)
            setContentPane(mainpanelModifiable);
        else
            setContentPane(mainpanelUnmodifiable);
        setLocation(400, 230);
        setSize(1000, 600);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            setContentPane(mainpanelModifiable);
            revalidate();
            repaint();
        }
        if (e.getSource() == removeButton) {

        }
        if (e.getSource() == acceptButton) {
            setContentPane(mainpanelUnmodifiable);
            recipe.setProcedure(procedureTextModifiable.getText());
            procedureTextUnmodifiable.setText(recipe.getProcedure());
            if (newrecipe)
                recipeList.add(recipe);

            Object[][] recipeMatrix = Recipe.toMatrix(recipeList);

            DefaultTableModel recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"ID", "Nome", "kCal", "T. Preparazione", "T. Cottura"}) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            recipeTable = new JTable(recipeModel);
            revalidate();
            repaint();
        }
        if (e.getSource() == cancelButton) {
            if (newrecipe)
                dispose();
            else {
                setContentPane(mainpanelUnmodifiable);
                revalidate();
                repaint();
            }
        }
    }
}
