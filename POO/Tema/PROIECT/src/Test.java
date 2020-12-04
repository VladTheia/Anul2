import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Test {
    public static void main(String[] args) throws  Exception {
        //Writer for appending the output in the output file
        FileWriter writer = new FileWriter("output.txt");
        //Reading the input. Starting with the store data.
        File storeFile = new File("store.txt");
        Scanner sc = new Scanner(storeFile);

        //Naming the store
        Store.getInstance().setName(sc.nextLine());

        //Initializing the departments
        Department bookDepartment = new BookDepartment("BookDepartment",
                new Vector(), new Vector(),
                new Vector(), -1);
        Department musicDepartment = new MusicDepartment(
                "MusicDepartment",
                new Vector(), new Vector(),
                new Vector(), -1);
        Department softwareDepartment = new SoftwareDepartment(
                "SoftwareDepartment",
                new Vector(), new Vector(),
                new Vector(), -1);
        Department videoDepartment = new VideoDepartment(
                "VideoDepartment",
                new Vector(), new Vector(),
                new Vector(), -1);
        Store.getInstance().addDepartment(bookDepartment);
        Store.getInstance().addDepartment(musicDepartment);
        Store.getInstance().addDepartment(softwareDepartment);
        Store.getInstance().addDepartment(videoDepartment);

        //Getting department's data
        StringTokenizer data;
        String departmentType;
        int departmentID;

        //There are 4 departments, so we loop through the input 4 times
        for (int loop = 0; loop < 4; loop++) {
            data = new StringTokenizer(sc.nextLine(), ";");
            Vector departmentData = new Vector();
            //Store the data of every line in a vector to work easily with it
            while (data.hasMoreTokens())
                departmentData.add(data.nextToken());
            departmentType = (String) departmentData.get(0);
            departmentID = Integer.parseInt((String) departmentData.get(1));
            //Checking which department it's working with
            switch (departmentType) {
                case "BookDepartment":
                    bookDepartment.setID(departmentID);
                    break;
                case "MusicDepartment":
                    musicDepartment.setID(departmentID);
                    break;
                case "SoftwareDepartment":
                    softwareDepartment.setID(departmentID);
                    break;
                case "VideoDepartment":
                    videoDepartment.setID(departmentID);
                    break;
            }

            //Getting the items and adding them to their department
            Item currentItem;
            int nrOfItems = Integer.parseInt(sc.nextLine());
            for (int i = 0; i < nrOfItems; i++) {
                data = new StringTokenizer(sc.nextLine(), ";");
                Vector itemData = new Vector();
                while (data.hasMoreTokens()) {
                    itemData.add(data.nextToken());
                }
                currentItem = new Item((String) itemData.get(0),         //Name
                        Integer.parseInt((String) itemData.get(1)),      //ID
                        Float.parseFloat((String) itemData.get(2)),      //Price
                        Store.getInstance().getDepartment(departmentID));//Department
                Store.getInstance().getDepartment(departmentID).addItem
                        (currentItem);
            }
        }
        //Reading the customers data and adding them to the store
        File customersFile = new File("customers.txt");
        sc = new Scanner(customersFile);
        Strategy strategyA = new StrategyA();
        Strategy strategyB = new StrategyB();
        Strategy strategyC = new StrategyC();

        Customer currentCustomer;
        int nrOfCustomers = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < nrOfCustomers; i++) {
            data = new StringTokenizer(sc.nextLine(), ";");
            Vector customerData = new Vector();
            while (data.hasMoreTokens()) {
                customerData.add(data.nextToken());
            }
            Strategy currentStrategy = null;
            switch ((String) customerData.get(2)) {
                case "A":
                    currentStrategy = strategyA;
                    break;
                case "B":
                    currentStrategy = strategyB;
                    break;
                case "C":
                    currentStrategy = strategyC;
                    break;
            }
            currentCustomer = new Customer((String) customerData.get(0),
                    Float.parseFloat((String) customerData.get(1)),
                    currentStrategy);
            currentCustomer.getWishList().strategy = currentStrategy;
            Store.getInstance().enter(currentCustomer);
        }

        //Executing the events while reading them
        File eventsFile = new File("events.txt");
        sc = new Scanner(eventsFile);
        int nrOfEvents = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < nrOfEvents; i++) {
            Vector eventData = new Vector();
            data = new StringTokenizer(sc.nextLine(), ";");
            while (data.hasMoreTokens()) {
                eventData.add(data.nextToken());
            }
            String customerName;
            //choosing the action
            switch ((String) eventData.get(0)) {
                case "addItem":
                    Customer customer = Store.getInstance().getCustomer
                            ((String) eventData.get(3));
                    int itemID = Integer.parseInt((String) eventData.get(1));
                    Item item = Store.getInstance().getItem(itemID);
                    Department department = item.getDepartment();
                    //Determine weather it's added to the shoppingCart or to the WishList
                    if ("ShoppingCart".compareTo((String) eventData.get(2)) == 0) {
                        customer.getShoppingCart().add(item);
                    } else {
                        customer.getWishList().add(item);
                        if (!department.getObservers().contains(customer))
                            department.addObserver(customer);
                    }
                    break;
                case "delItem":
                    customer = Store.getInstance().getCustomer
                            ((String) eventData.get(3));
                    itemID = Integer.parseInt((String) eventData.get(1));
                    item = Store.getInstance().getItem(itemID);
                    department = item.getDepartment();
                    if ("ShoppingCart".compareTo((String) eventData.get(2)) == 0) {
                        customer.getShoppingCart().remove(item);
                    } else {
                        customer.getWishList().remove(item);
                        ListIterator<Item> it = customer.getWishList().listIterator();
                        /*check if customer has any other items from this
                          department, to determine if he's still an observer*/
                        int ok = 0;
                        while (it.hasNext()) {
                            if (it.next().getDepartment().getID() == department.getID()) {
                                ok = 1;
                                break;
                            }
                        }
                        if (ok == 0)
                            department.removeObserver(customer);
                    }
                    break;
                case "addProduct":
                    departmentID = Integer.parseInt((String) eventData.get(1));
                    itemID = Integer.parseInt((String) eventData.get(2));
                    float price = Float.parseFloat((String) eventData.get(3));
                    String itemName = (String) eventData.get(4);
                    department = Store.getInstance().getDepartment(departmentID);
                    Item addedItem = new Item(itemName, itemID, price, department);
                    department.addItem(addedItem);
                    NotificationType nt = NotificationType.ADD;
                    Notification notification = new Notification(nt, departmentID, itemID);
                    department.notifyAllObservers(notification);
                    break;
                case "modifyProduct":
                    departmentID = Integer.parseInt((String) eventData.get(1));
                    itemID = Integer.parseInt((String) eventData.get(2));
                    price = Float.parseFloat((String) eventData.get(3));
                    department = Store.getInstance().getDepartment(departmentID);
                    Store.getInstance().getItem(itemID).setPrice(price);
                    nt = NotificationType.MODIFY;
                    notification = new Notification(nt, departmentID, itemID);
                    department.notifyAllObservers(notification);
                    break;
                case "delProduct":
                    itemID = Integer.parseInt((String) eventData.get(1));
                    item = Store.getInstance().getItem(itemID);
                    department = item.getDepartment();
                    department = Store.getInstance().getDepartment(department.getID());
                    item = department.getItem(itemID);
                    Store.getInstance().getDepartment(department.getID()).getItems().
                            remove(item);
                    for (Customer customer1 : Store.getInstance().getCustomers()) {
                        if (customer1.getWishList().contains(item)) {
                            customer1.getWishList().remove(item);
                            ListIterator<Item> it = customer1.getWishList().listIterator();
                        /*check if customer has any other items from this
                          department, to determine if he's still an observer*/
                            int ok = 0;
                            while (it.hasNext()) {
                                if (it.next().getDepartment().getID() == department.getID()) {
                                    ok = 1;
                                    break;
                                }
                            }
                            if (ok == 0)
                                department.removeObserver(customer1);
                        }
                    }
                    nt = NotificationType.REMOVE;
                    notification = new Notification(nt, department.getID(), itemID);
                    department.notifyAllObservers(notification);
                    break;
                case "getItem":
                    customerName = (String) eventData.get(1);
                    customer = Store.getInstance().getCustomer(customerName);
                    item = customer.getWishList().executeStrategy();
                    department = item.getDepartment();
                    department = Store.getInstance().getDepartment(department.getID());
                    customer.getShoppingCart().add(item);
                    customer.getWishList().remove(item);
                    ListIterator<Item> it = customer.getWishList().listIterator();
                        /*check if customer has any other items from this
                          department, to determine if he's still an observer*/
                    int ok = 0;
                    while (it.hasNext()) {
                        if (it.next().getDepartment().getID() == department.getID()) {
                            ok = 1;
                            break;
                        }
                    }
                    if (ok == 0)
                        department.removeObserver(customer);
                    writer.write(item + "\n");
                    break;
                case "getItems":
                    /*In order to keep the output format the same, the items
                     * have to be put in a vector*/
                    Vector items;
                    customerName = (String) eventData.get(2);
                    customer = Store.getInstance().getCustomer(customerName);
                    if ("ShoppingCart".compareTo((String) eventData.get(1)) == 0) {
                        it = customer.getShoppingCart().listIterator();
                        items = new Vector();
                        while (it.hasNext()) {
                            items.add(it.next());
                        }
                        writer.write(items + "\n");
                    } else {
                        it = customer.getWishList().listIterator();
                        items = new Vector();
                        while (it.hasNext()) {
                            items.add(it.next());
                        }
                        writer.write(items + "\n");
                    }
                    break;
                case "getTotal":
                    customerName = (String) eventData.get(2);
                    customer = Store.getInstance().getCustomer(customerName);
                    String total;
                    //Chose fixed precision to match the required output
                    if ("ShoppingCart".compareTo((String) eventData.get(1)) == 0) {
                        total = String.format("%.2f", customer.getShoppingCart().getTotalPrice());
                        writer.write(total + "\n");
                    } else {
                        total = String.format("%.2f", customer.getWishList().getTotalPrice());
                        writer.write(total + "\n");
                    }
                    break;
                case "accept":
                    departmentID = Integer.parseInt((String) eventData.get(1));
                    customerName = (String) eventData.get(2);
                    department = Store.getInstance().getDepartment(departmentID);
                    customer = Store.getInstance().getCustomer(customerName);
                    department.accept(customer.getShoppingCart());
                    break;
                case "getObservers":
                    departmentID = Integer.parseInt((String) eventData.get(1));
                    department = Store.getInstance().getDepartment(departmentID);
                    writer.write(department.getObservers() + "\n");
                    break;
                case "getNotifications":
                    customerName = (String) eventData.get(1);
                    customer = Store.getInstance().getCustomer(customerName);
                    writer.write(customer.getNotifications() + "\n");
                    break;
            }
        }
        writer.close();
    }
}