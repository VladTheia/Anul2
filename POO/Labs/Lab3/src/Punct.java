public class Punct {
    private int x, y;

    public Punct() {
        x = 0;
        y = 0;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    double distance(int a, int b) {
        return Math.sqrt(Math.pow(x - a, 2) + Math.pow(y - b, 2));
    }

    double distance(Punct p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }
}

class Test2 {
    public static void main(String[] args) {
        Punct A = new Punct();
        Punct B = new Punct();

        A.setX(1); A.setY(2);
        B.setX(-1); B.setY(3);

        System.out.println("A: " + A + " B: " + B);
        System.out.println("Distance = " + A.distance(B));
    }
}
