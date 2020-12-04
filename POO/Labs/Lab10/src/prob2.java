import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

class SwingJRadioButton extends JFrame implements ActionListener {
    JFrame f;
    String question;
    String op1, op2, op3, op4;

    public void citire()  throws IOException {
        FileInputStream f = new FileInputStream("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Curs10H\\prb2.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(f));
        question = br.readLine();
        op1 = br.readLine();
        op2 = br.readLine();
        op3 = br.readLine();
        op4 = br.readLine();
    }

    public SwingJRadioButton(String title) throws IOException {
        //super("Swing JRadioButton Demo");
        f = new JFrame (title);
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new SpringLayout());
        f.setMinimumSize(new Dimension(300, 200));
        f.getContentPane().setBackground(Color.blue);
        citire();

        JLabel intrebare = new JLabel(question);
        f.add(intrebare);



        JRadioButton option1 = new JRadioButton(op1);
        JRadioButton option2 = new JRadioButton(op2);
        JRadioButton option3 = new JRadioButton(op3);
        JRadioButton option4 = new JRadioButton(op4);

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

        option1.addActionListener(this);
        option2.addActionListener(this);
        option3.addActionListener(this);
        option4.addActionListener(this);

        f.pack();
        f.show();
    }

    public static void main(String[] args) throws IOException{
        SwingJRadioButton b = new SwingJRadioButton("Problema 2");
        b.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JRadioButton button = (JRadioButton)e.getSource();
        if (button.getText().equals(op1)){
            f.getContentPane().setBackground(Color.green);
        } else {
            f.getContentPane().setBackground(Color.red);
        }
    }
}