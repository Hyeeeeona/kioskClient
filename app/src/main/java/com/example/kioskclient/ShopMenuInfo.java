package com.example.kioskclient;

public class ShopMenuInfo {
    private int id;
    private String menu_name;
    private String menu_size;
    private int hotorcold;
    private int menu_price;
    private int shop_id_id;
    private String menu_img;

    public ShopMenuInfo() {
    }
    public ShopMenuInfo(int id, String menu_name, String menu_size, int hotorcold, int menu_price, int shop_id_id, String menu_img) {
        this.id = id;
        this.menu_name = menu_name;
        this.menu_size = menu_size;
        this.hotorcold = hotorcold;
        this.menu_price = menu_price;
        this.shop_id_id = shop_id_id;
        this.menu_img = menu_img;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getMenuName() {
        return menu_name;
    }

    public void setMenuName(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenuSize() {
        return menu_size;
    }

    public void setMenuSize(String menu_size) {
        this.menu_size = menu_size;
    }

    public int getHotOrCold() { return hotorcold; }

    public void setHotOrCold(int hotorcold) {
        this.hotorcold = hotorcold;
    }

    public int getMenuPrice() {
        return menu_price;
    }

    public void setMenuPrice(int menu_price) {
        this.menu_price = menu_price;
    }

    public int getShopIdId() {
        return shop_id_id;
    }

    public void setShopIdId(int shop_id_id) {
        this.shop_id_id = shop_id_id;
    }

    public String getMenuImg() {
        return menu_img;
    }

    public void setMenuImg(String menu_img) {
        this.menu_img = menu_img;
    }

}

