public class Punct {
    private int x, y;

    public Punct() {
        this.x = 0;
        this.y = 0;
    }

    int getX() {
        return this.x;
    }

    void setX(int x) {
        this.x = x;
    }

    int getY() {
        return this.y;
    }

    void setY(int y) {
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    double distance(int a, int b) {
        return Math.sqrt(Math.pow(this.x - a, 2) + Math.pow(this.y - b, 2));
    }

    double distance(Punct p) {
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
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
