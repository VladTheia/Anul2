public class Numar {
    int nr;

    public Numar(int x) {
        this.nr = x;
    }
    //returneaza suma dintre nr (membrul clasei) si a
    public int suma(int a) {
        return this.nr + a;
    }
    //returneaza suma dintre nr, a si b
    public int suma(int a, int b) {
        return this.nr + a + b;
    }
    //returneaza suma dintre nr, a, b si c
    public int suma(int a, int b, int c) {
        return this.nr + a + b + c;
    }
    //returneaza suma dintre nr, a, b, c si d
    public int suma(int a, int b, int c, int d) {
        return this.nr + a + b + c + d;
    }

    public static void main(String[] args) {
        Numar x = new Numar(5);
        System.out.println(x.suma(1, 2, 3, 4));
    }
}
