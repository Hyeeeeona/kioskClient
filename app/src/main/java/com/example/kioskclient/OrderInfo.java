package com.example.kioskclient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderInfo {

    private int order_id;  //PK
    private String order_time; //'2019-06-18 14:52:30'
    private String name;
    private String phone_number;
    private String reservation_time;
    private int shop_id;
    private int total;

    public OrderInfo() {
    }

    public OrderInfo(String name, String phone_number, int reservation_min, int shop_id, int total) {
        long mNow;
        Date mDate;
        Date rDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        rDate = new Date(mNow + (60000*reservation_min));
        this.name = name;
        this.order_time = mFormat.format(mDate);
        this.order_id = 0;
        this.phone_number = phone_number;
        this.reservation_time = mFormat.format(rDate);
        this.shop_id = shop_id;
        this.total = total;


    }

    public int getOrderId() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_time(){
        return order_time;
    }
    public void setOrder_time(){
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        this.order_time = mFormat.format(mDate);
    }
    public  String getName() {
        return name;
    }
    public  void setName(String name) {
        this.name = name;
    }

    public  String getPhone_number(){
        return phone_number;
    }
    public  void  setPhone_number(String phone_number){
        this.phone_number = phone_number;
    }

    public  String getReservation_time(){
        return reservation_time;
    }
    public void setReservation_time(int reservation_min){
        long mNow;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mNow = System.currentTimeMillis();
        Date rDate;
        rDate = new Date(mNow + (60000*reservation_min));
        this.reservation_time = mFormat.format(rDate);
    }
    public int getShopId() {
        return shop_id;
    }

    public void setShopId(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getTotal() {
        return total;
    }
    public void  setTotal(int total){
        this.total = total;
    }

}

