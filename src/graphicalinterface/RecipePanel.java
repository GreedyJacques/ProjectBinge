package graphicalinterface;

import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
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

    private ArrayList<Recipe> recipeList;

    JTable recipeTable;

    Recipe selectedRecipe;

    DefaultTableModel recipeModel;

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

        removeButton.addActionListener(this);
        openButton.addActionListener(this);
        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        searchBar.addKeyListener(this);

        /*Test recipe list*/
        recipeList = new ArrayList<>();

        Recipe a = new Recipe();
        a.setId(25);
        a.setName("Lasagna");
        a.setProcedure("In realta non so come si fanno");

        Recipe b = new Recipe();
        b.setId(10);
        b.setName("Pizza");
        b.setProcedure("Questa la sa fare Laura");

        for (int i = 0; i < 1000; ++i)
            recipeList.add(new Recipe());

        recipeList.add(a);
        recipeList.add(b);


        /*end test*/


        Object[][] recipeMatrix = Recipe.toMatrix(recipeList);


        recipeModel = new DefaultTableModel(recipeMatrix, new String[]{"ID", "Nome", "kCal", "T. Preparazione", "T. Cottura"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        recipeTable = new JTable(recipeModel);
        JScrollPane scrollPanel = new JScrollPane(recipeTable);

        recipeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        recipeTable.getColumnModel().getColumn(1).setPreferredWidth(1500);
        recipeTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        recipeTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        recipeTable.getColumnModel().getColumn(4).setPreferredWidth(200);


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
                    Object selectedId = recipeTable.getValueAt(row, 0);
                    selectedRecipe = Recipe.findRecipe(recipeList, (int) selectedId);
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

        if (e.getSource() == filterButton) {
        }


        if (e.getSource() == addButton) {
            Recipe newRecipe = new Recipe(Recipe.getMaxId(recipeList) + 1);
            new RecipeDetailPanel(newRecipe, true, recipeList, recipeTable);
            //TODO
        }


        if (e.getSource() == removeButton) {
            if (selectedRecipe != null) {
                int row = recipeTable.getSelectedRow();
                Object selectedId = recipeTable.getValueAt(row, 0);
                recipeList.remove(Recipe.findRecipe(recipeList, (int) selectedId));
                recipeModel.removeRow(row);
            } else
                return;
        }

        if (e.getSource() == openButton) {
            if (selectedRecipe != null)
                new RecipeDetailPanel(selectedRecipe, false, recipeList, recipeTable);
            else
                return;
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
