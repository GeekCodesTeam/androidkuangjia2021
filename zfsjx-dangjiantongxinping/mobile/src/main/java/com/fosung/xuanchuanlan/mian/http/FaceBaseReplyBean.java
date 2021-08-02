/*
 * *********************************************************
 *   author   yangqilin
 *   company  fosung
 *   email    yql19911010@126.com
 *   date     19-1-18 上午9:54
 * ********************************************************
 */

package com.fosung.xuanchuanlan.mian.http;


import com.fosung.frameutils.http.ZReply;

/**
 * Http报文返回数据的基类，如果使用ZResponse直接获取实体，则实体需要继承此基类
 * <p>
 * 比较正规的报文返回结构，也是此程序主要的报文结构
 */
public class FaceBaseReplyBean implements ZReply {

    public boolean success;
    public String  message;
    public int  code;

    @Override
    public boolean isSuccess() {
        return success == true;
    }

    @Override
    public int getReplyCode() {
        if (code==0) {
            return 200;
        } else {
            return 204;
        }
    }

    @Override
    public String getErrorMessage() {
        return message;
    }
}
