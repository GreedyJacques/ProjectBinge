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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

public class RecipePanel extends JPanel implements ActionListener, KeyListener {

    private JButton addButton, removeButton,
            openButton, filterButton, searchButton;

    private JTextField searchbar;

    public RecipePanel() {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]", "50[][]20[grow,fill][]150[][][]50"));

        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        openButton = new JButton("APRI");
        filterButton = new JButton("FILTRI");
        searchButton = new JButton("CERCA");

        searchbar = new JTextField("");

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        openButton.setPreferredSize(new Dimension(175, 50));
        filterButton.setPreferredSize(new Dimension(175, 50));


        searchButton.addActionListener(this);
        searchbar.addKeyListener(this);

        /*Test recipe list*/
        ArrayList<Recipe> testRecipeList = new ArrayList<>();

        testRecipeList.add(new Recipe());
        testRecipeList.add(new Recipe(5));
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

        DefaultTableModel recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"Nome", "kCal", "Tempo Preparazione", "Tempo Cottura", "ID"});

        JTable recipeTable = new JTable(recipeModel);
        JScrollPane scrollPanel = new JScrollPane(recipeTable);
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Ricette"));

        add(new JLabel("Cerca:"), "right");
        add(searchbar, "");
        add(searchButton);
        add(new JLabel("Ordina Per:"), "top");
        add(new JPanel(new GridLayout(1, 8)));
        add(new JLabel(""));
        add(scrollPanel, "span 2 5, grow");
        add(new JLabel(""), "");
        add(filterButton, "right");
        add(addButton, "right");
        add(removeButton, "right");
        add(openButton, "right");

        ListSelectionModel model = recipeTable.getSelectionModel();
        model.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!model.isSelectionEmpty()) {
                    int row = recipeTable.getSelectedRow();
                    Object selectedrecipe = recipeTable.getValueAt(row, 4);
                    JOptionPane.showMessageDialog(null, "Selected ID " + selectedrecipe);
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String searchedthing = searchbar.getText();
            JOptionPane.showMessageDialog(null, "you searched for: " + searchedthing);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        String searchedthing = searchbar.getText();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "you searched for: " + searchedthing);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
