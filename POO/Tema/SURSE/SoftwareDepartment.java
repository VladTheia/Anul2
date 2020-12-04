import java.util.Vector;

public class SoftwareDepartment extends Department{
    public SoftwareDepartment(String name, Vector<Item> items,
                           Vector<Customer> customers, Vector<Customer> observers,
                           int ID) {
        this.name = name;
        this.items = items;
        this.customers = customers;
        this.observers = observers;
        this.ID = ID;
    }

    void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
