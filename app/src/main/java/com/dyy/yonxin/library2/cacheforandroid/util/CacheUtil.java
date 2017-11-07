package com.dyy.yonxin.library2.cacheforandroid.util;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.cache.DBCache;
import com.dyy.yonxin.library2.cacheforandroid.cache.ListCache;
import com.dyy.yonxin.library2.cacheforandroid.cache.ShareCache;
import com.dyy.yonxin.library2.cacheforandroid.cache.sub.CacheUserDBCache;
import com.dyy.yonxin.library2.cacheforandroid.cache.sub.CacheUserListCache;
import com.dyy.yonxin.library2.cacheforandroid.cache.sub.CacheUserShareCache;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class CacheUtil {
    private ListCache<CacheUser> cacheUserCacheList;
    private ShareCache<CacheUser> cacheUserCacheShare;
    private DBCache<CacheUser> cacheUserCacheDB;
    public static String cacheMsg = "";//仅用于测试，实际应用请删除
    //回收标志位
    private boolean connectedCache = false;

    private static CacheUtil mCacheUtil;
    private CacheUtil(){}
    private static CacheUtil getInstance(){
        if(mCacheUtil == null)
            mCacheUtil = new CacheUtil();
        return mCacheUtil;
    }
    public static CacheUser getCacheUser() {
        //绝招哦，对象唯一，getInstance内部化了
        // 不用外部调用时再加getInstance,所有public静态类外部调用，其他内部调用
        if(!getInstance().connectedCache){
            getInstance().connectCache();
            getInstance().connectedCache = true;
        }
        return getInstance().getCacheUserListCache().getCache(new CacheUser());
    }

    public static CacheUser getCacheUser(CacheUser mCacheUser) {
        //绝招哦，对象唯一，getInstance内部化了
        // 不用外部调用时再加getInstance,所有public静态类外部调用，其他内部调用
        if(!getInstance().connectedCache){
            getInstance().connectCache();
            getInstance().connectedCache = true;
        }
        return getInstance().getCacheUserListCache().getCache(mCacheUser);
    }

    public static void setCacheUser(CacheUser mCacheUser){
        getInstance().getCacheUserDBCache().saveObjInCache(mCacheUser);
        getInstance().getCacheUserShareCache().saveObjInCache(mCacheUser);
        getInstance().getCacheUserListCache().saveObjInCache(mCacheUser);
    }

    public static void clearAllCacheUser(CacheUser mCacheUser){
        getInstance().getCacheUserDBCache().clearCache(mCacheUser);
        getInstance().getCacheUserShareCache().clearCache(mCacheUser);
        getInstance().getCacheUserListCache().clearCache(mCacheUser);
    }

    public static void clearCacheUserShare(CacheUser mCacheUser){
        getInstance().getCacheUserShareCache().clearCache(mCacheUser);
        getInstance().getCacheUserListCache().clearCache(mCacheUser);
    }

    public static void clearCacheUserList(CacheUser mCacheUser){
        getInstance().getCacheUserListCache().clearCache(mCacheUser);
    }

    private void connectCache() {
        //是否采用超出定时清空数据，默认采用
        //Whether to use empty data beyond the time limit, the default adoption
        getCacheUserDBCache().setCleanOverDueTime(false);

        //采用定时清空时，可设置定时清空时间
        //When the timer is empty, the time of clearing empty can be set
//        getCacheUserDBCache().setCacheSaveTime(5*1000);

        //设置是否唯一数据（Set whether unique data）
        // 【一个类单对象无需另外保存唯一量如id等数据，一个类多对象保存需要根据唯一量获取存储数据，唯一量需单独保存】
        //【A single class object does not need to save unique data, such as ID, etc. a class of multi object storage needs to obtain the storage data according to the unique quantity, and the unique amount needs to be saved individually】
//        getCacheUserDBCache().setObjSingle(true);

        //链式结构是为了更加灵活，随意可拆，如果需要保密，只能存数据库,就直接用DBCache就行了，不用链接。
        //Chain structure is to more flexible, removable,
        // if you need to keep confidential,
        // you can only store the database,
        // you can use DBCache directly, without links.
        getCacheUserListCache().setNextCache(getCacheUserShareCache());
        getCacheUserShareCache().setNextCache(getCacheUserDBCache());
    }

    private ListCache<CacheUser> getCacheUserListCache(){
        if(cacheUserCacheList == null)
            cacheUserCacheList = new CacheUserListCache();
        return cacheUserCacheList;
    }

    private ShareCache<CacheUser> getCacheUserShareCache(){
        if(cacheUserCacheShare == null)
            cacheUserCacheShare = new CacheUserShareCache();
        return cacheUserCacheShare;
    }

    private DBCache<CacheUser> getCacheUserDBCache(){
        if(cacheUserCacheDB == null)
            cacheUserCacheDB = new CacheUserDBCache();
        return cacheUserCacheDB;
    }
}
