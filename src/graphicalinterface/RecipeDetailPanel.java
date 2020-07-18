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
    private JButton removeButton, modifyButton, acceptButton, cancelButton;

    private JLabel procedureUnmodifiable;
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

        mainpanelUnmodifiable = new JPanel(new MigLayout("fill, wrap 3", "50[grow,fill]20[grow,fill]20[]", "50[][][]50"));
        mainpanelModifiable = new JPanel(new MigLayout("fill, wrap 3", "50[grow,fill]20[grow,fill]20[]", "50[][][]50"));


        removeButton = new JButton("RIMUOVI");
        modifyButton = new JButton("MODIFICA");
        acceptButton = new JButton("ACCETTA");
        cancelButton = new JButton("ANNULLA");
        procedureUnmodifiable = new JLabel(recipe.getProcedure());
        procedureModifiable = new JTextArea(recipe.getProcedure());

        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);
        acceptButton.addActionListener(this);
        cancelButton.addActionListener(this);

        removeButton.setPreferredSize(new Dimension(175, 50));
        modifyButton.setPreferredSize(new Dimension(175, 50));
        acceptButton.setPreferredSize(new Dimension(175, 50));
        cancelButton.setPreferredSize(new Dimension(175, 50));


        mainpanelUnmodifiable.add(new JTextPane(), "span 1 3, grow");
        mainpanelUnmodifiable.add(procedureUnmodifiable, "span 1 3, grow");
        mainpanelUnmodifiable.add(new JLabel(""));
        mainpanelUnmodifiable.add(removeButton, "right");
        mainpanelUnmodifiable.add(modifyButton, "right");

        mainpanelModifiable.add(new JTextPane(), "span 1 3, grow");
        mainpanelModifiable.add(procedureModifiable, "span 1 3, grow");
        mainpanelModifiable.add(new JLabel(""));
        mainpanelModifiable.add(acceptButton, "right");
        mainpanelModifiable.add(cancelButton, "right");

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
            procedureUnmodifiable.setText(recipe.getProcedure());
            if(newrecipe)
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
