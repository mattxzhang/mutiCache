package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.DBExchangeUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class DBCache<T> implements ICacheWay<T> {
    private ICacheWay<T> nextCache;
    private long overDueTime = 20 * 1000;


    @Override
    public void setNextCache(ICacheWay<T> nextCache) {
        this.nextCache = nextCache;
    }

    @Override
    public void setCacheSaveTime(long overDueTime) {
        this.overDueTime = overDueTime;
    }

    @Override
    public T getCache(T t) {
        T cacheObj = getObjectAfterClear(t);
        if (cacheObj != null) {
            CacheUtil.cacheMsg = "从DBCache拿到数据\n";
            return restoreNormalData(cacheObj);
        } else if (nextCache != null) {
            cacheObj = nextCache.getCache(t);
            if (cacheObj != null)
                saveObjInCache(cacheObj);
        }
        return cacheObj;
    }

    private T getObjectAfterClear(T t) {
        T cacheObj = getObjectInDB(t);
        if (cacheObj != null) {
            clearCacheOverDueTime(cacheObj);
            cacheObj = getObjectInDB(t);
        }
        return cacheObj;
    }

    private <T> T restoreNormalData(T t) {
        if (t instanceof CacheUser)
            return (T) DBExchangeUtil.getCacheUserDBRestore((CacheUser) t);
        return t;
    }

    private <T> T getObjectInDB(T t) {
        if (t instanceof CacheUser)
            return (T) getCacheUserInDB();
        return null;
    }

    public CacheUser getCacheUserInDB() {
        Query<CacheUser> query = DBManager.getCacheUserDao().queryBuilder().build();
        List<CacheUser> dbUsers = query.list();

        if (dbUsers != null && dbUsers.size() > 0)
            return dbUsers.get(0);

        return null;
    }

    @Override
    public void clearCacheOverDueTime(T t) {
        if (t instanceof CacheUser) {
            if (TimeUtils.getTimeStampDif(getTimeStampByType(t)) > overDueTime) {
                CacheUser mCacheUser = (CacheUser) t;
                DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME + " where USER_ID=" + mCacheUser.getUserId());
            }
        }

    }

    private long getTimeStampByType(T t) {
        if (t instanceof CacheUser)
            return ((CacheUser) t).getSaveInDBTime();
        return 0;
    }

    @Override
    public void saveObjInCache(T t) {
        CacheUser mCacheUser = (CacheUser) t;
        if (mCacheUser == null)
            return;
        mCacheUser = DBExchangeUtil.getCacheUserReadyDB(mCacheUser);
        CacheUser nowCacheUser = getCacheUserInDB();
        if (nowCacheUser == null)
            DBManager.getCacheUserDao().insert(mCacheUser);
        else {
            DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME + " where USER_ID=" + mCacheUser.getUserId());
            DBManager.getCacheUserDao().insert(mCacheUser);
        }

    }

    @Override
    public void clearCache(T t) {
        DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME);
    }
}
