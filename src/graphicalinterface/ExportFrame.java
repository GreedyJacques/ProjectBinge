package graphicalinterface;

import domainclasses.recipes.IngredientQty;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExportFrame extends JFrame implements ActionListener {
    private JPanel mainpanel;

    private JTextArea shoppingIngredient;
    private JScrollPane scrollShopping;

    private JButton copyButton;

    public ExportFrame(ArrayList<IngredientQty> shoppingList){
        super("Lista Della Spesa");

        mainpanel= new JPanel(new MigLayout("fill, wrap 1", "[grow, fill]", "[grow, fill][]"));
        copyButton=new JButton("COPIA NELLA CLIPBOARD");
        shoppingIngredient= new JTextArea(ShoppingPanel.getShoppingIngredient(shoppingList));

        copyButton.addActionListener(this);

        copyButton.setPreferredSize(new Dimension(50, 50));

        shoppingIngredient.setEditable(false);

        scrollShopping=new JScrollPane(shoppingIngredient);

        mainpanel.add(scrollShopping);
        mainpanel.add(copyButton);

        setContentPane(mainpanel);

        setLocation(400, 230);
        setSize(400, 600);
        setVisible(true);





    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==copyButton){

        }

    }
}
