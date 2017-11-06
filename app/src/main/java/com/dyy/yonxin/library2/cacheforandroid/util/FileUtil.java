package com.dyy.yonxin.library2.cacheforandroid.util;

import com.dyy.yonxin.library2.cacheforandroid.CacheForAndorid;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class FileUtil {
    public static String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(CacheForAndorid.getRes().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            StringBuilder sb = new StringBuilder();
            while((line = bufReader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
            bufReader.close();
            inputReader.close();
            sb.deleteCharAt(sb.length()-1);
            return URLDecoder.decode(sb.toString(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
