package com.dyy.yonxin.library2.cacheforandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;

import java.util.Set;

public class ShareUtils {
    private String shareName = "ImitateNBADefault";
    private int shareMode = Context.MODE_PRIVATE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static ShareUtils shareUtils;

    private ShareUtils(){}
    private static ShareUtils getInstance(){
        if(shareUtils == null)
            shareUtils = new ShareUtils();
        return shareUtils;
    }

    private SharedPreferences getShare(){
        if(sharedPreferences == null){
            sharedPreferences = CacheForAndorid.getSuperContext().getSharedPreferences(shareName,shareMode);
        }
        return sharedPreferences;
    }

    public static ShareUtils resetShare(){
        ShareUtils.getInstance().commit();
        ShareUtils.getInstance().shareName = "ImitateNBADefault";
        ShareUtils.getInstance().shareMode = Context.MODE_PRIVATE;
        return ShareUtils.getInstance();
    }

    /**
     * ShareUtils在同一个类中要调用不同share,进行重置
     * @param shareName
     * @return
     */
    public static ShareUtils resetShare(String shareName){
        ShareUtils.getInstance().commit();
        ShareUtils.getInstance().shareName = shareName;
        return ShareUtils.getInstance();
    }

    /**
     * ShareUtils在同一个类中要调用不同share,进行重置
     * @param shareName
     * @return
     */
    public static ShareUtils resetShare(String shareName, int shareMode){
        ShareUtils.getInstance().commit();
        ShareUtils.getInstance().shareName = shareName;
        ShareUtils.getInstance().shareMode = shareMode;
        return ShareUtils.getInstance();
    }

    private SharedPreferences.Editor getEditor(){
        if(editor == null){
            editor  = getShare().edit();
        }
        return editor;
    }

    public ShareUtils set(String name, int intValue){
        getEditor().putInt(name,intValue);
        return this;
    }

    public ShareUtils set(String name, long longValue){
        getEditor().putLong(name,longValue);
        return this;
    }

    public ShareUtils set(String name, boolean boolValue){
        getEditor().putBoolean(name,boolValue);
        return this;
    }

    public ShareUtils set(String name, float floatValue){
        getEditor().putFloat(name,floatValue);
        return this;
    }

    public ShareUtils set(String name, String stringValue){
        getEditor().putString(name,stringValue);
        return this;
    }

    public ShareUtils set(String name, Set<String> stringSet){
        getEditor().putStringSet(name,stringSet);
        return this;
    }

    public void commit(){
        if(editor!=null){
            editor.apply();
        }
    }

    public void clear(){
        getEditor().clear();
        commit();
    }

    public void remove(String name){
        getEditor().remove(name).commit();
    }

    public int getInt(String name){
        return getShare().getInt(name,0);
    }

    public int getInt(String name, int defaultValue){
        return getShare().getInt(name,defaultValue);
    }

    public long getLong(String name){
        return getShare().getLong(name,0L);
    }

    public long getLong(String name, long defaultValue){
        return getShare().getLong(name,defaultValue);
    }

    public boolean getBoolean(String name){
        return getShare().getBoolean(name,false);
    }

    public boolean getBoolean(String name, boolean defaultValue){
        return getShare().getBoolean(name,defaultValue);
    }

    public float getFloat(String name){
        return getShare().getFloat(name,0f);
    }

    public float getFloat(String name, float defaultValue){
        return getShare().getFloat(name,defaultValue);
    }

    public String getString(String name){
        return getShare().getString(name,"");
    }

    public String getString(String name, String defaultValue){
        return getShare().getString(name,defaultValue);
    }

    public Set<String> getSet(String name){
        return getShare().getStringSet(name,null);
    }

    public Set<String> getSet(String name, Set<String> defaultValue){
        return getShare().getStringSet(name,defaultValue);
    }
}
