import java.util.Arrays;
public class Problema8 {
    public static void main(String args[]) {
        double[] myArray = new double[10];
        for (int i = 0; i < 10; i++)
            myArray[i] = Math.random();
        Arrays.sort(myArray);
        System.out.println("5th element is = " + Arrays.binarySearch(myArray, 5));
    }
}
