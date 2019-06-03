package com.example.kioskclient;

public class CartListViewItem {
    private String menuName ;
    private String menuOption;
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
    public void setOption(String str){
        menuOption = str;
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
    public String getMenuOption(){
        return  this.menuOption;
    }
}
