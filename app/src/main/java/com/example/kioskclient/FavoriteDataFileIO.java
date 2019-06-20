package com.example.kioskclient;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FavoriteDataFileIO {
    public static void saveAddFavoriteData(Context context, int store_id){
        JSONArray jsonArray = readFavoriteDataJson(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("store_id",store_id);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        saveFavoriteDataJson(context,jsonArray);
    }

    public static void deleteFavoriteData(Context context, int store_id){
        JSONArray jsonArray = readFavoriteDataJson(context);
        JSONArray result = new JSONArray();
        for(int i = 0;i<jsonArray.length();i++){
            if(jsonArray.isNull(i)){

            } else {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int ibuf = jsonObject.getInt("store_id");
                    if(ibuf != store_id){
                        result.put(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        saveFavoriteDataJson(context,result);
    }

    public static void saveFavoriteDataJson(Context context, JSONArray jsonArray){
        String filename = "FavoriteData.json";

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            Log.d("Json_fa",jsonArray.toString());
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static void saveFavoriteEmptyData(Context context){
        String filename = "FavoriteData.json";
        JSONArray jsonArray = new JSONArray();
        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            Log.d("Json_fa",jsonArray.toString());
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static JSONArray readFavoriteDataJson(Context context){
        String filename = "FavoriteData.json";
        JSONArray jsonArray = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String strBuf = bufferedReader.readLine();
            jsonArray = new JSONArray(strBuf);

        }catch (Exception e){
            e.printStackTrace();

            FavoriteDataFileIO.saveFavoriteEmptyData(context);
        }
        return jsonArray;
    }

}
