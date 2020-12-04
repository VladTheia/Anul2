public class Magazin {
    String nume;
    Produs p1, p2, p3;

    public Magazin(String nume, Produs p1, Produs p2, Produs p3) {
        this.nume = nume;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public String toString() {
        return "Magazin " + this.nume + "\nprodusul 1 " + this.p1 +
                "\nprodusul 2 " + this.p2 + "\nprodusul 3 "  + this.p3;
    }

    double getTotalMagazin() {
        return this.p1.getTotalProdus() + this.p2.getTotalProdus() + this.p3.getTotalProdus();
    }

    public static void main(String[] args) {
        Produs monitor = new Produs("Monitor", 1000, 5);
        Produs mouse = new Produs("Mouse", 150, 10);
        Produs tastatura = new Produs("Tastatura", 200, 15);
        Magazin mag = new Magazin("Altex", monitor, mouse, tastatura);

        System.out.println(mag);
        System.out.println(mag.getTotalMagazin());
    }
}
