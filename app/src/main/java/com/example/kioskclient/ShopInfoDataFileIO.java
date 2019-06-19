package com.example.kioskclient;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class ShopInfoDataFileIO {
    final static String filename = "ShopInfo.json";

    public static JSONObject makeShopInfoDataJson(ShopInfo shopInfo){
        JSONObject shopInfoDataJsonObject = new JSONObject();
        try {
            shopInfoDataJsonObject.put("shop_id", shopInfo.getShopId());
            shopInfoDataJsonObject.put("shop_name", shopInfo.getShopName());
            shopInfoDataJsonObject.put("shop_tel", shopInfo.getShopTel());
            shopInfoDataJsonObject.put("shop_businesshours", shopInfo.getBusinessHours());
            shopInfoDataJsonObject.put("shop_personalday", shopInfo.getPersonalDay());
            shopInfoDataJsonObject.put("shop_introduction", shopInfo.getIntroduction());
            shopInfoDataJsonObject.put("shop_address", shopInfo.getShopAddress());

        }catch (JSONException e){
            e.printStackTrace();
        }
        return shopInfoDataJsonObject;
    }

    public static void saveShopInfoDataJson(Context context, JSONObject jsonObject){

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isExistShopInfoData(Context context) {
        Log.d("debugging", context.getFilesDir().toString());
        File file = context.getFileStreamPath(filename);
            return file.exists();
    }

    public static void saveShopInfoEmptyData(Context context){
        JSONObject jsonObject = makeShopInfoDataJson(new ShopInfo());

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonObject.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static JSONObject readShopInfoDataJson(Context context){
        JSONObject jsonObject = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String strBuf = bufferedReader.readLine();
            jsonObject = new JSONObject(strBuf);

        }catch (Exception e){
            e.printStackTrace();
            ShopInfoDataFileIO.saveShopInfoEmptyData(context);
        }
        return jsonObject;
    }
}
