package com.dyy.yonxin.library2.cacheforandroid.util;

import com.google.gson.Gson;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class GsonUtil {
    private static Gson gson = new Gson();
    public static <T>T getObjectByJson(String json, T t){
        return (T) gson.fromJson(json,t.getClass());
    }

    public static  <T> String getJsonByObject(T t) {
        return gson.toJson(t);
    }
}
