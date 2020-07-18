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

    private JTextField searchBar;

    public RecipePanel() {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]", "50[][]20[grow,fill][]150[][][]50"));

        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        openButton = new JButton("APRI");
        filterButton = new JButton("FILTRI");
        searchButton = new JButton("CERCA");

        searchBar = new JTextField("");

        addButton.setPreferredSize(new Dimension(175, 50));
        removeButton.setPreferredSize(new Dimension(175, 50));
        openButton.setPreferredSize(new Dimension(175, 50));
        filterButton.setPreferredSize(new Dimension(75, 30));
        searchButton.setPreferredSize(new Dimension(75, 30));

        searchButton.addActionListener(this);
        searchBar.addKeyListener(this);

        /*Test recipe list*/
        ArrayList<Recipe> testRecipeList = new ArrayList<>();

        testRecipeList.add(new Recipe());
        testRecipeList.add(new Recipe(5));
        testRecipeList.add(new Recipe());
        /*end test*/

        Object[][] recipeMatrix = Recipe.toMatrix(testRecipeList);


        DefaultTableModel recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"ID", "Nome", "kCal", "Tempo Preparazione", "Tempo Cottura"});

        JTable recipeTable = new JTable(recipeModel);
        JScrollPane scrollPanel = new JScrollPane(recipeTable);
        scrollPanel.setBorder(BorderFactory.createTitledBorder("Ricette"));

        add(new JLabel("Cerca:"), "right");
        add(searchBar, "");
        add(searchButton, "left");
        add(new JLabel("Ordina Per:"), "top");
        add(new JPanel(new GridLayout(1, 8)));
        add(filterButton, "left");
        add(scrollPanel, "span 2 5, grow");
        add(new JLabel(""), "");
        add(new JLabel(""));
        add(addButton, "right");
        add(removeButton, "right");
        add(openButton, "right");

        ListSelectionModel selectionModel = recipeTable.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!selectionModel.isSelectionEmpty()) {
                    int row = recipeTable.getSelectedRow();
                    Object selectedrecipe = recipeTable.getValueAt(row, 0);
                    JOptionPane.showMessageDialog(null, "Selected ID " + selectedrecipe);
                }
            }
        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String searchedThing = searchBar.getText();
            JOptionPane.showMessageDialog(null, "you searched for: " + searchedThing);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        String searchedThing = searchBar.getText();

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "you searched for: " + searchedThing);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
