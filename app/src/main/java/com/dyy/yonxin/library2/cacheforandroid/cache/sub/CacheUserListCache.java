package com.dyy.yonxin.library2.cacheforandroid.cache.sub;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.cache.ListCache;

/**
 * Created by 段钰莹 on 2017/11/7.
 */

public class CacheUserListCache extends ListCache<CacheUser> {
    static final String CACHE_USER = "cacheUser";
    @Override
    public String getCacheName(CacheUser cacheUser) {
        return CACHE_USER+cacheUser.getUserId();
    }
}
