import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class P1 {
    public static void main(String[] args) {
        int i = 0;
        long dif = 0;

        File in = new File("p1.in");
        try {

            Scanner sc = new Scanner(in);
            int arrSize = sc.nextInt();
            long[] arr = new long[arrSize];
            while (sc.hasNextInt()) {
                arr[i++] = sc.nextInt();
            }

            //Sortam crescator, din moment ce vom salva diferenta in modul, nu are rost sortarea descrescatoare
            Arrays.sort(arr);
            //Din moment ce numarul de elemente difera in functie de input, nu putem face loop unrolling, dar putem
            //face 2 structuri for, unde indexul creste din 2 in 2, pentru a nu mai verifica de fiecare data daca i
            //este par sau impar
            for (i = 0; i < arrSize ; i += 2) {
                    dif += arr[i];
            }
            for (i = 1; i < arrSize ; i += 2) {
                dif -= arr[i];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Long res = Math.abs(dif);
        try {
            Writer wr = new FileWriter("p1.out");
            wr.write(res.toString() + '\n');
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
