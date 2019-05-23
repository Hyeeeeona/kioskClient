package com.example.kioskclient;

import android.content.Intent;

public class CartListViewItem {
    private String menuName ;
    private int menuCost ;
    private int menuCount ;

    public void setCount(int count) {
        menuCount = count ;
    }
    public void setName(String str) {
        menuName = str ;
    }
    public void setCost (int cost) {
        menuCost = cost;
    }

    public String getMenuName() {

        return this.menuName ;
    }
    public int getMenuCost() {

        return this.menuCost ;
    }
    public int getMenuCount()
    {
        return this.menuCount;
    }
}
