public class Problema7 {
    boolean isPrime(int x) {
        for (int i = 2; i <= x/2; i++)
            if (x % i == 0)
                return false;
        return true;
    }

    public static void main(String args[]) {
        int n = 6;
        Problema7 obj = new Problema7();
        for (int i = 2; i <= n; i = i + 2)
            for (int j = 1; j < i; j++)
                    if (obj.isPrime(j) && obj.isPrime(i - j))
                        System.out.println(i + " = " + j + " + " + (i - j));
    }
}
