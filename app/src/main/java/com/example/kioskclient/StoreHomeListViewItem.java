package com.example.kioskclient;

public class StoreHomeListViewItem {


    private int id ;
    private String menuName ;
    private int menuOptionTemp ;
    private int menuCost_s ;
    private int menuCost_m ;
    private int menuCost_l ;


    public void setID(int str) {
        id = str ;
    }
    public void setName(String str) {
        menuName = str ;
    }
    public void setTemp (int str) { menuOptionTemp = str;}
    public void setCost_s (int cost) { menuCost_s = cost ;}
    public void setCost_m (int cost) { menuCost_m = cost ;}
    public void setCost_l (int cost) { menuCost_l = cost ;}



    public int getMenuID () {
        return this.id;
    }
    public String getMenuName() {
        return this.menuName ;
    }
    public int getMenuOptionTemp() {
        return this.menuOptionTemp ;
    }
    public int getMenuCost_s() {
        return this.menuCost_s ;
    }
    public int getMenuCost_m() {
        return this.menuCost_m ;
    }
    public int getMenuCost_l() {
        return this.menuCost_l ;
    }



}
