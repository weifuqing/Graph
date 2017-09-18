package com.example.statistics.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.example.statistics.StatConfig;
import com.example.statistics.bean.Event;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by weifuqing on 2017/8/10 0010.
 */

public class LogUtil {

    private static final String fileName_1 = "log1.txt";
    private static final String fileName_2 = "log2.txt";
    private static final String fileNameKey = "log_fileName";
    private static String current_file;

    public static void saveLog(Event event) {
        current_file = SpUtil.getString(StatConfig.getContext(),fileNameKey);
        if(TextUtils.isEmpty(current_file)){
            current_file = fileName_1;
            SpUtil.putString(StatConfig.getContext(),fileNameKey,fileName_1);
        }
        Gson gson = new Gson();
        String log = gson.toJson(event);
        File file = new File(StatConfig.getContext().getExternalCacheDir()+StatConfig.FILE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File(file,current_file);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file,true);
            writer.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void saveLog(String log){
        current_file = SpUtil.getString(StatConfig.getContext(),fileNameKey);
        if(TextUtils.isEmpty(current_file)){
            current_file = fileName_1;
            SpUtil.putString(StatConfig.getContext(),fileNameKey,fileName_1);
        }
        File file = new File(StatConfig.getContext().getExternalCacheDir()+StatConfig.FILE_PATH);
        if(!file.exists()){
            file.mkdirs();
        }
        file = new File(file,current_file);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file,true);
            writer.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String getLog(){
        current_file = SpUtil.getString(StatConfig.getContext(),fileNameKey);
        if(TextUtils.isEmpty(current_file)){
            current_file = fileName_1;
            SpUtil.putString(StatConfig.getContext(),fileNameKey,fileName_1);
        }
        StringBuffer log = new StringBuffer();
        File file = new File(StatConfig.getContext().getExternalCacheDir()+StatConfig.FILE_PATH,current_file);
        if(fileName_1.equals(current_file)){
            SpUtil.putString(StatConfig.getContext(),fileNameKey,fileName_2);
        }else {
            SpUtil.putString(StatConfig.getContext(),fileNameKey,fileName_1);
        }
        if(!file.exists()){
            return "";
        }
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            BufferedReader bf = new BufferedReader(reader);
            String s;
            while ((s = bf.readLine())!=null){
                log.append(s);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null){
                    reader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        cleanLog(file);
        return log.toString();
    }

    private static void cleanLog(File file){
        file.delete();
    }

    public static void cleanLog(){
        File file = new File(StatConfig.getContext().getExternalCacheDir()+StatConfig.FILE_PATH,current_file.equals(fileName_1)?fileName_2:fileName_1);
        file.delete();
    }
}
