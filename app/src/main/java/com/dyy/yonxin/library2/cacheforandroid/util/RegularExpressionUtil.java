package com.dyy.yonxin.library2.cacheforandroid.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 段钰莹 on 2017/11/2.
 */

public class RegularExpressionUtil {
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static boolean isDouble(String str){
        Pattern pattern = Pattern.compile("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+");
        Matcher isNum = pattern.matcher(str);
        return  isNum.matches();
    }

    public static boolean isDecimal(String str){
       return isDouble(str) || isNumeric(str);
    }
}
