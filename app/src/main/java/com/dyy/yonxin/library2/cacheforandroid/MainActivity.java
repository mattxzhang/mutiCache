package com.dyy.yonxin.library2.cacheforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dyy.yonxin.library2.cacheforandroid.config.PermissionConfig;
import com.dyy.yonxin.library2.cacheforandroid.uilistener.SeriObjConfigListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;

import static com.dyy.yonxin.library2.cacheforandroid.uilistener.SeriObjConfigListener.REQUEST_CODE_SETTING;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //一般没sdcard权限，这应用也用不了
        checkSdcardPermission();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            checkSdcardPermission();
        }
    }

    private void checkSdcardPermission() {
        File storageFile = CacheForAndorid.getSuperApp().getStorageDirectory();
        if(storageFile.getPath().isEmpty()){
            askSdcardPermission();
        }
    }

    private void askSdcardPermission() {
        SeriObjConfigListener mSeriObjConfigListener = SeriObjConfigListener.getInstance(this);

        AndPermission.with(this)
                .requestCode(PermissionConfig.STORAGE)
                .permission(Permission.STORAGE)
                .callback(mSeriObjConfigListener.getPermissionListener())
                .rationale(mSeriObjConfigListener.getRationaleListener())
                .start();
    }

    public void clickShare(View view){
        Intent intent = new Intent(this,ShareActivity.class);
        startActivity(intent);
    }

    public void clickProperties(View view){
        Intent intent = new Intent(this,PropertiesActivity.class);
        startActivity(intent);
    }

    public void clickFile(View view){
        Intent intent = new Intent(this,SeriObjActivity.class);
        startActivity(intent);
    }

    public void clickDB(View view){
        Intent intent = new Intent(this,DBStateActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SETTING){
            askSdcardPermission();
            return;
        }
    }


    public void clickAllWay(View view) {
        Intent intent = new Intent(this,CompositeCacheActivity.class);
        startActivity(intent);
    }
}
