public class StrategyB implements Strategy{
    public Item execute(WishList wishList) {
        return wishList.alphabetical();
    }

    public String toString() {
        return "B\n";
    }
}
