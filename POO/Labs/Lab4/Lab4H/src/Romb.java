public class Romb extends Paralelogram {
    int diag1;
    int diag2;

    public Romb() {
        super(0, 0, 0, 0);
        diag1 = 0;
        diag2 = 0;
    }

    public Romb(int latura, int diag1, int diag2) {
        super(latura, latura);
        this.diag1 = diag1;
        this.diag2 = diag2;
    }

    public Romb(double unghi1, double unghi2) {
        super(0, 0, unghi1, unghi2);
    }

    public Romb(int latura, double unghi1, double unghi2, int diag1, int diag2) {
        super(latura, latura, unghi1, unghi2);
        this.diag1 = diag1;
        this.diag2 = diag2;
    }

    double arie() {
        return diag1 * diag2 / 2;
    }

    public static void main(String[] args) {
        Romb r = new Romb(5, 60, 120, 10, 5);
        System.out.println("Aria este " + r.arie());
    }
}
