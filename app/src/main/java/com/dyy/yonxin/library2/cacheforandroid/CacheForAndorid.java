package com.dyy.yonxin.library2.cacheforandroid;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v4.content.PermissionChecker;

import java.io.File;

/**
 * Created by 段钰莹 on 2017/11/1.
 */

public class CacheForAndorid extends Application {
    private static CacheForAndorid mCacheForAndorid;
    @Override
    public void onCreate() {
        super.onCreate();
        mCacheForAndorid = this;
    }

    public static Context getSuperContext(){
        return mCacheForAndorid;
    }

    public static CacheForAndorid getSuperApp(){
        return mCacheForAndorid;
    }

    public static Resources getRes(){
        return getSuperContext().getResources();
    }

    public File getStorageDirectory() {
        File dataCacheDir = null;
        String externalStorageState;
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException e) {
            externalStorageState = "";
        }
        if (MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(this)) {
            dataCacheDir = getExternalDataDir(this);
        }else{
            dataCacheDir = new File("");
        }

        return dataCacheDir;
    }

    private File getExternalDataDir(Context context) {
        File appDir = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
        if (!appDir.exists()) {
            if (!appDir.mkdirs()) {
                return null;
            }
        }
        return appDir;
    }

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String MEDIA_MOUNTED = "mounted";

    private static boolean hasExternalStoragePermission(Context context) {
        int perm = PermissionChecker.checkPermission(context,EXTERNAL_STORAGE_PERMISSION,android.os.Process.myPid(),android.os.Process.myUid(),context.getPackageName());
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    public static final String ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    public static final String ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";

}
