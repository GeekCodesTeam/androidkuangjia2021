package com.fosung.xuanchuanlan.setting.http;


import com.fosung.frameutils.http.ZReply;

public class LoginBaseReply implements ZReply {

    int    expires_in;
    String scope;

    @Override
    public boolean isSuccess() {
        return scope.equals("login");
    }

    @Override
    public int getReplyCode() {
        return expires_in;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
