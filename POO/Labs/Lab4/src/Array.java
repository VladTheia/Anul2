import javax.xml.bind.Element;
import java.util.Collections;
import java.util.Vector;

public class Array {
    //Vectorul in care se vor retine elementele
    private Vector vector;

    //Constructor clasei
    public Array() {
        //Instantierea vectorului cu elemente
        vector = new Vector();
    }

    //Metoda care adauga un element in vector, folosind pozitia curenta
    public void addElement(Integer x) {
        vector.add(x);
    }

    //Metoda care adauga un element in vector, tinand cont de pozitia indicata
    public void addElement(Integer x, int poz) {
        if(poz >= 0 && poz <= vector.size()) {
            vector.add(poz, x);
        }
    }

    //Metoda care returneaza elementul aflat in vector la pozitia indicata
    public int get(int poz) {
        int result;
        if(poz >= 0 && poz < vector.size()) {
            result = (int) vector.get(poz);
            return result;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    //Metoda ce intoarce numarul de elemente din vector
    public int getSize() {
        return vector.size();
    }

    //Metoda pentru stergerea unui element din vector
    public boolean remove(Integer x) {
        return vector.remove(x);
    }

    //Metoda pentru stergerea elementului de pe pozitia pos din vector
    public Integer remove(int pos) {
        return (int) vector.remove(pos);
    }

    //Metoda uzitata pentru afisarea unui obiect de tip Array
    public String toString() {
        String result = "{";
        for(int i = 0; i < vector.size(); i++) {
            result += get(i) + ", ";
        }
        result += "}";
        return result;
    }

    public void sort() {
        Collections.sort(vector);
    }
}

class SortedArray extends Array {
    public SortedArray() {
        super();
    }

    public void addElement(Integer x) {
        super.addElement(x);
        sort();
    }

    public void addElement(Integer x, int poz) {
        if (poz >= 0 && poz <= getSize()) {
            if (poz == 0 && get(poz + 1) > x) {
                super.addElement(poz, x);
            } else if (poz == getSize() && get(poz - 1) < x) {
                super.addElement(poz, x);
            } else if (get(poz - 1) < x && x < get(poz + 1)) {
                super.addElement(poz, x);
            }
        }
    }
}

class MyStack {
    Array stack;

    public MyStack() {
        stack = new Array();
    }

    void push(int x) {
        stack.addElement(x);
    }

    int pop() {
        int x = stack.get(stack.getSize() - 1);
        stack.remove(stack.getSize() - 1);
        return x;
    }

    public String toString() {
        return stack.toString();
    }
}

class Test2 {
    public static void main(String[] args) {
        SortedArray arr = new SortedArray();
        arr.addElement(1);
        arr.addElement(2);
        arr.addElement(3);
        System.out.println("Array: " + arr);
        arr.remove(0);
        System.out.println("Array: " + arr);
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(5);
        stack.push(10);
        System.out.println("Stack: " + stack);
        stack.pop();
        System.out.println("Stack: " + stack);

    }
}