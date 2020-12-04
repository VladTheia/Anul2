public class da {

}

class A {
    void metoda() {
        System.out.println("A: Metoda 1");
    }
    //Supraincarcare
    void metoda(int nr) {
        System.out.println("A: Metoda 2 " + nr);
    }
}

class B extends A {
    //Supradefinire
    void metoda() {
        System.out.println("B: Metoda supradefinita");
    }
}
class Test {
    public static void main(String args[]) {
        B obj = new B();
        obj.metoda();
    }
}

