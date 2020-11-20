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
    String SinglePrice;
    String Unit;
    String Tag;


    public Item() {
        // So that App doesn't crash.
    }

    public Item(String title, String price, String quantity, String description, String imageUrl, ArrayList<String> images, String inStock, String singlePrice, String unit, String tag) {
        Title = title;
        Price = price;
        Quantity = quantity;
        Description = description;
        ImageUrl = imageUrl;
        Images = images;
        InStock = inStock;
        SinglePrice = singlePrice;
        Unit = unit;
        Tag = tag;
    }

    public String getTitle() {
        return Title;
    }

    public String getUnit() {
        return Unit;
    }

    public String getSinglePrice() {
        return SinglePrice;
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

    public String getTag() {
        return Tag;
    }
}
