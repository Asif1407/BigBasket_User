package DataModels;

import java.util.ArrayList;

public class Item {

    String Title;
    String Description;
    int price;
    String quantity;
    String inStock;
    String ImageUrl;
    ArrayList<String> images;

    public Item() {
        // So that App doesn't crashes.
    }

    public Item(String title, String description, int price, String quantity, String inStock, String imageUrl, ArrayList<String> images) {
        Title = title;
        Description = description;
        this.price = price;
        this.quantity = quantity;
        this.inStock = inStock;
        ImageUrl = imageUrl;
        this.images = images;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }




    //    public Item(String title, String description, int price, String quantity, String instock, ArrayList<String> images) {
//        this.title = title;
//        this.description = description;
//        this.price = price;
//        this.quantity = quantity;
//        this.instock = instock;
//        this.images = images;
//    }
//
//    //dummy constructor for testing
//
//
//    public Item(String title, String description, int price, String quantity, String instock) {
//        this.title = title;
//        this.description = description;
//        this.price = price;
//        this.quantity = quantity;
//        this.instock = instock;
//    }
//    //the above constructor can be deleted
//
//    public Item() {
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }
//
//    public String getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(String quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getInstock() {
//        return instock;
//    }
//
//    public void setInstock(String instock) {
//        this.instock = instock;
//    }
//
//    public ArrayList<String> getImages() {
//        return images;
//    }
//
//    public void setImages(ArrayList<String> images) {
//        this.images = images;
//    }
}
