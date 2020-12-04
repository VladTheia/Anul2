import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;

class prob1 {
    public static TreeSet printWords(String fileName) {
        TreeSet ts = new TreeSet();
        StringTokenizer st;
        try {
            File file = new File(fileName);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                st = new StringTokenizer(sc.nextLine(), " <=?!/:>.,;*{}()[]=-+\'\"\n\t");
                while (st.hasMoreTokens()) {
                    ts.add(st.nextToken());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return ts;
    }

    public static TreeSet printWordsComparator(TreeSet tsArg) {
        TreeSet ts = new TreeSet(new MyComparator());
        ts.addAll(tsArg);
        return ts;
    }

    public static void main(String[] args){
        TreeSet ts = new TreeSet();
        TreeSet ts2 = new TreeSet();
        ts = printWords("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab7\\Arhiva\\test01.txt");
        ts2 = printWordsComparator(ts);
        System.out.println(ts);
        System.out.println(ts2);
    }
}

class MyComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        return o2.compareTo(o1);
    }
}