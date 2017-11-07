package com.dyy.yonxin.library2.cacheforandroid.cache;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.CacheUtil;
import com.dyy.yonxin.library2.cacheforandroid.util.TimeUtils;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/6.
 * 增加新的分类时，需要增加前面三个的分支，或者使用新的类继承它，重写这些方法
 */

public abstract class DBCache<T> extends Caches<T>  {
    abstract void saveObjByType(T t);
    abstract <T> T restoreNormalData(T t);
    abstract <T> T getObjectInDB(T t);

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



    public CacheUser getCacheUserInDB() {
        Query<CacheUser> query = DBManager.getCacheUserDao().queryBuilder().build();
        List<CacheUser> dbUsers = query.list();

        if (dbUsers != null && dbUsers.size() > 0)
            return dbUsers.get(0);

        return null;
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
