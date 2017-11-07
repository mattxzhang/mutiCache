package com.dyy.yonxin.library2.cacheforandroid.cache.sub;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.cache.ShareCache;

/**
 * Created by 段钰莹 on 2017/11/7.
 */

public class CacheUserShareCache extends ShareCache<CacheUser> {
    static final String CACHE_USER = "cacheUser";
    @Override
    public  String getCacheName(CacheUser mCacheUser){
        if(singleObj)
            return CACHE_USER;
        return CACHE_USER+mCacheUser.getUserId();
    }
}
