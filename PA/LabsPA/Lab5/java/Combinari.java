import java.util.*;

public class Combinari {
    static void back(int step, int stop, int[] domain, int[] solution) {
        if (step == stop) {
            for (int i = 0; i < stop; i++) {
                System.out.print(domain[solution[i]] + " ");
            }
            System.out.print('\n');
            return;
        }

        int i = step > 0 ? solution[step - 1] + 1 : 0;
        for (; i < domain.length; i++) {
            solution[step] = i;
            back(step + 1, stop, domain, solution);
        }
    }


    public static void main(String[] args) {
        int[] m = new int[]{1,2,3,4};
        int k = 3;
        int [] sol = new int[k];

        back(0, k, m, sol);
    }
}