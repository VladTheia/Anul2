import java.io.*;
import java.util.Scanner;

public class P3 {
    public static void main(String[] args) {
        int i = 0;
        Long res = Long.valueOf(0);

        File in = new File("p3.in");
        try {
            Scanner sc = new Scanner(in);
            int arrSize = sc.nextInt();
            long[] arr = new long[arrSize];
            long totalSum = 0;
            while (sc.hasNextInt()) {
                arr[i] = sc.nextInt();
                totalSum += arr[i++];
            }

            long dp[][] = new long[arrSize][arrSize];
            long f1, f2, f3;
            int j, jump;

            //Algoritmul ne va obtine castigul maxim al lui Tuzgu. Stim ca exista doua optiuni. Poate alege v[i] sau
            // v[j]. Indiferent de alegere, Ritza fa alege astfel incat sa minimizeze castigul lui Tuzgu, astfel: daca
            // alege v[i], Ritza va alege v[i + 1] sau v[j], Tuzgu ramanand ori cu suma posibila de la v[i + 2] la v[j],
            // ori cu cea de la v[i + 1] la v[j - 1] (si va ramane cu cea din ele care va fi mai mica, pentru ca asta
            // este scopul Ritzei). Urmand aceeasi gandire, daca alege v[j], Ritza ii  va lasa la dispozitie minimul
            // dintre suma pe care o putea obine deintre v[i + 1] si v[j - 1] sau dintre v[i] si v[j - 2]. Pentru ca
            // Tuzgu dorese sa isi maximizeze castigul, va alege maximul dintre optiunile pe care le are la dispozitie.
            for (jump = 0; jump < arrSize; jump++) {
                for (i = 0, j = jump; j < arrSize; i++, j++) {
                    f1 = (i + 2 <= j) ? dp[i + 2][j] : 0;
                    f2 = (i + 1 <= j - 1) ? dp[i + 1][j - 1] : 0;
                    f3 = (i <= j - 2) ? dp[i][j - 2] : 0;

                    dp[i][j] = Math.max(arr[i] + Math.min(f1, f2),
                                        arr[j] + Math.min(f2, f3));
                }
            }

            //Din castigul maxim al lui Tzugu scadem castigul maxim al Ritzei (suma elementelor - castigul lui Tuzgu)
            res = dp[0][arrSize - 1] - (totalSum - dp[0][arrSize - 1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Writer wr = new FileWriter("p3.out");
            wr.write( res.toString() + '\n');
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
