public class Patrat extends Dreptunghi {
    public Patrat() {
        super(0,0);
    }

    public Patrat(int latura1) {
        super(latura1, latura1);
    }

    double arie() {
        return latura1 * latura1;
    }

    public static void main(String[] args) {
        Patrat p = new Patrat(10);
        System.out.println("Aria este " + p.arie());
    }
}
