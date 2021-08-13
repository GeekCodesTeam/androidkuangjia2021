/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.fosung.frameutils.http.okhttp.callback;


import com.fosung.frameutils.util.GsonUtil;
import com.fosung.frameutils.util.LogUtil;

import java.io.IOException;

import okhttp3.Response;

/**
 * Http请求解析成Gson对象回调类
 */
public abstract class GsonCallback<T> extends Callback<T> {

    public Class<T> cls;

    public GsonCallback(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T parseNetworkResponse(Response response) throws IOException {
        String string = response.body()
                                .string();
        LogUtil.i("http response", string);
        
        if (cls == String.class) {
            return (T) string;
        }
        return GsonUtil.stringToBean(string, cls);
    }

}
