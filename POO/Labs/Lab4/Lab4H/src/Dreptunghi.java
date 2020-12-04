public class Dreptunghi extends  Paralelogram {
    public Dreptunghi() {
        super(0,0,0,0);
    }

    public Dreptunghi(int latura1, int latura2) {
        super(latura1, latura2, 90, 90);
    }

    double arie() {
        return latura1 * latura2;
    }

    public static void main(String[] args) {
        Dreptunghi d = new Dreptunghi(10, 5);
        System.out.println("Aria este " + d.arie());
    }
}
