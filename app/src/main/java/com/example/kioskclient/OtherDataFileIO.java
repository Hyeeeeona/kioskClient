package com.example.kioskclient;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OtherDataFileIO {

    public static void saveOrderIdLast(Context context, int order_id) {
        String filename = "order_id";
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(order_id);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int readOrderIdLast(Context context) {
        int order_id;
        String filename = "order_id";
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(filename);

        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String strBuf = bufferedReader.readLine();
        order_id = Integer.parseInt(strBuf);

        //TODO: 작업이후 추가
        } catch (Exception e) {
            e.printStackTrace();
            OtherDataFileIO.saveOrderIdLast(context, 1);
            order_id = 1;
        }
        return order_id;
    }

}
