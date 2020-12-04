import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class P2 {
    public static void main(String[] args) {
        int i = 0;
        int j;
        Long res = Long.valueOf(0);

        File in = new File("p2.in");
        try {
            Scanner sc = new Scanner(in);
            int arrSize = sc.nextInt();
            int k = sc.nextInt();

            if (arrSize == k) {
                res = Long.valueOf(0);

                try {
                    Writer wr = new FileWriter("p2.out");
                    wr.write(res.toString() + '\n');
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }

            long[] arr = new long[arrSize];

            while (sc.hasNextInt()) {
                arr[i++] = sc.nextInt();
            }

            //Din moment ce array-ul este de timp primitiv, nu putem face direct sortarea decrescator
            Arrays.sort(arr);
            long aux;
            //Inversarea elementelor pentru a avea array-ul sortat descrescator
            for (i = 0; i < arrSize / 2; i++) {
                aux = arr[i];
                arr[i] = arr[arrSize - 1 - i];
                arr[arrSize - 1 - i] = aux;
            }

            int dif = 0;
            long dp[][] = new long[arrSize][arrSize];

            //Luand in considerare un element (sau eliminand i elemente de la arr[0] la arr[i]), diferenta maxima va fi
            //mereu reprezentata de primul element din vectorul sortat (arr[0]).
            for (i = 0; i < arrSize; i++) {
                dp[i][0] = arr[0];
                if (i % 2 == 0) {
                    dif += arr[i];
                } else {
                    dif -= arr[i];
                }
                dp[i][i] = dif;
            }

            //Nu poti lua in considerare mai multe elemente decat ai la dispozitie, deci valoarea lor dp este 0
            for (i = 1; i < arrSize; i++) {
                for (j = i + 1; j < arrSize; j++) {
                    dp[i][j] = 0;
                }
            }

            //dp[i][j] este diferenta optima pt primele i + 1 elemente, luand in considerare j + 1 elemente (eliminand
            //i - j elemente). Aceasta diferenta este otima, fie alegand sa adaugam/scadem (in functie de cine alege)
            //elementul i din array, fie alegand sa nu includem elementul, deci este aceeasi cu cea cu mai putine
            //elemente in array, dar luand tot atatea in considerare.
            long f1;
            long f2;
            for (i = 2; i < arrSize; i++) {
                for (j = 1; j < i; j++) {
                    if (j % 2 == 0) {
                        f1 = dp[i - 1][j - 1] + arr[i];
                    } else {
                        f1 = dp[i - 1][j - 1] - arr[i];
                    }
                    f2 = dp[i - 1][j];
                    dp[i][j] = Math.max(f1, f2);
                }
            }

            res = dp[arrSize - 1][arrSize - k - 1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Writer wr = new FileWriter("p2.out");
            wr.write(res.toString() + '\n');
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
