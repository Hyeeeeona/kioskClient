package com.example.kioskclient;

import android.graphics.drawable.Drawable;

public class FavoriteListViewItem {
    private String iconDrawable ;
    private String storeName ;
    private String menuStr ;
    private int storeId;

    public void setIcon(String icon) {
        iconDrawable = icon ;
    }
    public void setStoreName(String str) {
        storeName = str ;
    }
    public void setMenu (String menu) {
        menuStr = menu ;
    }
    public void setStoreId (int id) { storeId = id; }

    public String getIcon() {
        return this.iconDrawable ;
    }
    public String getStoreName() {
        return this.storeName ;
    }
    public String getMenuStr() {
        return this.menuStr;
    }
    public int getStoreId() { return this.storeId; }
}
