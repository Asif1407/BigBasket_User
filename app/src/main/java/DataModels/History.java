package DataModels;

public class History {

    private String name, address, orderStatus, paymentStatus, pNumber, pinCode, timeStamp, totalPrice, transactionId;

    public History() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getpNumber() {
        return pNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public History(String name, String address, String orderStatus, String paymentStatus, String pNumber, String pinCode, String timeStamp, String totalPrice, String transactionId) {
        this.name = name;
        this.address = address;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.pNumber = pNumber;
        this.pinCode = pinCode;
        this.timeStamp = timeStamp;
        this.totalPrice = totalPrice;
        this.transactionId = transactionId;
    }
}
