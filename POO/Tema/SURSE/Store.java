import java.util.Iterator;
import java.util.Vector;

public class Store {
    private static Store obj = null;
    String name; //Store's name
    Vector<Department> departments; //Store's departments
    Vector<Customer> customers; //Customers that are in the store

    //Lazy instatiation
    private Store() {
        departments = new Vector();
        customers = new Vector();
    }

    public static Store getInstance() {
        if (obj == null)
            obj = new Store();
        return obj;
    }

    //Sets the store's name
    void setName(String name) {
        Store.getInstance().name = name;
    }

    //Client enters the store
    void enter(Customer customer) {
        if (!Store.getInstance().customers.contains(customer))
            Store.getInstance().customers.add(customer);
    }

    //Client exits the store
    void exit(Customer customer) {
        if (Store.getInstance().customers.contains(customer))
            Store.getInstance().customers.remove(customer);
    }

    //Returns a shopping cart that'll be used by a customer
    ShoppingCart getShoppingCart(float budget) {
        ShoppingCart shoppingCart = new ShoppingCart(budget);
        return shoppingCart;
    }

    //Gets all the customers that are in the store at that moment
    Vector<Customer> getCustomers() {
        return Store.getInstance().customers;
    }

    //Search customers by name
    Customer getCustomer(String name) {
        for (Customer customer : Store.getInstance().customers) {
            if (customer.getName().compareTo(name) == 0)
                return customer;
        }
        return null;
    }

    //TGets all the departments
    Vector<Department> getDepartments() {
        return Store.getInstance().departments;
    }

    //Adds a department to the store
    void addDepartment(Department department) {
        if (!Store.getInstance().departments.contains(department))
            Store.getInstance().departments.add(department);
    }

    //Gets the department with that ID
    Department getDepartment(int ID) {
        for (Department department : departments) {
            if (department.getID() == ID)
                return department;
        }
        return null;
    }

    Item getItem(int ID) {
        for (Department department : departments) {
            for (Item item : department.items) {
                if (item.getID() == ID)
                    return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String result = "";
        result += getInstance().name + '\n';
        result += getInstance().getDepartments().toString();
        return result;
    }
}