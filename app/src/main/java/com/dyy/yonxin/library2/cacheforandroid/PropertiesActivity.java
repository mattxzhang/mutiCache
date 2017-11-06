package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.view.View;

import com.dyy.yonxin.library2.cacheforandroid.config.PermissionConfig;
import com.dyy.yonxin.library2.cacheforandroid.uilistener.PropertyConfigListener;
import com.dyy.yonxin.library2.cacheforandroid.util.PropertiesUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ToastUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;

public class PropertiesActivity extends SimpleCacheActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
    }

    @Override
    protected String getInstructionName() {
        return "propertiesInstruction.txt";
    }

    public void clickSetProperties(View view){
        File saveFile = getSaveFile();
        if(saveFile.getPath().isEmpty()){
            askSdcardPermission();
            return;
        }

        PropertiesUtil.getInstance().resetProperties()
                .put("version",1)
                .put("dbVersion",2)
                .put("versionName",1.0)
                .put("isLatestVersion",true)
                .put("appName","CacheForAndroid")
                .save(saveFile);
    }

    public void clickGetProperties(View view){
        File saveFile = getSaveFile();
        if(getSaveFile().getPath().isEmpty()){
            askSdcardPermission();
            return;
        }

        PropertiesUtil.getInstance().resetProperties().loadProperties(saveFile);
        showProperties();
    }

    private void askSdcardPermission() {
        PropertyConfigListener propertyListeners = PropertyConfigListener.getInstance(this);

        AndPermission.with(this)
                .requestCode(PermissionConfig.STORAGE)
                .permission(Permission.STORAGE)
                .callback(propertyListeners.getPermissionListener())
                .rationale(propertyListeners.getRationaleListener())
                .start();
    }

    private File getSaveFile() {
        File storageFile = CacheForAndorid.getSuperApp().getStorageDirectory();
        if(storageFile.getPath().equals(""))
            return storageFile;
        File propertyFile = new File(storageFile,"temp.properties");
        return propertyFile;
    }



    private void showProperties() {
        PropertiesUtil mPropertiesUtil = PropertiesUtil.getInstance();
        int version = mPropertiesUtil.getInt("version");
        int dbVersion = mPropertiesUtil.getInt("dbVersion");
        double versionName = mPropertiesUtil.getDouble("versionName");
        boolean isLatestVersion = mPropertiesUtil.getBoolean("isLatestVersion");

        ToastUtil.shorts("version="+version);
        ToastUtil.shorts("dbVersion="+dbVersion);
        ToastUtil.shorts("versionName="+versionName);
        ToastUtil.shorts("isLatestVersion="+isLatestVersion);

    }

}
