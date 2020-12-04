public class StrategyA implements Strategy{
    public Item execute(WishList wishList) {
        return wishList.cheapest();
    }

    public String toString() {
        return "A\n";
    }
}
