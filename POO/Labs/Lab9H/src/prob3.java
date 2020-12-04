import java.util.ArrayList;
import java.util.Iterator;

public class prob3 {
    public static void main (String[] args) {
        IntegerMatrix matrix = new IntegerMatrix();
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        list1.add(1); list1.add(2); list1.add(3);
        list2.add(2); list2.add(3); list2.add(4);
        list3.add(3); list3.add(4); list3.add(5);
        matrix.add(list1);
        matrix.add(list2);
        matrix.add(list3);
        System.out.println(matrix);
        IntegerMatrix matrix2 = (IntegerMatrix) matrix.addition(matrix);
        System.out.println(matrix2);
    }
}

class IntegerMatrix extends AMatrix<Integer> {
    public String toString() {
        String str = new String();
        Iterator<ArrayList<Integer>> rowIterator = iterator();
        while (rowIterator.hasNext())
            str += rowIterator.next().toString() + "\n";
        return str;
    }

    public Integer sum(Integer obj1, Integer obj2) {
        return obj1 + obj2;
    }


    public AMatrix addition(AMatrix m) {
        AMatrix<Integer> matrix = new IntegerMatrix();

        Iterator<ArrayList<Integer>> rowIterator1 = this.iterator();
        Iterator<ArrayList<Integer>> rowIterator2 = m.iterator();

        while (rowIterator1.hasNext() && rowIterator2.hasNext()) {
            ArrayList<Integer> currentRow = new ArrayList<>();

            Iterator<Integer> colIterator1 = (rowIterator1.next()).iterator();
            Iterator<Integer> colIterator2 = (rowIterator2.next()).iterator();

            while (colIterator1.hasNext() && colIterator2.hasNext()) {
                Integer element = sum(colIterator1.next(),colIterator2.next());
                currentRow.add(element);
            }
            matrix.add(currentRow);
        }
        return matrix;
    }
}