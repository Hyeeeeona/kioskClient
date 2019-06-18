package com.example.kioskclient;

public class OrderDetailInfo {

    private int id;
    private String menu_name; //'2019-06-18 14:52:30'
    private String menu_size;
    private int quantity;
    private int total;
    private int order_id;

    public OrderDetailInfo() {
    }

    public OrderDetailInfo(int id,int order_id, String menu_name, String menu_size, int quantity, int total) {
        this.id = id;
        this.order_id = order_id;
        this.menu_name = menu_name;
        this.menu_size = menu_size;
        this.quantity = quantity;
        this.total = total;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_size() {
        return menu_size;
    }

    public void setMenu_size(String menu_size) {
        this.menu_size = menu_size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

