package graphicalinterface;

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
    private JButton acceptButton, cancelButton;

    private JLabel nameUnmodifiable; //1
    private JPanel portionsUnmodifiable; //3
    private JScrollPane ingredientsUnmodifiable; //4
    private JTable ingredientsTableUnmodifiable; //4
    private JScrollPane procedureUnmodifiable; //5
    private JTextArea procedureTextUnmodifiable; //5
    private JButton addShoppingButton; //8
    private JButton modifyButton; //9
    private JButton removeButton; //10
    private JLabel kcalUnmodifiable; //11
    private JLabel prepUnmodifiable; //12
    private JLabel cookUnmodifiable; //13

    private JTextArea procedureModifiable;

    private JPanel mainpanelModifiable, mainpanelUnmodifiable;

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

        mainpanelUnmodifiable = new JPanel(new MigLayout("fill, wrap 4", "[grow,fill][grow,fill][grow,fill][]", "[][][grow,fill][][][][]"));
        mainpanelModifiable = new JPanel(new MigLayout("fill, wrap 3", "50[grow,fill]20[grow,fill]20[]", "50[][][]50"));

        Object[][] ingredientMatrix = new Object[recipe.getIngredients().size()][3];

        DefaultTableModel ingredientModel = new DefaultTableModel(ingredientMatrix, new String[]{"Nome", "Qta", "kCal"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ingredientsTableUnmodifiable = new JTable(ingredientModel);

        ingredientsTableUnmodifiable.getColumnModel().getColumn(0).setPreferredWidth(5);
        ingredientsTableUnmodifiable.getColumnModel().getColumn(1).setPreferredWidth(5);
        ingredientsTableUnmodifiable.getColumnModel().getColumn(2).setPreferredWidth(5);


        nameUnmodifiable = new JLabel(recipe.getName()); //1
        portionsUnmodifiable = new JPanel(new GridLayout(1,3)); //3
        ingredientsUnmodifiable = new JScrollPane(ingredientsTableUnmodifiable); //4
        procedureTextUnmodifiable = new JTextArea(recipe.getProcedure()); //5
        procedureUnmodifiable = new JScrollPane(procedureTextUnmodifiable); //5
        addShoppingButton = new JButton("AGGIUNGI A SPESA"); //8
        modifyButton = new JButton("MODIFICA"); //9
        removeButton = new JButton("RIMUOVI"); //10
        kcalUnmodifiable = new JLabel("kCal totali: " + recipe.getKcal()); //11
        prepUnmodifiable = new JLabel("T. Preparazione: " + recipe.getPreptime()); //12
        cookUnmodifiable = new JLabel("T. Cottura: " + recipe.getCooktime()); //13

        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);

        addShoppingButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        modifyButton.setPreferredSize(new Dimension(175, 50));

        mainpanelUnmodifiable.add(nameUnmodifiable, "span 3 1, grow");
        mainpanelUnmodifiable.add(new JLabel("2"), "wrap");
        mainpanelUnmodifiable.add(portionsUnmodifiable, "span 3 1");
        mainpanelUnmodifiable.add(new JLabel("4"), "wrap");
        mainpanelUnmodifiable.add(ingredientsUnmodifiable, "span 1 4, grow");
        mainpanelUnmodifiable.add(procedureUnmodifiable, "span 2 4, grow");
        mainpanelUnmodifiable.add(new JLabel("7"), "wrap");
        mainpanelUnmodifiable.add(addShoppingButton, "wrap");
        mainpanelUnmodifiable.add(modifyButton, "wrap");
        mainpanelUnmodifiable.add(removeButton, "wrap");
        mainpanelUnmodifiable.add(kcalUnmodifiable);
        mainpanelUnmodifiable.add(prepUnmodifiable);
        mainpanelUnmodifiable.add(cookUnmodifiable);
        mainpanelUnmodifiable.add(new JLabel("14"));


        /* JFrame methods called */
        if (newrecipe)
            setContentPane(mainpanelModifiable);
        else
            setContentPane(mainpanelUnmodifiable);
        setLocation(400, 230);
        setSize(500, 500);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyButton) {
            setContentPane(mainpanelModifiable);
        }
        if (e.getSource() == removeButton) {

        }
        if (e.getSource() == acceptButton) {
            setContentPane(mainpanelUnmodifiable);
            recipe.setProcedure(procedureModifiable.getText());
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
        }
        if (e.getSource() == cancelButton) {
            if (newrecipe)
                dispose();
            else
                setContentPane(mainpanelUnmodifiable);
        }
    }
}
