import sun.reflect.generics.tree.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class prob4 {
    public static void main(String[] args) {
        TreeMap<String, LinkedList> map = new TreeMap<String, LinkedList>();
        int rowNum = 1;
        StringTokenizer st;
        File f = new File("D:\\Facultate\\Anul 2\\Semestrul 1\\POO\\Lab7\\Arhiva\\test01.txt");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                st = new StringTokenizer(sc.nextLine(), " <?!/:>.,;*{}()[]=-+\'\"\n\t");
                while (st.hasMoreTokens()) {
                    String str = st.nextToken();
                    if (!map.containsKey(str)) {
                        LinkedList newList = new LinkedList();
                        newList.add(rowNum);
                        map.put(str, newList);
                    } else {
                        LinkedList oldList = map.get(str);
                        oldList.add(rowNum);
                    }
                }
                rowNum++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
