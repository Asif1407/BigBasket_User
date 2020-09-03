package com.example.bigbasket_user.Model;

public class ModelCart {
    private String  Description, ImageUrl,
            Price, Quantity, Title, SinglePrice;

    public ModelCart() {
    }

    public String getDescription() {
        return Description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getTitle() {
        return Title;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSinglePrice() {
        return SinglePrice;
    }

    public ModelCart(String description, String imageUrl, String price, String quantity, String title, String singlePrice) {
        Description = description;
        ImageUrl = imageUrl;
        Price = price;
        Quantity = quantity;
        Title = title;
        SinglePrice = singlePrice;
    }
}
