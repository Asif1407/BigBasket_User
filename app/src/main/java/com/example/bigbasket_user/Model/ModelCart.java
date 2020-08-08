package com.example.bigbasket_user.Model;

public class ModelCart {
    private String  price, category,
            productImage, quantity, title, productId;

    public ModelCart() {
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getProductId() {
        return productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ModelCart(String price, String category, String productImage, String quantity, String title, String productId) {
        this.price = price;
        this.category = category;
        this.productImage = productImage;
        this.quantity = quantity;
        this.title = title;
        this.productId = productId;
    }
}
