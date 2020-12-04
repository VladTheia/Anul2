public class prob2 {
    public static void main(String[] args) {
        Rectangle r1;
        //r1 = new Shape(); -> clasa abstracta, nu poate fi instantiata
        r1 = new Square(); //upcasting

        Square sq1 = new Square();
        Rectangle r2;
        Shape s;
        Circle c;
        r2 = (Rectangle) sq1;
        s = (Shape) sq1;
        //c = (Circle) sq1;


        //Conversie 1
        //Circle c1 = new Circle();
        //Square sq = (Square) c1;
        Square sq;
        //Conversie 2
        Rectangle r = new Rectangle(5.0, 5.0);
        sq = (Square) r; //downcast
        //Conversie 3
        sq = new Square(7.0);
        r = sq; //upcast

    }
}
