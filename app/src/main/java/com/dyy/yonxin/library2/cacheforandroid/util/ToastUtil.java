package com.dyy.yonxin.library2.cacheforandroid.util;

import android.widget.Toast;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;

/**
 * Created by 段钰莹 on 2017/11/1.
 */

public class ToastUtil {
    public static void shorts(String message){
        Toast.makeText(CacheForAndorid.getSuperContext(),message,Toast.LENGTH_SHORT).show();
    }

    public static void longs(String message){
        Toast.makeText(CacheForAndorid.getSuperContext(),message,Toast.LENGTH_LONG).show();
    }
}
