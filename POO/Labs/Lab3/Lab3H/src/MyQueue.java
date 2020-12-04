import java.util.*;

class MyArray {
    private int v[];
    private int size;

    public MyArray() {
        this(100);
    }

    public MyArray(int length) {
        size = 0;
        v = new int[length];
    }

    public int get(int poz) {
        if(poz < size) {
            return v[poz];
        } else {
            return -1;
        }
    }

    public void set(int pos, int value) {
        v[pos] = value;
        size++;
    }

    public int getSize() {
        return size;
    }
}

public class MyQueue {
    private MyArray array;
    private static final int Infinit = 9500;
    private int first, last, size;

    public MyQueue() {
        this.first = 0;
        this.last = 0;
        this.size = 0;
        this.array = new MyArray();
    }

    int getSize() {
        return this.size;
    }

    void enqueue(int value) {
        if (this.size == 0) {
            this.size = 1;
            this.last = 0;
            this.first = 0;
            this.array.set(0, value);
        } else {
            this.size++;
            this.last++;
            this.array.set(last, value);
        }
    }

    int dequeue() {
        int x = this.array.get(first);
        first++;
        size--;
        return x;
    }

    boolean isEmpty() {
        if (this.size == 0) return true;
        return false;
    }

    public String toString() {
        StringBuilder sb =new StringBuilder();

        for (int i = first; i <= last; i++)
        {
            sb.append(this.array.get(i) + " ");
        }
        return sb.toString();
    }
}

class Test {
    public static void main(String args[]) {
        MyQueue queue = new MyQueue();
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(10);
        queue.enqueue(-1);
        queue.enqueue(2);
        System.out.println(queue);
        System.out.println(queue.dequeue());
        System.out.println(queue.getSize());
        System.out.println(queue);
        queue.enqueue(9);
        queue.enqueue(queue.dequeue());
        queue.enqueue(11);
        queue.enqueue(22);
        System.out.println(queue);
        while(!queue.isEmpty()) {
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println("");
        System.out.println(queue);
    }
}