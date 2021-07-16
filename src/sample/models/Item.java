package sample.models;

import sample.resources.Params;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDateTime;

public class Item {
    int id;
    int itemType,postedBy,soldTo,isSold,price,soldPrice;
    String itemName,datePosted,itemDescription,itemPic;

    public Item(int itemType, int postedBy, int price, String itemName, String itemDescription) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.itemType = itemType;
        this.postedBy = postedBy;
        this.price = price;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        makePic();
    }

    private void makePic() throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec((itemName+ LocalDateTime.now()+this.postedBy).toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<hash.length;i++){
            sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
        }
        this.itemPic= sb.toString();
    }

    public Item(int id, int itemType, int postedBy, int soldTo, int isSold, int price, int soldPrice, String itemName, String datePosted, String itemDescription, String itemPic) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.id = id;
        this.itemType = itemType;
        this.postedBy = postedBy;
        this.soldTo = soldTo;
        this.isSold = isSold;
        this.price = price;
        this.soldPrice = soldPrice;
        this.itemName = itemName;
        this.datePosted = datePosted;
        this.itemDescription = itemDescription;
        this.itemPic = itemPic;
        makePic();
    }

    public Item() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(int postedBy) {
        this.postedBy = postedBy;
    }

    public int getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(int soldTo) {
        this.soldTo = soldTo;
    }

    public int getIsSold() {
        return isSold;
    }

    public void setIsSold(int isSold) {
        this.isSold = isSold;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(int soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }
}
