import java.util.ArrayList;
import java.util.Iterator;

public class prob4 {
    public static void main(String[] args) {
        GenericListMethods gen = new GenericListMethods();
        gen.add(1);
        gen.add(1);
        gen.add(1);
        gen.add(2);
        gen.add(3);
        gen.add(4);
        gen.add(5);
        System.out.println(gen);
        gen.removeDuplicates(gen);
        System.out.println(gen);
        //System.out.println(gen.binarySearch(gen, 4, 0, 4));
        System.out.println(gen.max(gen));
    }
}

class GenericListMethods extends ArrayList implements GenericInterface {
    @Override
    public <E extends Comparable<E>> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        ArrayList<E> arr = new ArrayList<>();
        Iterator listIt = list.iterator();
        E current;
        while (listIt.hasNext()) {
            current = (E) listIt.next();
            if (!arr.contains(current))
                arr.add(current);
        }
        return arr;
    }

    @Override
    public <E extends Comparable<E>> E max(ArrayList<E> list) {
        E max = list.get(0);
        E current;
        Iterator<E> listIt = list.iterator();
        while (listIt.hasNext()) {
            current = listIt.next();
            if (max.compareTo(current) < 0)
                max = current;
        }
        return max;
    }

    @Override
    public <E extends Comparable<E>> int binarySearch(ArrayList<E> list, E key, int start, int end) {
        int mid = (start + end)/2;
        if(list.get(mid) == key)
            return mid;
        else {
            if (list.get(mid).compareTo(key) == -1) {
                ArrayList<E> listLow = (ArrayList<E>) list.subList(start, mid);
                return binarySearch(listLow, key, start, mid);
            }
            else {
                ArrayList<E> listHigh = (ArrayList<E>) list.subList(mid + 1, end);
                return binarySearch(listHigh, key, mid + 1, end);
            }
        }
    }
}