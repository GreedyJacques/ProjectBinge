package graphicalinterface;

import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecipePanel extends JPanel implements ActionListener {

    public RecipePanel() {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]", "50[][]20[grow,fill][][][]50"));

        JButton addButton = new JButton("ADD");
        JButton removeButton = new JButton("REMOVE");
        JButton openButton = new JButton("OPEN");

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        openButton.setPreferredSize(new Dimension(175, 50));

        /*Test recipe list*/
        ArrayList<Recipe> testRecipeList = new ArrayList<>();

        testRecipeList.add(new Recipe());
        testRecipeList.add(new Recipe());
        testRecipeList.add(new Recipe());
        /*end test*/

        Object[][] recipeMatrix = new Object[testRecipeList.size()][5];

        for (int i = 0; i < testRecipeList.size(); ++i) {
            recipeMatrix[i][0] = testRecipeList.get(i).getName();
            recipeMatrix[i][1] = 1000;
            recipeMatrix[i][2] = testRecipeList.get(i).getPreptime();
            recipeMatrix[i][3] = testRecipeList.get(i).getCooktime();
            recipeMatrix[i][4] = testRecipeList.get(i).getId();
        }

        DefaultTableModel recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"Nome", "kCal", "Tempo Preparazione", "Tempo Cottura"});

        JTable recipeTable = new JTable(recipeModel);
        JScrollPane scrollPanel = new JScrollPane(recipeTable);
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Ricette"));

        add(new JLabel("Cerca:"), "right");
        add(new JTextField(""), "");
        add(new JButton("Filtri"));
        add(new JLabel("Ordina Per:"), "top");
        add(new JPanel(new GridLayout(1, 8)));
        add(new JLabel(""));
        add(scrollPanel, "span 2 4, grow");
        add(new JLabel(""), "");
        add(addButton, "right");
        add(removeButton, "right");
        add(openButton, "right");

        ListSelectionModel model = recipeTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (! model.isSelectionEmpty()){
                    int selectedRow = model.getMinSelectionIndex();
                    JOptionPane.showMessageDialog(null,"Selected row " + selectedRow);
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
