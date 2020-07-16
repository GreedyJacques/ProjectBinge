package graphicalinterface;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingPanel extends JPanel implements ActionListener {

    public ShoppingPanel(){

        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]","50[grow, fill][][][][]50"));


        JButton addButton = new JButton("AGGIUNGI");
        JButton removeButton = new JButton("RIMUOVI");
        JButton modifyButton = new JButton("MODIFICA");
        JButton addfromrecipeButton = new JButton("AGGIUNGI DA RICETTA");

        addButton.setPreferredSize(new Dimension(175,50));
        removeButton.setPreferredSize(new Dimension(175,50));
        modifyButton.setPreferredSize(new Dimension(175,50));
        addfromrecipeButton.setPreferredSize(new Dimension(175,50));

        add(new JTextPane(),"span 1 5, grow");
        add(new JLabel(""));
        add(addButton,"right");
        add(addfromrecipeButton, "right");
        add(removeButton,"right");
        add(modifyButton,"right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
