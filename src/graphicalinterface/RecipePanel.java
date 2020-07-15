package graphicalinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipePanel extends JSplitPane implements ActionListener {

    public RecipePanel() {
        super(JSplitPane.VERTICAL_SPLIT,new JPanel(),new JPanel());
        JTextPane p1 = new JTextPane();
        JButton p2 = new JButton("Ciao");

        setTopComponent(p1);
        setBottomComponent(p2);

        setDividerLocation(360);
        setDividerSize(0);

        setResizeWeight(1.0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
