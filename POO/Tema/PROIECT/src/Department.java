import java.util.Iterator;
import java.util.Vector;

abstract class Department implements Subject{
    String name; //department's name
    Vector<Item> items; //items avalable for sale
    Vector<Customer> customers; //customers that bought at least an item
    Vector<Customer> observers; //customers that wish at least an item
    int ID; //department's ID

    //Customer who bought an item from that department
    public void enter(Customer customer) {
        if (!(customers.contains(customer)))
            customers.add(customer);
    }

    //Customer who doesen't want to buy any more items from that department
    public void exit(Customer customer) {
        if ((customers.contains(customer)))
            customers.remove(customer);
    }

    //Customers that boucht at least an item from that department
    public Vector<Customer> getCustomers() {
        return customers;
    }

    //Gets department's ID
    public int getID() {
        return ID;
    }

    //Changes deparmtent's ID
    public void setID(int ID) { this.ID = ID;}

    //Adds an item to the department
    public void addItem(Item item) {
        if (!(items.contains(item)))
            items.add(item);
    }

    //Gets department's items
    public  Vector<Item> getItems() {
        return items;
    }

    //Searches items by ID
    public Item getItem(int ID) {
        for (Item item : items) {
            if (item.getID() == ID)
                return item;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    //Gets department's observers
    public Vector<Customer> getObservers() {
        return observers;
    }

    /*Method that returns the cheapest item, useful for SoftwareDepartment's
          accept method*/
    public Item cheapest() {
        Item cheapest = this.getItems().elementAt(0);
        for (Item item : this.getItems()) {
            if (cheapest.getPrice() > item.getPrice())
                cheapest = item;
        }
        return cheapest;
    }

    /*Method that returns the most expensive item, useful for
      VideoDepartment's accept method*/
    public Item mostExpensive() {
        Item expensive = this.getItems().elementAt(0);
        for (Item item : this.getItems()) {
            if (expensive.getPrice() < item.getPrice())
                expensive = item;
        }
        return expensive;
    }

    //Registers a customer as an observer when we has items in the Wishlist
    public void addObserver(Customer customer){
        observers.add(customer);
    }

    //Removes customer from the observer list when he has an empty Wishlist
    public void removeObserver(Customer customer){
        observers.remove(customer);
    }

    //notifies customers if an item has been added/removed from the department
    public void notifyAllObservers(Notification notification){
        Iterator<Customer> it = observers.iterator();
        while (it.hasNext()) {
            Customer cust = it.next();
            cust.update(notification);
        }
    }

    abstract void accept(Visitor visitor);

    @Override
    public String toString() {
        String result = "";
        result += name + ';' + ID + ';' + '\n' + items;//';' customers + observers
        return result;
    }
}