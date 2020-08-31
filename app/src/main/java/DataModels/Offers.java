package DataModels;

public class Offers {

    String Title;
    String Price;
    String Quantity;
    String ImageUrl;
    String Description;

    public Offers() {
    }

    public Offers(String title, String price, String quantity, String imageUrl, String description) {
        Title = title;
        Price = price;
        Quantity = quantity;
        ImageUrl = imageUrl;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
