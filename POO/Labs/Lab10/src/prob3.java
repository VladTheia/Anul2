import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

import javax.swing.*;

class SwingJCheckButton extends JFrame implements ItemListener {
    JFrame f;
    String question;
    String op1, op2, op3, op4;

    public void citire()  throws IOException {
        FileInputStream f = new FileInputStream("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Curs10H\\prb3.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        question = br.readLine();
        op1 = br.readLine();
        op2 = br.readLine();
        op3 = br.readLine();
        op4 = br.readLine();
    }

    public SwingJCheckButton(String title) throws IOException {
        //super("Swing JRadioButton Demo");
        f = new JFrame (title);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new SpringLayout());
        f.setMinimumSize(new Dimension(300, 200));
        f.getContentPane().setBackground(Color.blue);
        citire();

        JLabel intrebare = new JLabel(question);
        f.add(intrebare);



        JCheckBox option1 = new JCheckBox(op1);
        JCheckBox option2 = new JCheckBox(op2);
        JCheckBox option3 = new JCheckBox(op3);
        JCheckBox option4 = new JCheckBox(op4);

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        f.setLayout(new FlowLayout());

        f.add(option1);
        f.add(option2);
        f.add(option3);
        f.add(option4);

        option1.addItemListener(this);
        option2.addItemListener(this);
        option3.addItemListener(this);
        option4.addItemListener(this);

        f.pack();
        f.show();
    }

    public static void main(String[] args) throws IOException{
        SwingJCheckButton b = new SwingJCheckButton("Problema 2");
        b.setVisible(true);
    }

    public void itemStateChanged(ItemEvent e) {
       JCheckBox b1 = (JCheckBox)e.getSource();
       if (b1.getText().equals(op1) || b1.getText().equals(op2)) {
           f.getContentPane().setBackground(Color.green);
       } else {
           f.getContentPane().setBackground(Color.red);
       }
    }
}