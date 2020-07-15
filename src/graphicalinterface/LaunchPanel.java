package graphicalinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPanel extends JFrame implements ActionListener {
    private JButton btnOne, btntwo, btnthree, btnfour;


    public LaunchPanel() {
        super("Prova divertente");
        btnOne=new JButton("RICETTE");
        btnOne.addActionListener(this);
        btntwo=new JButton("LISTA SPESA");
        btntwo.addActionListener(this);
        btntwo.addActionListener(this);
        btnthree=new JButton("COSA FARE");
        btnthree.addActionListener(this);
        btnthree.addActionListener(this);
        btnfour=new JButton("INVENTARIO");
        btnfour.addActionListener(this);
        btnfour.addActionListener(this);
        JPanel p1 = new JPanel(new BorderLayout());
        JPanel p2 = new JPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        JTextPane panel1 = new JTextPane();
        tabbedPane.addTab("tab 1", panel1);



        /* JFrame methods called */
        setContentPane(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(900,400);
        setSize(350, 75);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== this.btnOne){
            new LaunchPanel();
        }
    }




}
