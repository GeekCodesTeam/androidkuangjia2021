/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.fosung.frameutils.http.okhttp.builder;


import com.fosung.frameutils.http.okhttp.request.PostStringRequest;
import com.fosung.frameutils.http.okhttp.request.RequestCall;

import okhttp3.MediaType;

/**
 * String构建器
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder> {
    private String    content;


    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public RequestCall build() {
        MediaType mime = mimeType == null ? null : MediaType.parse(mimeType);
        return new PostStringRequest(url, tag, params, headers, content, mime, id).build();
    }
}
