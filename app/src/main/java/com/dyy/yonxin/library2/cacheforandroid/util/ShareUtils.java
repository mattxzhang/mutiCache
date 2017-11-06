package com.dyy.yonxin.library2.cacheforandroid.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;

import java.util.Set;

/**
 * Created by 段钰莹 on 2017/9/2.<br/>
 * Share集成类<br/>
 * 用法：<br/>
 * 1.初始化<br/>
 * ShareUtils share = ShareUtils.getInstance();<br/>
 * 2.设置存储名字，模式<br/>
 * resetShare();或resetShare(name);或resetShare(name,mode);<br/>
 * 3.存储数据<br/>
 * share.set("xx",xx).set("xx",xx).commit();<br/>
 * 4.取出数据<br/>
 * int a = share.getInt(name);或int a = share.getInt(share,default)<br/>
 */

public class ShareUtils {
    private String shareName = "ImitateNBADefault";
    private int shareMode = Context.MODE_PRIVATE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static ShareUtils shareUtils;

    private ShareUtils(){}
    public static ShareUtils getInstance(){
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

    public ShareUtils resetShare(){
        commit();
        this.shareName = "ImitateNBADefault";
        this.shareMode = Context.MODE_PRIVATE;
        return this;
    }

    /**
     * ShareUtils在同一个类中要调用不同share,进行重置
     * @param shareName
     * @return
     */
    public ShareUtils resetShare(String shareName){
        commit();
        this.shareName = shareName;
        return this;
    }

    /**
     * ShareUtils在同一个类中要调用不同share,进行重置
     * @param shareName
     * @return
     */
    public ShareUtils resetShare(String shareName, int shareMode){
        commit();
        this.shareName = shareName;
        this.shareMode = shareMode;
        return this;
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
