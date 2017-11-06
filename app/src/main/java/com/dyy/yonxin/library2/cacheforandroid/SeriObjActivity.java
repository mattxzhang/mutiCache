package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.view.View;

import com.dyy.yonxin.library2.cacheforandroid.bean.User;
import com.dyy.yonxin.library2.cacheforandroid.util.SerialUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.ToastUtil;

import java.io.File;

public class SeriObjActivity extends SimpleCacheActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seri_obj);
    }

    @Override
    protected String getInstructionName() {
        return "serialObjectInstruction.txt";
    }

    public void clickSerialzable(View view){
        User user = getSaveUser();
        File saveFile = getSaveFile();
        SerialUtil.saveObjectInFile(user,saveFile);
    }

    public void clickDeserializable(View view){
        File saveFile = getSaveFile();
        User user = SerialUtil.restoreObjectByFile(saveFile,new User());
        showUserContent(user);
    }

    private void showUserContent(User user) {
        ToastUtil.shorts("id="+user.getUserId());
        ToastUtil.shorts("name="+user.getName());
        ToastUtil.shorts("password="+user.getPassword());
    }

    private File getSaveFile() {
        File storageFile = CacheForAndorid.getSuperApp().getStorageDirectory();
        if(storageFile.getPath().isEmpty())
            return storageFile;
        File serialFile = new File(storageFile,"user.config");
        return serialFile;
    }

    private User getSaveUser() {
        User user = new User();
        user.setUserId(1);
        user.setName("咸蛋超人");
        user.setPassword("dbdqxmn");
        return user;
    }


}
