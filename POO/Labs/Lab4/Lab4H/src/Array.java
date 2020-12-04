import java.util.*;
import java.lang.*;

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

class SortedArray extends Array{
    public Array array;

    public SortedArray() {
        super();
    }
    public void addElement(Integer x) {
        super.addElement(x);
        super.sort();
    }

    public int get(int poz) {
        return super.get(poz);
    }

    public int getSize() {
        return super.getSize();
    }

    public boolean remove(Integer x) {
        return super.remove(x);
    }

    public Integer remove(int pos) {
        return super.remove(pos);
    }

    public String toString() {
        String result = "{";
        for(int i = 0; i < super.getSize(); i++) {
            result += super.get(i) + ", ";
        }
        result += "}";
        return result;
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
        return stack.remove(stack.getSize() - 1);
    }

    boolean isEmpty() {
        if (stack.getSize() == 0)
            return true;
        return false;
    }


    public String toString() {
        String result = "{";
        for(int i = 0; i < stack.getSize(); i++) {
            result += stack.get(i) + ", ";
        }
        result += "}";
        return result;
    }

    public static void main(String[] args) {
        SortedArray array = new SortedArray();
        MyStack stack = new MyStack();

        array.addElement(5);
        array.addElement(3);
        array.addElement(1);
        array.addElement(8);
        array.addElement(12);
        System.out.println(array);

        stack.push(12);
        stack.push(22);
        stack.push(18);
        stack.push(5);
        stack.push(42);
        System.out.println(stack);
        stack.pop();
        stack.pop();
        System.out.println(stack);
    }
}