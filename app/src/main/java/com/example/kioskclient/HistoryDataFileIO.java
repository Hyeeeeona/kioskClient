package com.example.kioskclient;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class HistoryDataFileIO {

    public static JSONObject makeHistoryDataJson(int shop_id, String shop_name, String history_menu,long date){
        JSONObject result = new JSONObject();
        try {
            result.put("shop_id",shop_id);
            result.put("shop_name",shop_name);
            result.put("history_menu",history_menu);
            result.put("date",date);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void saveAddHistoryDataJson(Context context, JSONObject jsonObject){
        JSONArray jsonArray = readHistoryDataJson(context);
        jsonArray.put(jsonObject);
        saveHistoryDataJson(context,jsonArray);
    }
    public static void saveHistoryDataJson(Context context, JSONArray jsonArray){
        String filename = "HistoryData.json";

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static void saveHistoryEmptyData(Context context){
        String filename = "HistoryData.json";
        JSONArray jsonArray = new JSONArray();
        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static JSONArray readHistoryDataJson(Context context){
        String filename = "HistoryData.json";
        JSONArray jsonArray = null;
        try{
            FileInputStream fileInputStream = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String strBuf = bufferedReader.readLine();
            jsonArray = new JSONArray(strBuf);

        }catch (Exception e){
            e.printStackTrace();

            //FIXME: 작업 이후 삭제
            // CardDataFileIO 테스트 임시 데이터 저장
    /*
            String string = JsonExample.makeJsonStringCardDataExample();
            try {
                jsonArray = new JSONArray(string);
                HistoryDataFileIO.saveHistoryDataJson(context,jsonArray);
            }catch (Exception ee){
                e.printStackTrace();
            }
            */
            //TODO: 작업이후 추가
            HistoryDataFileIO.saveHistoryEmptyData(context);
        }
        return jsonArray;
    }

}
