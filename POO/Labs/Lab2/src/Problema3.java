import java.util.*;

public class Problema3 {
    static int nrAparitii(Vector vec, int x) {
        int aparitii = 0;
        System.out.println(vec.contains(x));
        int index;
        while (vec.contains(x)) {
            index = vec.indexOf(x);
            aparitii++;
            vec.remove(index);
        }

        return aparitii;
    }

    public static  void main(String args[]) {
        Random generator = new Random();
        Vector vec = new Vector();
        for (int i = 0; i < 20; i++) {
            vec.add(generator.nextInt(10));
        }
        System.out.println(vec);
        int max = (int) vec.get(0);
        int min = max;
        for (int i = 1; i < 20; i++) {
            if (max < (int) vec.get(i))
                max = (int) vec.get(i);
            if (min > (int) vec.get(i))
                min = (int) vec.get(i);
        }
        System.out.println("Max: " + max + "\nIndex of min: " + vec.indexOf(min));
        System.out.println(nrAparitii(vec, 5));
        System.out.println(vec);
    }
}
