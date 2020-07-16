package graphicalinterface;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryPanel extends JPanel implements ActionListener {

    public InventoryPanel(){
        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]","50[grow, fill][][][]50"));

        JButton addButton = new JButton("AGGIUNGI");
        JButton removeButton = new JButton("RIMUOVI");
        JButton modifyButton = new JButton("MODIFICA");

        addButton.setPreferredSize(new Dimension(175,50));
        removeButton.setPreferredSize(new Dimension(175,50));
        modifyButton.setPreferredSize(new Dimension(175,50));



        add(new JTextPane(),"span 1 4, grow");
        add(new JLabel(""));
        add(addButton,"right");
        add(removeButton,"right");
        add(modifyButton,"right");


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
