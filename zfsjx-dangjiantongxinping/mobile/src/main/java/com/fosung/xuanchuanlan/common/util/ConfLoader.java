package com.fosung.xuanchuanlan.common.util;

import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 {
    "userId":"sn1",
    "pwd":"123456",
    "log":0
 }
 */
public class ConfLoader {

    private static final String TAG = "ConfLoader";

    private static final String CONF_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/conf.txt";

    public ConfLoader(){}

    public ConfInfo load(){
        ConfInfo info = new ConfInfo();

        File file = new File(CONF_FILE_PATH);

        Log.i(TAG, "conf file path:"+CONF_FILE_PATH);

        if(!file.exists()){
            Log.w(TAG, "conf.txt is not exist");
            return null;
        }

        StringBuilder jsonStrBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String str = null;
            while((str = bufferedReader.readLine()) != null){
                jsonStrBuilder.append(str);
            }

            String json = jsonStrBuilder.toString();
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.has("userId")) {
                info.userId = jsonObject.getString("userId");
            }

            if (jsonObject.has("pwd")) {
                info.pwd = jsonObject.getString("pwd");
            }
            //读取log级别
            if(jsonObject.has("log")) {
                info.logLevel = jsonObject.getInt("log");
            }else {
                //默认级别为0 不打印
                info.logLevel = 0;
            }
            //读取log级别
            if(jsonObject.has("sdklog")) {
                info.sdklog = jsonObject.optBoolean("sdklog",false);
            }else {
                //默认级别为0 不打印
                info.logLevel = 0;
            }
            Log.i(TAG, "conf json "+json+" userId "+info.userId+" info.pwd "+info.pwd);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return info;
    }

    public static class ConfInfo{
        public String userId;
        public String pwd;

        /**
         * NONE(0),
         * ERROR(1),
         * WARNING(2),
         * INFO(3),
         * DEBUG(4),
         * VERBOSE(5)
         */
        public int logLevel;   //
        public boolean  sdklog;   //

        @Override
        public String toString() {
            return "ConfStatusInfo{" +
                    "userId='" + userId + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", logLevel=" + logLevel +
                    ", sdklog=" + sdklog +
                    '}';
        }
    }
}
