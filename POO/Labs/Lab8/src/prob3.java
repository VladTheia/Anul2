import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

class LinkedList<T> implements Iterable<T> {
    static class Node<T> {
        T value;
        Node next;

        public Node(T val, Node nxt) {
            value = val;
            next = nxt;
        }

        public Node(T val) {
            value = val;
            next = null;
        }
    }

    Node first;
    Node last;

    class ListIterator implements Iterator<T> {
        Node<T> node = null;

        public boolean hasNext() {
            if (node == null && first != null) {
                return true;
            } else if (node != null) {
                if (node.next != null) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        public T next() {
            if (node == null && first != null) {
                node = first;
                return (T) first.value;
            } else if (node != null) {
                node = node.next;
                return node.value;
            }
            throw new NoSuchElementException();
        }
    }

    public void addFirst(T data) {
        Node node = new Node(data);
        if (first == null) {
            first = node;
            last = first;
        } else {
            node.next = first;
            first = node;
        }
    }

    public void add(T data) {
        if (first == null) {
            first = new Node(data);
            last = first;
        } else {
            Node node = new Node(data);
            last.next = node;
            last = node;
        }
    }

    public T getNode() {
        return (T) first.value;
    }

    public Iterator<T> iterator() {
        ListIterator it = new ListIterator();
        while (first != null) {
            it.node = first;
            first = first.next;
        }
        return it;
    }

//    public String toString() {
//        String result = "(";
//        if (first != null) {
//            if (first == last) {
//                result += first.value + ")";
//                return result;
//            }
//            while (first != last) {
//                result += first.value + ", ";
//            }
//            result += first.value + ")";
//        }
//        return result;
//    }
}

class Task3 {

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        list.add(10);
        list.add(20);
        list.addFirst(5);
        list.add(22);
        list.add(25);
        list.add(30);

        int last = 0;
        System.out.println("Afisare 1");
        for (Integer i : list) {
            System.out.print(i + ", ");
            if (i < last) {
                System.err.println("LinkedList a fost implementata gresit.");
            }
            last = i;
        }
        System.out.println();

        System.out.println("Afisare 2");
        Iterator<Integer> itr = list.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + ", ");
        }
        System.out.println();
    }
}