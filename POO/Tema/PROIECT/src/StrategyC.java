public class StrategyC  implements Strategy{
    public Item execute(WishList wishList) {
        return wishList.getMostRecent();
    }

    public String toString() {
        return "C\n";
    }
}