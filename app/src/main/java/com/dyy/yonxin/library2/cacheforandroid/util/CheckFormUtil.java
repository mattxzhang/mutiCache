package com.dyy.yonxin.library2.cacheforandroid.util;

import com.dyy.yonxin.library2.cacheforandroid.bean.CheckNullItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 段钰莹 on 2017/10/14.
 */

public class CheckFormUtil {
    private List<CheckNullItem> checkNullItems = new ArrayList<>();

    public void clear(){
        checkNullItems.clear();
    }

    public CheckFormUtil addFormElement(String checkContent, String errMessage){
        CheckNullItem item = new CheckNullItem();
        item.setCheckContent(checkContent);
        item.setErrMessage(errMessage);
        return addFormElement(item);
    }

    /**
     * 用于一些判断文件存在性的特殊情况，pass为空则不通过
     * @param pass
     * @param errMessage
     * @return
     */
    public CheckFormUtil addFormElement(boolean pass, String errMessage){
        CheckNullItem item = new CheckNullItem();

        item.setCheckContent(pass?"123":"");
        item.setErrMessage(errMessage);

        return addFormElement(item);
    }

    public CheckFormUtil addFormElement(CheckNullItem item){
        checkNullItems.add(item);
        return this;
    }

    public boolean isPassCheck(String checkContent, String errMessage){
        if(checkContent == null || errMessage.length() == 0)
            return false;
        return true;
    }

    public boolean isPassCheck(){
        for(CheckNullItem item:checkNullItems){
            String content = item.getCheckContent();
            if(content == null || content.length() == 0){
                return false;
            }
        }
        return true;
    }

    public String getErrMessage(){
        for(CheckNullItem item:checkNullItems){
            String content = item.getCheckContent();
            if(content == null || content.length() == 0){
                return item.getErrMessage();
            }
        }
        return "";
    }

}
