package sample.models;

public class Requests {
    int id,fromUser,toUser,onProduct,isAccepted,offeredPrice;
    String message,onDate;

    public Requests(int id, int fromUser, int toUser, int onProduct, int isAccepted, int offeredPrice, String message, String onDate) {
        this.id = id;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.onProduct = onProduct;
        this.isAccepted = isAccepted;
        this.offeredPrice = offeredPrice;
        this.message = message;
        this.onDate = onDate;
    }

    public Requests() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromUser() {
        return fromUser;
    }

    public void setFromUser(int fromUser) {
        this.fromUser = fromUser;
    }

    public int getToUser() {
        return toUser;
    }

    public void setToUser(int toUser) {
        this.toUser = toUser;
    }

    public int getOnProduct() {
        return onProduct;
    }

    public void setOnProduct(int onProduct) {
        this.onProduct = onProduct;
    }

    public int getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(int isAccepted) {
        this.isAccepted = isAccepted;
    }

    public int getOfferedPrice() {
        return offeredPrice;
    }

    public void setOfferedPrice(int offeredPrice) {
        this.offeredPrice = offeredPrice;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }
}
