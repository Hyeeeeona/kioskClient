package com.example.kioskclient;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class CartDataFileIO {

    public static JSONObject makeCartDataJson(String storeName, JSONArray menuData){
        JSONObject result = new JSONObject();
        try {
            result.put("StoreName",storeName);
            result.put("MenuData",menuData);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void saveCartDataJson(Context context, JSONObject jsonObject){
        String filename = "CartData.json";

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static void saveCartEmptyData(Context context){
        String filename = "CartData.json";
        JSONObject jsonObject = makeCartDataJson("", new JSONArray());

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static JSONObject readCartDataJson(Context context){
        String filename = "CartData.json";
        JSONObject jsonObject = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String strBuf = bufferedReader.readLine();
            jsonObject = new JSONObject(strBuf);

        }catch (Exception e){
            e.printStackTrace();

            //FIXME: 작업 이후 삭제
            // CartDataFileIO 테스트 임시 데이터 저장

            String string = JsonExample.makeJsonStringCartDataExample();
            try {
                jsonObject = new JSONObject(string);
                CartDataFileIO.saveCartDataJson(context,jsonObject);
            }catch (Exception ee){
                e.printStackTrace();
            }
            //TODO: 작업이후 추가
            // CartDataFileIO.saveCartEmptyData(getApplicationContext());
        }
        return jsonObject;
    }
}
