import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class BookRenderer extends JPanel implements ListCellRenderer{
    JLabel title;
    JLabel author;
    JLabel image;

    public BookRenderer(Book book) {
        title = new JLabel(book.getName());
        author = new JLabel(book.getAuthor());
        image.setIcon(new ImageIcon("Imagini/" + book.getIconName() + ".jpg"));
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setBackground(isSelected ? Color.red : Color.white);
        setForeground(isSelected ? Color.white : Color.BLACK);
        return this;
    }
}

class prb1 extends JFrame{
    static JFrame f;
    static JList b;
    static int index;

    public static void main(String[] args){
        f = new JFrame("Frame");
        f.setLayout(new FlowLayout());
        JPanel results = new JPanel();
        JLabel authorLable = new JLabel("Autor:");
        JLabel bookLable = new JLabel("Carte:");
        JTextField authorText =new JTextField( "", 15);
        JTextField bookText =new JTextField( "", 15);
        results.add(authorLable);
        results.add(authorText);
        results.add(bookLable);
        results.add(bookText);


        prb1 x = new prb1();
        JScrollPane scrollPanel = new JScrollPane();
        JLabel l = new JLabel("Select the book");
        DefaultListModel<Book> books = new DefaultListModel<>();
        Book b1 = new Book("Author1", "C/C++ Programming", "cpp");
        Book b2 = new Book("Author2", "Java Programming", "java");
        Book b3 = new Book("Author3", "C# Programming", "cs");
        Book b4 = new Book("Author4", "IOS Programming", "ios");
        Book b5 = new Book("Author5", "Windows Phone Programming", "wp");
        Book b6 = new Book("Author6", "Android Programming", "android");
        books.addElement(b1); books.addElement(b2); books.addElement(b3); books.addElement(b4); books.addElement(b5); books.addElement(b6);
        b = new JList(books);
        ListSelectionModel sModel = b.getSelectionModel();
        sModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(b.isSelectionEmpty())
                    return;
                if(e.getValueIsAdjusting()) {
                    index = b.getSelectedIndex();
                    authorText.setText(books.get(index).getName());
                    bookText.setText(books.get(index).getAuthor());
                }
            }
        });
        scrollPanel.setViewportView(b);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                books.remove(index);
            }
        });
        results.add(deleteButton);



        f.add(results);
        f.add(scrollPanel);
        f.setSize(500, 300);
        f.show();
    }
}
