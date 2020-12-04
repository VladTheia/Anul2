public class Item {
    private String name; //Item's name
    private int ID; //Item's unique ID
    private float price; //Item's price
    private Department department; //The department it belongs to

    public Item() {
    }

    public Item(String name, int ID, float price, Department department) {
        this.name = name;
        this.ID = ID;
        this.price = price;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getPrice() { return price; }

    public void setPrice(float price) { this.price = price; }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        String s = String.format("%.2f", price);
        return name + ';' + ID + ';' + s;
    }
}