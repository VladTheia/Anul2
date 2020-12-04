public class Problema5 {
    int putere(int x, int exp) {
        if (exp == 0)
            return 1;
        if (exp == 1)
            return x;
        return x * putere(x, exp - 1);
    }

    void print(int x, int exp) {
        Problema5 obj = new Problema5();
        System.out.println(obj.putere(x, exp) + " " + Math.pow(x, exp));
    }

    public static void main(String args[]) {
        Problema5 obj2 = new Problema5();
        obj2.print(5, 3);
    }
}
