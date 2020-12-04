import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class SListSet extends LinkedList implements SortedSet{
    Comparator c;

    public SListSet() {
        c = null;
    }

    public SListSet(Comparator comparator) {
        c = comparator;
    }

    public Comparator comparator() {
        return c;
    }

    @Override
    public boolean add(Object o) {
        if (contains(o))
            return false;
        super.add(o);
        Collections.sort(this, c);
        return true;
    }

    @Override
    public Object first() {
        if (isEmpty())
            return null;
        return first();
    }

    @Override
    public Object last() {
        if (isEmpty())
            return null;
        return last();
    }

    @Override
    public SortedSet subSet(Object fromElement, Object toElement) {
        if (contains(toElement) && contains(fromElement))
            return subSet(fromElement, toElement);
        return null;
    }

    @Override
    public SortedSet headSet(Object toElement) {
        if (contains(toElement))
            return headSet(toElement);
        return null;
    }

    @Override
    public SortedSet tailSet(Object fromElement) {
        if (contains(fromElement))
            return tailSet(fromElement);
        return null;

    }

    public static void main(String[] args) {
        SListSet sl = new SListSet(new MyComparator());
        StringTokenizer st;
        try {
            File file = new File("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab7\\Arhiva\\test01.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                st = new StringTokenizer(sc.nextLine(), " <=?!/:>.,;*{}()[]=-+\'\"\n\t");
                while (st.hasMoreTokens()) {
                    sl.add(st.nextToken());
                }
            }
            System.out.println(sl);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}