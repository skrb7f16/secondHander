package sample.models;

public class Rewards {
    int id,toUser,amount,redeemed,onProduct,onRequest;
    String dateRecieved;

    public Rewards(int id, int toUser, int amount, int redeemed, int onProduct, int onRequest, String dateRecieved) {
        this.id = id;
        this.toUser = toUser;
        this.amount = amount;
        this.redeemed = redeemed;
        this.onProduct = onProduct;
        this.onRequest = onRequest;
        this.dateRecieved = dateRecieved;
    }

    public Rewards(int toUser, int amount, int redeemed, int onProduct, int onRequest) {
        this.toUser = toUser;
        this.amount = amount;
        this.redeemed = redeemed;
        this.onProduct = onProduct;
        this.onRequest = onRequest;
    }

    public Rewards() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(int redeemed) {
        this.redeemed = redeemed;
    }

    public int getOnProduct() {
        return onProduct;
    }

    public void setOnProduct(int onProduct) {
        this.onProduct = onProduct;
    }

    public int getOnRequest() {
        return onRequest;
    }

    public void setOnRequest(int onRequest) {
        this.onRequest = onRequest;
    }

    public String getDateRecieved() {
        return dateRecieved;
    }

    public void setDateRecieved(String dateRecieved) {
        this.dateRecieved = dateRecieved;
    }
}
