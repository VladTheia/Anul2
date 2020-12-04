import java.util.Vector;

public class BookDepartment extends Department{
    public BookDepartment(String name, Vector<Item>items,
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
