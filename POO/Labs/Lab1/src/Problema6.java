public class Problema6 {
    boolean isPrime(int x) {
        for (int i = 2; i <= x/2; i++)
            if (x % i == 0)
                return false;
          return true;
    }

    public static void main(String args[]) {
        Problema6 obj = new Problema6();
        for (int i = 0; i < 20; i++) {
            if (obj.isPrime(i) == true)
                System.out.println(i + " este prin");
            else
                System.out.println(i + " nu este prin");
        }
    }
}
