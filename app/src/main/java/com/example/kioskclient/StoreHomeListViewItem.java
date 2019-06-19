package com.example.kioskclient;

public class StoreHomeListViewItem {


    private String id = "" ;
    private String menuName ;
    private String menuOptionTemp ;
    private int menuCost_s ;
    private int menuCost_m ;
    private int menuCost_l ;


    public void setID(String str) {
        id = str ;
    }
    public void setName(String str) {
        menuName = str ;
    }
    public void setTemp (String str) { menuOptionTemp = str;}
    public void setCost_s (int cost) { menuCost_s = cost ;}
    public void setCost_m (int cost) { menuCost_m = cost ;}
    public void setCost_l (int cost) { menuCost_l = cost ;}



    public String getMenuID () {
        return this.id;
    }
    public String getMenuName() {
        return this.menuName ;
    }
    public String getMenuOptionTemp() {
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
