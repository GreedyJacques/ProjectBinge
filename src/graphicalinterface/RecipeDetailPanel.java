package graphicalinterface;

import domainclasses.recipes.Recipe;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class RecipeDetailPanel extends JFrame implements ActionListener {
    private JButton  removeButton, modifyButton;

    public RecipeDetailPanel(Recipe recipe){
        super(recipe.getName());
        JPanel mainpanel = new JPanel(new MigLayout("fill, wrap 3", "50[grow,fill]20[grow,fill]20[]","50[][][]50"));


        removeButton = new JButton("RIMUOVI");
        modifyButton = new JButton("MODIFICA");



        removeButton.addActionListener(this);
        modifyButton.addActionListener(this);



        removeButton.setPreferredSize(new Dimension(175,50));
        modifyButton.setPreferredSize(new Dimension(175,50));


        mainpanel.add(new JTextPane(),"span 1 3, grow");
        mainpanel.add(new JTextArea(recipe.getProcedure()),"span 1 3, grow");
        mainpanel.add(new JLabel(""));

        mainpanel.add(removeButton,"right");
        mainpanel.add(modifyButton,"right");

        /* JFrame methods called */
        setContentPane(mainpanel);
        setLocation(400, 230);
        setSize(500, 500);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
