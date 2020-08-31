package DataModels;

public class History {

    String username;
    String date;
    String time;
    String totalProducts;
    String totalAmount;

    public History() {
    }

    public History(String username, String date, String time, String totalProducts, String totalAmount) {
        this.username = username;
        this.date = date;
        this.time = time;
        this.totalProducts = totalProducts;
        this.totalAmount = totalAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
