public class Fractie {
    double numarator, numitor;

    public Fractie(double numarator, double numitor) {
        this.numarator = numarator;
        this.numitor = numitor;
    }

    public Fractie() {
        this(1, 1);
    }

    double CMMDC(double a, double b ) {
        double c;
        while (b != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    double CMMMC(double a, double b) {
        return a * b /CMMDC(a, b);
    }

    Fractie suma(Fractie x) {
        Fractie f = new Fractie();
        f.numarator = this.numarator * CMMMC(this.numitor, x.numitor) / this.numitor
                + x.numarator * CMMMC(this.numitor, x.numitor)/x.numitor;
        f.numitor = CMMMC(this.numitor, x.numitor);

        return f;
    }

    public String toString() {
        return this.numarator + "/" + this.numitor;
    }

    boolean equals(Fractie x) {
        if (numarator * x.numitor == x.numarator * numitor)
            return true;
        return false;
    }

    public static void main(String[] args) {
        Fractie f1 = new Fractie(1, 6);
        Fractie f2 = new Fractie(3, 4);

        System.out.println(f1.suma(f2));
        if (f1.equals(f2))
            System.out.println("Equals");
        else
            System.out.println("Not equals");
    }
}
