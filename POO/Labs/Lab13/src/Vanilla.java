public class Vanilla extends ToppingDecorator {
    public Vanilla(IceCream iceCream) {
        super(iceCream);
        System.out.println("Se adauga vanilie");
        price = 2;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ";vanilla ";
    }

    @Override
    public double getCost() {
        return super.getCost() + price;
    }
}