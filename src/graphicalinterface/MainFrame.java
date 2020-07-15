package graphicalinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    public MainFrame() {
        super("Project Binge");
        JTabbedPane mainpanel = new JTabbedPane();
        RecipePanel recipepanel = new RecipePanel();
        ShoppingPanel shoppingpanel = new ShoppingPanel();
        JPanel inventorypanel = new JPanel();

        JMenuBar menuBar= new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem openFile = new JMenuItem("Apri");
        JMenuItem saveFile = new JMenuItem("Salva");

        file.add(openFile);
        file.add(saveFile);

        menuBar.add(file);

        setJMenuBar(menuBar);

        mainpanel.addTab("Ricette", recipepanel);
        mainpanel.addTab("Lista Spesa", shoppingpanel);
        mainpanel.addTab("Inventario", inventorypanel);

        /* JFrame methods called */
        setContentPane(mainpanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(350, 180);
        setSize(1280, 720);
        setMinimumSize(new Dimension(1280,720));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO
    }
}
