import java.util.ArrayList;

public class Problema4 {
}

class GenericListMethods implements GenericInterface {
    @Override
    public <E extends Comparable<E>> ArrayList<E> removeDuplicates(ArrayList<E> list) {
        return null;
    }

    @Override
    public <E extends Comparable<E>> E max(ArrayList<E> list) {
        return null;
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

            }
        }
    }
}