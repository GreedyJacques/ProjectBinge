package graphicalinterface;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingPanel extends JPanel implements ActionListener {
    private JButton addButton, removeButton,
            modifyButton, addFromRecipeButton;

    public ShoppingPanel(){



        super(new MigLayout("fill, wrap 2", "50[grow,fill]20[]","50[grow, fill][][][][]50"));


        addButton = new JButton("AGGIUNGI");
        removeButton = new JButton("RIMUOVI");
        modifyButton = new JButton("MODIFICA");
        addFromRecipeButton = new JButton("AGGIUNGI DA RICETTA");

        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);
        addFromRecipeButton.addActionListener(this);

        addButton.setPreferredSize(new Dimension(175,50));
        removeButton.setPreferredSize(new Dimension(175,50));
        modifyButton.setPreferredSize(new Dimension(175,50));
        addFromRecipeButton.setPreferredSize(new Dimension(175,50));

        add(new JTextPane(),"span 1 5, grow");
        add(new JLabel(""));
        add(addButton,"right");
        add(addFromRecipeButton, "right");
        add(removeButton,"right");
        add(modifyButton,"right");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== addButton){

        }
        if(e.getSource()== removeButton){

        }
        if(e.getSource()== modifyButton){

        }
        if(e.getSource()== addFromRecipeButton){

        }

    }
}
