import java.util.Collection;
import java.util.Vector;

public class Customer implements Observer{
    String name;
    ShoppingCart shoppingCart;
    WishList wishList;
    Vector<Notification> notifications;
    Strategy strategy;

    public Customer(String name, float budget, Strategy strategy) {
        this.name = name;
        this.shoppingCart = new ShoppingCart(budget);
        this.wishList = new WishList();
        this.notifications = new Vector<>();
        this.strategy = strategy;
    }

    public void update(Notification notification) {
        notifications.add(notification);
    }

    public String getName() {
        return name;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    public Vector<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Vector<Notification> notifications) {
        this.notifications = notifications;
    }

    //Used for testing
    public String toString() {
        return name;
    }
}
