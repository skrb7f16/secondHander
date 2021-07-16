package sample.models;

public class Category {
    String categoryName,categoryAdded;
    int categoryId,totalItems;

    public Category(String categoryName, String categoryAdded, int categoryId, int totalItems) {
        this.categoryName = categoryName;
        this.categoryAdded = categoryAdded;
        this.categoryId = categoryId;
        this.totalItems = totalItems;
    }

    public Category() {
    }

    public Category(String categoryName, int categoryId) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryAdded() {
        return categoryAdded;
    }

    public void setCategoryAdded(String categoryAdded) {
        this.categoryAdded = categoryAdded;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
