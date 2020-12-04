public class Paralelogram extends Patrulater{
    public Paralelogram() {
        super(0,0,0,0);
    }

    public Paralelogram(int latura1, int latura2) {
        super(latura1, latura2, latura1, latura2);
    }

    public Paralelogram(double unghi1, double unghi2) {
        super(0, 0, 0, 0, unghi1, unghi2, unghi1, unghi2);
    }

    public Paralelogram(int latura1, int latura2, double unghi1, double unghi2) {
        /*this(latura1, latura2);
        this.unghi1 = unghi1;
        this.unghi2 = unghi2;
        this.unghi3 = unghi1;
        this.unghi4 = unghi2;*/
        super(latura1, latura2, latura1, latura2, unghi1, unghi2, unghi1, unghi2);
    }

    double arie() {
        return latura1 * latura2 * Math.sin(Math.toRadians(unghi1));
    }

    public static void main(String[] args) {
        Paralelogram p = new Paralelogram(3, 5, 90, 120);
        System.out.println("Aria este " + p.arie());
    }
}
