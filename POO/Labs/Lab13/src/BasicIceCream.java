public class BasicIceCream implements IceCream {
    double price;

    public BasicIceCream() {
        System.out.println("Se pune conul");
        price = 0.5;
    }

    @Override
    public String getDescription() {
        return "cone";
    }

    @Override
    public double getCost() {
        return price;
    }
}
