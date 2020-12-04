import java.util.Iterator;
import java.util.Vector;

/*abstract*/ class Product {
    String productName;
    float productPrice;

    public Product() {

    }

    public Product(String name, float price) {
        productName = name;
        productPrice = price;
    }

    float getPrice() {
        return productPrice;
    }

    @Override
    public String toString() {
        return "Name: " + productName + "\n" + "Price: " + productPrice + "\n";
    }
}

public class ShopSingleton {
    private static ShopSingleton obj = null;
    String name;
    Vector<Product> products;

    private ShopSingleton() {
        products = new Vector<Product>();
    }

    public static ShopSingleton getInstance() {
        if (obj == null)
            obj = new ShopSingleton();
        return obj;
    }

    public void showProducts() {
        Vector<Product> aux = ShopSingleton.getInstance().products;
        Iterator it = aux.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}

class Test {
    void addProduct(Product p) {
        if (ShopSingleton.getInstance().products.contains(p))
            return;
        ShopSingleton.getInstance().products.add(p);
    }

    void removeProduct(Product p) {
        if (!ShopSingleton.getInstance().products.contains(p))
            return;
        ShopSingleton.getInstance().products.remove(p);
    }

    Product getCheapestProduct() {
        Vector<Product> aux = ShopSingleton.getInstance().products;
        Iterator it = aux.iterator();
        Product cheapest = (Product) it.next();
        Product product;
        while (it.hasNext()) {
            product = (Product) it.next();
            if (product.getPrice() < cheapest.getPrice())
                cheapest = product;
        }
        return cheapest;
    }

    public static void main(String[] args) {
        ShopSingleton.getInstance().name = "eMag";
        Product book = new Product("Morometii", 50);
        Product food = new Product("Milka", 10);
        Product beverage = new Product("Borsec", 3);
        Product computer = new Product("Asus", 4000);
        Test test = new Test();
        test.addProduct(book);
        test.addProduct(food);
        test.addProduct(beverage);
        test.addProduct(computer);

        System.out.println(ShopSingleton.getInstance().name);
        System.out.println(ShopSingleton.getInstance().products);

        System.out.println("The cheapest product is: " + test.getCheapestProduct());
    }
}

//class Book extends Product {
//    public Book(String name, float price) {
//        productName = name;
//        productPrice = price;
//    }
//
//    float getPriceReduced () {
//        return productPrice - productPrice * 15 /100;
//    }
//}
//
//class Food extends Product {
//    public Food(String name, float price) {
//        productName = name;
//        productPrice = price;
//    }
//
//    float getPriceReduced () {
//        return productPrice - productPrice * 20 /100;
//    }
//}
//
//class Beverage extends Product {
//    public Beverage(String name, float price) {
//        productName = name;
//        productPrice = price;
//    }
//
//    float getPriceReduced () {
//        return productPrice - productPrice * 5 /100;
//    }
//}
//
//class Computer extends Product {
//    public Computer(String name, float price) {
//        productName = name;
//        productPrice = price;
//    }
//
//    float getPriceReduced () {
//        return productPrice - productPrice * 10 /100;
//    }
//}
//
//class ProductFactory {
//    public static Product factory(String type, String nameProduct, float productPrice) {
//        if (type.equals("Book")) {
//            return new Book(nameProduct, productPrice);
//        }
//        if (type.equals("Food")) {
//            return new Food(nameProduct, productPrice);
//        }
//        if (type.equals("Beverage")) {
//            return new Beverage(nameProduct, productPrice);
//        }
//        if (type.equals("Computer")) {
//            return new Computer(nameProduct, productPrice);
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        Product book = factory("Book", "Morometii", 20);
//        Product food = factory("Food", "Milka", 15);
//        Product beverage = factory("Beverage", "Borsec", 3);
//        Product computer = factory("Computer", "Asus", 5000);
//
//        System.out.println(book);
//        System.out.println(food);
//        System.out.println(beverage);
//        System.out.println(computer);
//    }
//}