package com.dyy.yonxin.library2.cacheforandroid.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class SerialUtil {
    private static SerialUtil mSerialUtil;
    private SerialUtil(){}

    private static SerialUtil getInstance(){
        if(mSerialUtil == null){
            mSerialUtil = new SerialUtil();
        }
        return mSerialUtil;
    }
    public static<T> void saveObjectInFile(T t, File saveFile){
        //Student对象序列化过程
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(saveFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(t);
            oos.flush();
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(oos!=null)
                SerialUtil.getInstance().closeObjectOutputStream(oos);
            if(fos!=null)
                SerialUtil.getInstance().closeFileOutputStream(fos);
        }
    }

    public static<T> T restoreObjectByFile(File saveFile,T t){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(saveFile);
            ois = new ObjectInputStream(fis);
            return (T) ois.readObject();
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(ois!=null)
                SerialUtil.getInstance().closeObjectInputStream(ois);
            if(fis!=null)
                SerialUtil.getInstance().closeFileInputStream(fis);
        }

        return null;

    }

    private void closeFileOutputStream(FileOutputStream fos) {
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeObjectOutputStream(ObjectOutputStream oos) {
        try {
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeFileInputStream(FileInputStream fis) {
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeObjectInputStream(ObjectInputStream ois) {
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
