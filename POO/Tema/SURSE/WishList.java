import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

class WishListComparator implements Comparator<Item> {
    @Override
    public int compare(Item o1, Item o2) {
        return o1.getName().compareTo(o2.getName());
    }
}


public class WishList extends ItemList{
    Vector<Item> items;
    Strategy strategy;
    Item mostRecent;


    public WishList() {
        comparator = new WishListComparator();
    }

    @Override
    public boolean add(Item element) {
        mostRecent = element;
        return super.add(element);
    }

    //Used for StrategyA
    public Item cheapest() {
        Item item;
        Item cheapest = getItem(0);
        ListIterator<Item> it = listIterator();
        while (it.hasNext()) {
            item = it.next();
            if (cheapest.getPrice() > item.getPrice())
                cheapest = item;
        }
        return cheapest;
    }

    //Used for strategyB
    public Item alphabetical() {
        //System.out.println("Strategy B wishlist: " + this);
        ListIterator<Item> it = listIterator();
        return it.next();
    }

    //Used for strategyC
    Item getMostRecent() {
        return mostRecent;
    }

    Item executeStrategy() {
        return strategy.execute(this);
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
