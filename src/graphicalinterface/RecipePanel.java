package graphicalinterface;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipePanel extends JPanel implements ActionListener {

    public RecipePanel() {
        super(new MigLayout("fill, wrap 3", "50[][grow,fill]20[]","50[][]20[grow,fill][][][]50"));

        JButton addButton = new JButton("ADD");
        JButton removeButton = new JButton("REMOVE");
        JButton openButton = new JButton("OPEN");

        addButton.setPreferredSize(new Dimension(150,50));
        removeButton.setPreferredSize(new Dimension(150,50));
        openButton.setPreferredSize(new Dimension(150,50));

        add(new JLabel("Cerca:"),"right");
        add(new JTextField(""), "");
        add(new JButton("Filtri"));
        add(new JLabel("Ordina Per:"), "top");
        add(new JPanel(new GridLayout(1,8)));
        add(new JLabel(""));
        add(new JTextPane(),"span 2 4,grow");
        add(new JLabel(""),"grow");
        add(addButton,"right");
        add(removeButton,"right");
        add(openButton,"right");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
