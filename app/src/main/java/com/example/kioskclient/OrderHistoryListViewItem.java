package com.example.kioskclient;

import android.graphics.drawable.Drawable;

public class OrderHistoryListViewItem {
    private Drawable iconDrawable ;
    private String storeName ;
    private String menuStr ;
    private String date;

    public void setDate(String str) {
        this.date = str;
    }
    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setStoreName(String str) {
        storeName = str ;
    }
    public void setMenu (String menu) {
        menuStr = menu ;
    }

    public String getDate() {
        return this.date;
    }
    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getStoreName() {
        return this.storeName ;
    }
    public String getMenuStr() {
        return this.menuStr;
    }
}
