public class Chocolate extends ToppingDecorator {
    public Chocolate(IceCream iceCream) {
        super(iceCream);
        System.out.println("Se adauga ciocolata");
        price = 1.5;
    }


    @Override
    public String getDescription() {
        return super.getDescription() + ";chocolate ";
    }

    @Override
    public double getCost() {
        return super.getCost() + price;
    }
}
