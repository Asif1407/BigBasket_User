package DataModels;

public class ModelItemList {
    private String price, quantity, title, unit;

    public ModelItemList() {
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    public ModelItemList(String price, String quantity, String title, String unit) {
        this.price = price;
        this.quantity = quantity;
        this.title = title;
        this.unit = unit;
    }
}
