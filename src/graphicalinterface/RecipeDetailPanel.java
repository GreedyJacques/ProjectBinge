package graphicalinterface;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeDetailPanel extends JFrame implements ActionListener {
    private JButton  removeButton, modifyButton;

    public RecipeDetailPanel(String title){
        super(title);
        JPanel mainpanel = new JPanel(new MigLayout("fill, wrap 3", "50[grow,fill]20[grow,fill]20[]","50[][][]50"));


        removeButton = new JButton("RIMUOVI");
        modifyButton = new JButton("MODIFICA");



        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);



        removeButton.setPreferredSize(new Dimension(175,50));
        modifyButton.setPreferredSize(new Dimension(175,50));


        mainpanel.add(new JTextPane(),"span 1 3, grow");
        mainpanel.add(new JTextPane(),"span 1 3, grow");
        mainpanel.add(new JLabel(""));

        mainpanel.add(removeButton,"right");
        mainpanel.add(modifyButton,"right");

        /* JFrame methods called */
        setContentPane(mainpanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(400, 230);
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280,720));
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
