package com.dyy.yonxin.library2.cacheforandroid.cache.sub;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.dyy.yonxin.library2.cacheforandroid.cache.DBCache;
import com.dyy.yonxin.library2.cacheforandroid.db.CacheUserDao;
import com.dyy.yonxin.library2.cacheforandroid.manager.DBManager;
import com.dyy.yonxin.library2.cacheforandroid.util.DBExchangeUtil;

import org.greenrobot.greendao.query.Query;

import java.util.List;


public class CacheUserDBCache extends DBCache<CacheUser> {
    public void saveObjByType(CacheUser mCacheUser) {
        saveCacheUser(mCacheUser);
    }

    public CacheUser restoreNormalData(CacheUser t) {
        return DBExchangeUtil.getCacheUserDBRestore(t);
    }

    public CacheUser getObjectInDB(CacheUser mCacheUser) {
        return getCacheUserInDB(mCacheUser.getUserId());
    }

    private void saveCacheUser(CacheUser mCacheUser) {
        if (mCacheUser == null)
            return;
        mCacheUser = DBExchangeUtil.getCacheUserReadyDB(mCacheUser);
        CacheUser nowCacheUser = getCacheUserInDB(mCacheUser.getUserId());
        if (nowCacheUser == null)
            DBManager.getCacheUserDao().insert(mCacheUser);
        else {
            DBManager.getSession().getDatabase().execSQL("delete from " + CacheUserDao.TABLENAME + " where USER_ID=" + mCacheUser.getUserId());
            DBManager.getCacheUserDao().insert(mCacheUser);
        }
    }

    private CacheUser getCacheUserInDB(int userId) {
        Query<CacheUser> query = null;
        if(singleObj){
            query = DBManager.getCacheUserDao().queryBuilder().build();
        }else{
            query = DBManager.getCacheUserDao().queryBuilder().where(CacheUserDao.Properties.UserId.eq(userId)).build();
        }


        List<CacheUser> dbUsers = query.list();

        if (dbUsers != null && dbUsers.size() > 0)
            return dbUsers.get(0);

        return null;
    }
}
