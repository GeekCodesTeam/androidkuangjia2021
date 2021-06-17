/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午1:25
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.http.entity;

import com.fosung.frameutils.http.ZReply;

/**
 * Http报文返回数据的基类，如果使用ZResponse直接获取实体，则实体需要继承此基类
 * 
 * 85信息的报文比较坑爹，所以单独弄父类，至于多坑，接下来你会深刻体会的
 */
public class BaseReplyBean85 implements ZReply {

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public int getReplyCode() {
        return 0;
    }

    @Override
    public String getErrorMessage() {
        return "";
    }
}
