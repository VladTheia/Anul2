import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

class NumarNegativ extends Exception {
    public NumarNegativ() {
        super("Numarul introdus este negativ!");
    }
}

class prob3{
    Scanner sc;
    Vector arr;

    public prob3() {
        sc = new Scanner(System.in);
        arr = new Vector();
    }

    public void myRead() throws NumarNegativ {
        int nr = sc.nextInt();
        arr.add(nr);
        if (nr < 0)
            throw new NumarNegativ();
    }

    public static void main(String[] args) {
        prob3 p = new prob3();
        try {
            while(true) {
                p.myRead();
            }
        } catch (NumarNegativ r) {
            System.out.println(r);
        } finally {
            System.out.println(Collections.max(p.arr));
        }
    }

}