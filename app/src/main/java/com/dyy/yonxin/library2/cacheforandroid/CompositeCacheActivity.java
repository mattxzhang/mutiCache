package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.PropertiesUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.dyy.yonxin.library2.cacheforandroid.util.PropertiesUtil.resetProperties;

public class CompositeCacheActivity extends AppCompatActivity {
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_cache);
        txtResult = (TextView) findViewById(R.id.txt_result);
    }

    public void clickShowGetCacheResult(View view) {
        CacheUser mCacheUser = new CacheUser();
        mCacheUser.setUserId(resetProperties().loadProperties(getSaveFile()).getInt("cacheUserId"));
        //上方子类配置需要多个相同类的不同对象时，设置好该识别变量，取出此类型
        //if you need to save different object with the same object,you must save a recognized varriable and save
        //it in different place.
        CacheUser cacheUser = CacheUtil.getCacheUser(mCacheUser);
        if (cacheUser == null)
            txtResult.append(CacheUtil.cacheMsg);
        else
            txtResult.append(CacheUtil.cacheMsg + cacheUser.getAllString());
    }


    public void clickSaveDataInCache(View view) {
        CacheUser mCacheUser = getMyCacheUser();
        //如果需要保存多条数据，就用id来区分他们，否则请使用setObjSingle来标识他们
        //If you need to save a lot of datas,use ID to distinguish them.
        // Otherwise,you must use setObjSingle(true) to flag them.
        resetProperties()
                .put("cacheUserId",mCacheUser.getUserId())
                .save(getSaveFile());
        CacheUtil.setCacheUser(getMyCacheUser());
        txtResult.append(CacheForAndorid.getRes().getString(R.string.hint_save_in_cache));
    }

    private File getSaveFile() {
        File storageFile = CacheForAndorid.getSuperApp().getStorageDirectory();
        if(storageFile.getPath().equals(""))
            return storageFile;
        File propertyFile = new File(storageFile,"cacheIds.properties");
        return propertyFile;
    }

    private CacheUser getMyCacheUser() {
        CacheUser mCacheUser = new CacheUser();
        mCacheUser.setUserId(1);
        mCacheUser.setUserName("DYY");
        mCacheUser.setFriends(getCacheFriends());
        return mCacheUser;
    }

    public List<String> getCacheFriends() {
        List<String> mCacheFriends = new ArrayList<>();
        mCacheFriends.add("a");
        mCacheFriends.add("b");
        mCacheFriends.add("c");
        return mCacheFriends;
    }

    public void clickClearAllCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        int cacheId = PropertiesUtil.resetProperties().loadProperties(getSaveFile()).getInt("cacheUserId");
        mCacheUser.setUserId(cacheId);
        CacheUtil.clearAllCacheUser(mCacheUser);
        txtResult.append(CacheForAndorid.getRes().getString(R.string.btn_clear_all)+"\n");
    }

    public void clickClearShareAndListCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        int cacheId = PropertiesUtil.resetProperties().loadProperties(getSaveFile()).getInt("cacheUserId");
        mCacheUser.setUserId(cacheId);
        CacheUtil.clearCacheUserShare(mCacheUser);
        txtResult.append(CacheForAndorid.getRes().getString(R.string.btn_clear_list_share)+"\n");
    }

    public void clickClearListCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        int cacheId = PropertiesUtil.resetProperties().loadProperties(getSaveFile()).getInt("cacheUserId");
        mCacheUser.setUserId(cacheId);
        CacheUtil.clearCacheUserList(mCacheUser);
        txtResult.append(CacheForAndorid.getRes().getString(R.string.btn_clear_list)+"\n");
    }
}
