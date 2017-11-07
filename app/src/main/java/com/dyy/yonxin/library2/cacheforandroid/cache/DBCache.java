package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;
import com.dyy.yonxin.library2.cacheforandroid.R;
import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

/**
 * Created by 段钰莹 on 2017/11/6.
 * 增加新的对象缓存时，需要使用新的类继承DBCache，重写这些方法
 * When adding new object caching, you need to use new classes to inherit DBCache, and rewrite these methods
 * @see com.dyy.yonxin.library2.cacheforandroid.cache.sub.CacheUserDBCache
 *
 */

public abstract class DBCache<T> extends Caches<T>  {
    public abstract void saveObjByType(T t);
    public abstract  T restoreNormalData(T t);
    public abstract  T getObjectInDB(T t);

    @Override
    public T getCache(T t) {
        T cacheObj = getObjectAfterClear(t);
        if (cacheObj != null) {
            CacheUtil.cacheMsg = CacheForAndorid.getRes().getString(R.string.get_data_from_dbcache);
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

    @Override
    public void clearCacheOverDueTime(T t) {
        if(!needClean)
            return;
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
        saveObjByType(t);

    }

    @Override
    public void clearCache(T t) {
        DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME);
    }
}
