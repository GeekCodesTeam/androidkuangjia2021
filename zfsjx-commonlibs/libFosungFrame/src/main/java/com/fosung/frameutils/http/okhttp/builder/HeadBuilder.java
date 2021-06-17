/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.fosung.frameutils.http.okhttp.builder;


import com.fosung.frameutils.http.okhttp.OkHttpUtils;
import com.fosung.frameutils.http.okhttp.request.OtherRequest;
import com.fosung.frameutils.http.okhttp.request.RequestCall;

/**
 * HTTP请求Header构建器
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
