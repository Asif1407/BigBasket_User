package DataModels;

import java.util.ArrayList;

public class Item {

    String Title;
    String Price;
    String Quantity;
    String Description;
    String ImageUrl;
    ArrayList<String> Images;
    String InStock;

    public Item() {
        // So that App doesn't crash.
    }

    public Item(String title, String price, String quantity, String description, String imageUrl, ArrayList<String> images, String inStock) {
        Title = title;
        Price = price;
        Quantity = quantity;
        Description = description;
        ImageUrl = imageUrl;
        Images = images;
        InStock = inStock;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }

    public String getInStock() {
        return InStock;
    }

    public void setInStock(String inStock) {
        InStock = inStock;
    }
}
