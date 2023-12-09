package com.cs407.ut;

public class ItemDataClass {
    private String itmeName, imageURL, itemDescriptions, itemPrice, itemCategory;

    public ItemDataClass(){

    }

    public String getItmeName() {
        return itmeName;
    }

    public void setItmeName(String itmeName) {
        this.itmeName = itmeName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getItemDescriptions() {
        return itemDescriptions;
    }

    public void setItemDescriptions(String itemDescriptions) {
        this.itemDescriptions = itemDescriptions;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemPrice = itemCategory;
    }

    public ItemDataClass(String itmeName, String imageURL, String itemDescriptions, String itemPrice, String itemCategory) {
        this.itmeName = itmeName;
        this.imageURL = imageURL;
        this.itemDescriptions = itemDescriptions;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
    }
}
