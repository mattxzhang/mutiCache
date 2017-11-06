package com.dyy.yonxin.library2.cacheforandroid.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class PropertiesUtil {
    private static PropertiesUtil mPropertiesUtil;
    private PropertiesUtil(){}
    public static PropertiesUtil getInstance(){
        if(mPropertiesUtil == null)
            mPropertiesUtil = new PropertiesUtil();
        return mPropertiesUtil;
    }

    private Properties properties = new Properties();

    public PropertiesUtil resetProperties(){
        properties.clear();
        return this;
    }

    public PropertiesUtil put(String key,String value){
        properties.put(key,value);
        return this;
    }

    public PropertiesUtil put(String key,int value){
        properties.put(key,""+value);
        return this;
    }

    public PropertiesUtil put(String key,double value){
        properties.put(key,""+value);
        return this;
    }

    public PropertiesUtil put(String key,boolean value){
        properties.put(key,""+value);
        return this;
    }

    public void save(File saveFile){
        FileOutputStream savePropertyStream = null;
        try {
            savePropertyStream = new FileOutputStream(saveFile,false);
            properties.store(savePropertyStream,"");
            ToastUtil.shorts("已保存");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(savePropertyStream!=null){
                closeSavePropertyStream(savePropertyStream);
            }
        }
    }

    private void closeSavePropertyStream(FileOutputStream savePropertyStream) {
        try {
            savePropertyStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProperties(File saveFile){
        FileInputStream in = null;
        try {
            in = new FileInputStream(saveFile);
            properties.load(in);
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null)
                closeInputStream(in);
        }
    }

    private void closeInputStream(FileInputStream in) {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String key){
        String configData = (String) properties.get(key);

        if(configData != null){
            if(RegularExpressionUtil.isNumeric(configData))
                return Integer.parseInt(configData);
        }
        return 0;
    }

    public double getDouble(String key){
        String configData = (String) properties.get(key);

        if(configData != null){
            if(RegularExpressionUtil.isNumeric(configData))
                return Double.parseDouble(configData);
        }
        return 0;
    }

    public boolean getBoolean(String key){
        String configData = (String) properties.get(key);

        if(configData != null){
            if(configData.equals("true"))
                return true;
        }
        return false;
    }
}
