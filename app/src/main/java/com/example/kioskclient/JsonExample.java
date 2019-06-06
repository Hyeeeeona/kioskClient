package com.example.kioskclient;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonExample {

    public static String makeJsonStringCartDataExample(){
        String result;

        JSONObject cartDataJsonObject = new JSONObject();
        JSONArray menuDataJsonArray = new JSONArray();
        JSONObject menuDataJsonObject = new JSONObject();
        try {
            //매장이름
            cartDataJsonObject.put("StoreName","현아네 떡볶이");

            //장바구니 메뉴 리스트
            menuDataJsonObject.put("MenuName","현아네 떡볶이");
            menuDataJsonObject.put("MenuCount",1);
            menuDataJsonObject.put("MenuOption","달콤한맛");
            menuDataJsonObject.put("MenuCost",4000);
            menuDataJsonArray.put(menuDataJsonObject);

            menuDataJsonObject = new JSONObject();
            menuDataJsonObject.put("MenuName","세희네 튀김");
            menuDataJsonObject.put("MenuCount",1);
            menuDataJsonObject.put("MenuOption","섞어서");
            menuDataJsonObject.put("MenuCost",3500);
            menuDataJsonArray.put(menuDataJsonObject);

            menuDataJsonObject = new JSONObject();
            menuDataJsonObject.put("MenuName","진아네 순대");
            menuDataJsonObject.put("MenuCount",1);
            menuDataJsonObject.put("MenuOption","순대만");
            menuDataJsonObject.put("MenuCost",4500);
            menuDataJsonArray.put(menuDataJsonObject);

            menuDataJsonObject = new JSONObject();
            menuDataJsonObject.put("MenuName","선화네 어묵");
            menuDataJsonObject.put("MenuCount",1);
            menuDataJsonObject.put("MenuOption","유부주머니 추가");
            menuDataJsonObject.put("MenuCost",4500);
            menuDataJsonArray.put(menuDataJsonObject);

            cartDataJsonObject.put("MenuData",menuDataJsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        result = cartDataJsonObject.toString();
        return result;
    }
}
