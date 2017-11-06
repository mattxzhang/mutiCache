package com.dyy.yonxin.library2.cacheforandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;

import java.util.ArrayList;
import java.util.List;

public class CompositeCacheActivity extends AppCompatActivity {
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_cache);
        txtResult = (TextView) findViewById(R.id.txt_result);
    }

    public void clickShowGetCacheResult(View view) {
        CacheUser cacheUser = CacheUtil.getCacheUser();
        if (cacheUser == null)
            txtResult.append(CacheUtil.cacheMsg);
        else
            txtResult.append(CacheUtil.cacheMsg + cacheUser.getAllString());
    }


    public void clickSaveDataInCache(View view) {
        CacheUtil.setCacheUser(getMyCacheUser());
        txtResult.append("保存到缓存\n");
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
        mCacheFriends.add("火星人");
        mCacheFriends.add("丧尸");
        mCacheFriends.add("哆啦A梦");
        return mCacheFriends;
    }

    public void clickClearAllCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        CacheUtil.clearAllCacheUser(mCacheUser);
        txtResult.append("清空缓存\n");
    }

    public void clickClearShareAndListCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        CacheUtil.clearCacheUserShare(mCacheUser);
        txtResult.append("清除Share和List缓存\n");
    }

    public void clickClearListCache(View view) {
        CacheUser mCacheUser = new CacheUser();
        CacheUtil.clearCacheUserList(mCacheUser);
        txtResult.append("清除List缓存\n");
    }
}
