package graphicalinterface;

import domainclasses.recipes.IngredientQty;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ExportFrame extends JFrame implements ActionListener {
    private JPanel mainpanel;

    private JTextArea shoppingIngredients;
    private JScrollPane scrollShopping;

    private JButton copyButton;

    public ExportFrame(ArrayList<IngredientQty> shoppingList) {
        super("Lista Della Spesa");

        mainpanel = new JPanel(new MigLayout("fill, wrap 1", "[grow, fill]", "[grow, fill][]"));
        copyButton = new JButton("COPIA NELLA CLIPBOARD");
        shoppingIngredients = new JTextArea(ShoppingPanel.getShoppingIngredient(shoppingList));

        copyButton.addActionListener(this);

        copyButton.setPreferredSize(new Dimension(50, 50));

        shoppingIngredients.setEditable(false);

        scrollShopping = new JScrollPane(shoppingIngredients);

        mainpanel.add(scrollShopping);
        mainpanel.add(copyButton);

        setContentPane(mainpanel);

        setLocation(400, 230);
        setSize(400, 600);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == copyButton) {
            StringSelection stringSelection = new StringSelection(shoppingIngredients.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }

    }
}
