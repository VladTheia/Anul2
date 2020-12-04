import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.FileNotFoundException;

class Problema1 {
    public static TreeSet printWords(String input) {
        TreeSet wordsSet = new TreeSet();
        try {
            File f = new File(input);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                StringTokenizer st = new StringTokenizer(sc.nextLine(),
                        " ,./<>?;:\'\"[]{}\n\t=+-()*!");
                while (st.hasMoreTokens()) {
                    wordsSet.add(st.nextToken());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return wordsSet;
    }

    public static TreeSet printWordsComparator(TreeSet wordsSet) {
        TreeSet compTreeSet = new TreeSet(new myComp());
        compTreeSet.addAll(wordsSet);
        return compTreeSet;
    }
}

class myComp implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o2.compareTo(o1);
    }

    public static void main(String[] args) {
        TreeSet ts = new TreeSet();
        TreeSet ts2 = new TreeSet();
        ts = Problema1.printWords("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab7\\Arhiva\\test01.txt");
        ts2 = Problema1.printWordsComparator(ts);
        System.out.println(ts);
        System.out.println(ts2);
    }
}

