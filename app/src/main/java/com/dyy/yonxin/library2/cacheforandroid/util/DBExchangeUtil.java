package com.dyy.yonxin.library2.cacheforandroid.util;

import com.dyy.yonxin.library2.cacheforandroid.bean.CacheUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by 段钰莹 on 2017/11/6.
 */

public class DBExchangeUtil {
    public static CacheUser getCacheUserReadyDB(CacheUser cacheUser){
        Gson gson = new Gson();
        String jsonFriends = gson.toJson(cacheUser.getFriends());
        cacheUser.setJsonFriends(jsonFriends);
        cacheUser.setSaveInDBTime(TimeUtils.getNowTimeStamp());
        return cacheUser;
    }

    public static CacheUser getCacheUserDBRestore(CacheUser cacheUser){
        Gson gson = new Gson();
        List<String> friends = gson.fromJson(cacheUser.getJsonFriends(),new TypeToken<List<String>>(){}.getType());
        cacheUser.setFriends(friends);
        return cacheUser;
    }
}
