public class Test1 {
    public static void main(String[] args) {
        IceCream vanilla = new Vanilla(new Chocolate(new BasicIceCream()));
        System.out.println("Ingrediente: " + vanilla.getDescription());
        System.out.println("Cost: " + vanilla.getCost());
    }
}
