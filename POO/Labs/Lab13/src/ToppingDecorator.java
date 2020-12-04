public abstract class ToppingDecorator implements IceCream{
    double price;

    IceCream tempIceCream;

    public ToppingDecorator(IceCream iceCream) {
        tempIceCream = iceCream;
    }

    public String getDescription() {
        return tempIceCream.getDescription();
    }

    public double getCost() {
        return tempIceCream.getCost();
    }

}
