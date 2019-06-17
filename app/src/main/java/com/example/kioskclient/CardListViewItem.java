package com.example.kioskclient;

public class CardListViewItem {
    private String cardName ;
    private String cardNumber;
    private String cardCompany ;

    public void setCompany(String str) {
        cardCompany = str ;
    }
    public void setName(String str) {
        cardName = str ;
    }
    public void setNumber(String str){
        cardNumber = str;
    }

    public String getCardName() {
        return this.cardName ;
    }
    public String getCardCompany() {
        return this.cardCompany ;
    }
    public String getCardNumber(){
        return  this.cardNumber;
    }
}
