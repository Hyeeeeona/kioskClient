package com.example.kioskclient;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class CardDataFileIO {

    public static JSONObject makeCardDataJson(String cardCompany,String cardNickName, String cardNumber){
        JSONObject result = new JSONObject();
        try {
            result.put("cardCompany",cardCompany);
            result.put("cardNumber",cardNumber);
            result.put("cardNickName",cardNickName);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return result;
    }

    public static void saveCardDataJson(Context context, JSONArray jsonArray){
        String filename = "CardData.json";

        FileOutputStream outputStream;
        try{
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(jsonArray.toString().getBytes());
            outputStream.close();
        }catch( Exception e){
            e.printStackTrace();
        }
    }

    public static void saveCardEmptyData(Context context){
        String filename = "CardData.json";
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

    public static JSONArray readCardDataJson(Context context){
        String filename = "CardData.json";
        JSONArray jsonArray = new JSONArray();
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
                CardDataFileIO.saveCardDataJson(context,jsonArray);
            }catch (Exception ee){
                e.printStackTrace();
            }*/
            //TODO: 작업이후 추가
            CardDataFileIO.saveCardEmptyData(context);
        }
        return jsonArray;
    }

}
