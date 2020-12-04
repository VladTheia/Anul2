public class Patrulater {
    public int latura1, latura2, latura3, latura4;
    public double unghi1, unghi2, unghi3, unghi4;

    public Patrulater() {
        this(0, 0, 0, 0);
    }

    public Patrulater(int latura1, int latura2, int latura3, int latura4) {
        this.latura1 = latura1;
        this.latura2 = latura2;
        this.latura3 = latura3;
        this.latura4 = latura4;
    }

    public Patrulater(double unghi1, double unghi2, double unghi3, double unghi4) {
        this(0, 0, 0, 0, unghi1, unghi2, unghi3, unghi4);
    }

    public Patrulater(int latura1, int latura2, int latura3, int latura4,
                      double unghi1, double unghi2, double unghi3, double unghi4) {
        this(latura1, latura2, latura3, latura4);
        this.unghi1 = unghi1;
        this.unghi2 = unghi2;
        this.unghi3 = unghi3;
        this.unghi4 = unghi4;
    }

    public int perimetru() {
        int result;
        result = latura1 + latura2 + latura3 + latura4;
        return result;
    }
}

class Paralelogram extends Patrulater {
    public Paralelogram() {
        super(0,0,0,0);
    }

    public Paralelogram(int latura1, int latura2) {
        super(latura1, latura2, latura1, latura2);
    }

    public Paralelogram(double unghi1, double unghi2) {
        super(0,0,0,0, unghi1, unghi2, unghi1, unghi2);
    }

    public Paralelogram(int latura1, int latura2, double unghi1, double unghi2) {
        super(latura1, latura2, latura1, latura2, unghi1, unghi2, unghi1, unghi2);
    }

    public double arie() {
        return latura1 * latura2 * Math.sin(Math.toDegrees(unghi1));
    }
}

class Romb extends Paralelogram {
    int diag1, diag2;

    public Romb() {
        super(0,0,0,0);
        this.diag1 = 0;
        this.diag2 = 0;
    }

    public Romb(int latura, int diag1, int diag2) {
        super(latura, latura);
        this.diag1 = diag1;
        this.diag2 = diag2;
    }

    public Romb(int latura, double unghi1, int diag1, int diag2) {
        super(latura, latura, unghi1, 180 - unghi1);
        this.diag1 = diag1;
        this.diag2 = diag2;
    }

    public double arie() {
        return diag1 * diag2 / 2;
    }
}

class Dreptunghi extends Paralelogram {
    public Dreptunghi() {
        super(0, 0, 90, 90);
    }

    public Dreptunghi(int latura1, int latura2) {
        super(latura1, latura2, 90, 90);
    }

    public double arie() {
        return latura1 * latura2;
    }
}

class Patrat extends Dreptunghi {
    public Patrat() {
        super();
    }

    public Patrat(int latura) {
        super(latura, latura);
    }

    public double arie() {
        return latura1 * latura2;
    }

    public static void main(String[] args) {
        Paralelogram p1 = new Paralelogram(10, 5, 60, 120);
        System.out.println("Aria paralelogram: " + p1.arie());
        Romb r = new Romb(10, 60, 5, 8);
        System.out.println("Aria rombului: " + r.arie());
        Dreptunghi d = new Dreptunghi(10, 5);
        System.out.println("Aria dreptunghiului: " + d.arie());
        Patrat p2 = new Patrat(5);
        System.out.println("Aria patratului: " + p2.arie());
    }
}