package com.example.kioskclient;

public class OrderInfo {

    private String uid;
    private int order_id;  //PK
    private int shop_id;
    private String order_time; //'2019-06-18 14:52:30'
    private String user_tel;
    private String order_cost;
    private String reservation_time;

    public OrderInfo() {
    }
    public OrderInfo(String uid, int shop_id, String shop_name, String shop_tel, String business_hours, String personal_day, String introduction) {
        this.uid = uid;
        this.shop_id = shop_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getShopId() {
        return shop_id;
    }

    public void setShopId(int shop_id) {
        this.shop_id = shop_id;
    }


}

