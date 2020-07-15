package graphicalinterface;

import javax.swing.*;
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
        btnthree=new JButton("COSA FARE");
        btnthree.addActionListener(this);
        btnfour=new JButton("INVENTARIO");
        btnfour.addActionListener(this);
        JPanel p1 = new JPanel();

        p1.add(btnOne);
        p1.add(btntwo);
        p1.add(btnthree);
        p1.add(btnfour);


        /* JFrame methods called */
        setContentPane(p1);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(900,400);
        setSize(350, 75);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== this.btnOne){
            JOptionPane.showMessageDialog(this, "yeeeeeee");
        }
    }




}
