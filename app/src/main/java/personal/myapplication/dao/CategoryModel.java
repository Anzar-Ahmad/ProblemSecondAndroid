package personal.myapplication.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class CategoryModel {
    @SerializedName("itemName")
    @Expose
    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}