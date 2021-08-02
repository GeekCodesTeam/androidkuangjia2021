/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-10-9 下午1:27
 * ********************************************************
 */

package com.fosung.xuanchuanlan.common.http.entity;


import com.fosung.frameutils.http.ZReply;

/**
 * Http报文返回数据的基类，如果使用ZResponse直接获取实体，则实体需要继承此基类
 * 
 * 比较正规的报文返回结构，也是此程序主要的报文结构
 */
public class BaseReplyBeanMaster2 implements ZReply {
    
    public boolean success;
    public String message;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public int getReplyCode() {
        return success?1:0;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
