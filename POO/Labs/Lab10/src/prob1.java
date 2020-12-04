import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class prob1 extends JFrame implements ActionListener{
    Frame f;
    GridBagLayout gridBag ;
    GridBagConstraints gbc ;
    JLabel etNumeDisc;
    JLabel etCale;
    JLabel etNumeFisier;
    JTextField numeDisc;
    JTextField cale;
    JTextField numeFisier;
    JLabel etRezultat;
    JTextField rezultat;


    void adauga ( Component comp ,int x, int y, int w, int h) {
        gbc . gridx = x;
        gbc . gridy = y;
        gbc . gridwidth = w;
        gbc . gridheight = h;
        gridBag . setConstraints (comp , gbc);
        f.add( comp );
    }
    public prob1(String title){
        f = new Frame (title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new SpringLayout());
        gridBag = new GridBagLayout ();
        gbc = new GridBagConstraints ();
        gbc . weightx = 1.0;
        gbc . weighty = 1.0;
        gbc . insets = new Insets (5, 5, 5, 5);
        f. setLayout ( gridBag );
        etNumeDisc = new JLabel (" Nume Disc:");
        gbc . fill = GridBagConstraints . NONE ;
        gbc . anchor = GridBagConstraints . EAST ;
        adauga ( etNumeDisc , 0, 2, 1, 1);
        etCale = new JLabel (" Cale :");
        adauga ( etCale , 0, 3, 1, 1);
        etNumeFisier = new JLabel (" Nume fisier :");
        adauga ( etNumeFisier , 0, 4, 1, 1);
        numeDisc = new JTextField ("", 30) ;
        gbc . fill = GridBagConstraints . HORIZONTAL ;
        gbc . anchor = GridBagConstraints . CENTER ;
        adauga (numeDisc , 1, 2, 2, 1);
        cale = new JTextField ("", 30) ;
        adauga ( cale , 1, 3, 2, 1);
        numeFisier = new JTextField ("", 30) ;
        adauga ( numeFisier , 1, 4, 2, 1);
        JButton iesire = new JButton (" Iesire ");
        adauga ( iesire , 2, 5, 1, 1);
        iesire.addActionListener(this);
        numeDisc.addActionListener(this);
        cale.addActionListener(this);
        numeFisier.addActionListener(this);
        etRezultat = new JLabel (" Cale :");
        adauga ( etRezultat , 0, 6, 1, 1);
        rezultat = new JTextField ("", 30) ;
        adauga ( rezultat , 1, 6, 2, 1);


        f. pack ();
        f. show ();
    }

    public static void main (String args[]){
        prob1 b = new prob1("LaboratorPOO");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        rezultat.setText(numeDisc.getText() + cale.getText() +  numeFisier.getText());
    }
}
