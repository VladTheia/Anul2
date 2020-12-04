import java.util.Collection;
import java.util.Comparator;
import java.util.ListIterator;

public class ShoppingCart extends ItemList implements Visitor{
    private float budget;

    class ItemComparator implements Comparator<Item> {
        @Override
        public int compare(Item o1, Item o2) {
            if (o1.getPrice() > o2.getPrice())
                return 1;
            if (o1.getPrice() < o2.getPrice())
                return -1;
            //If they have the same price, compare the names
            return o1.getName().compareTo(o2.getName());
        }
    }


    public ShoppingCart(float budget) {
        this.budget = budget;
        this.head = null;
        this.length = 0;
        this.comparator = new ItemComparator();
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    @Override
    public boolean add(Item element) {
        if (getTotalPrice() + element.getPrice() < budget) {
            setBudget(getBudget() - element.getPrice());
            /*Sends a new so it doesen't change when the items in certain
              departments change*/
            Item newItem = new Item(element.getName(), element.getID(),
                    element.getPrice(), element.getDepartment());
            super.add(newItem);
        }
        return false;
    }

    @Override
    public boolean remove(Item item) {
        setBudget(getBudget() + item.getPrice());
        return super.remove(item);
    }

    @Override
    public boolean addAll(Collection<? extends Item> c) {
        for (Item item : c) {
            if (add(item))
                continue;
            else
                return false;
        }
        return true;
    }

    //Lowers the price of any of this department's item by 10%
    public void visit(BookDepartment bookDepartment) {
        ListIterator<Item> it = listIterator();
        Item current;
        while (it.hasNext()) {
            current = it.next();
            float currPrice = current.getPrice();
            if (current.getDepartment().getID() == bookDepartment.getID())
                current.setPrice(currPrice -  currPrice / 10);
        }
    }

    //Adds to the shoppingCart budget 10% of this department's items total price
    public void visit(MusicDepartment musicDepartment) {
        float totalPriceOfItems = 0;
        for (Item item : musicDepartment.getItems()) {
            totalPriceOfItems += item.getPrice();
        }
        setBudget((getTotalPrice() + totalPriceOfItems / 10));
    }

    /*If the budget isn't enought to buy the cheapest product,
      this department's items price lower by 20%*/
    public void visit(SoftwareDepartment softwareDepartment) {
        if (getBudget() < getTotalPrice() +
                softwareDepartment.cheapest().getPrice()) {
            ListIterator<Item> it = listIterator();
            Item current;
            while (it.hasNext()) {
                current = it.next();
                float currPrice = current.getPrice();
                if (current.getDepartment().getID() == softwareDepartment.getID())
                    current.setPrice(currPrice - (currPrice / 5));
            }
        }
    }

    /*Lowers all this department's items price by 15% if their total price is
      higher than the most expensive item. Also adds to the budget 5% of the
      total price*/
    public void visit(VideoDepartment videoDepartment) {
        float totalPrice = 0;
        for (Item item : videoDepartment.getItems()) {
            if (contains(item))
                totalPrice += item.getPrice();
        }
        setBudget(getBudget() + totalPrice / 20);

        if (totalPrice > videoDepartment.mostExpensive().getPrice()) {
            ListIterator<Item> it = listIterator();
            Item current;
            while (it.hasNext()) {
                current = it.next();
                float currPrice = current.getPrice();
                if (current.getDepartment().getID() == videoDepartment.getID())
                    current.setPrice(currPrice - (currPrice * 3/20));
            }
        }
    }

    @Override
    public String toString() {
        String result = "";
        ListIterator<Item> it = this.listIterator();
        while (it.hasNext()) {
            Item item =  it.next();
            result += item.toString();
        }
        return result;
    }
}