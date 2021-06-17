/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.fosung.frameutils.http.okhttp.builder;


import com.fosung.frameutils.http.okhttp.request.PostFileRequest;
import com.fosung.frameutils.http.okhttp.request.RequestCall;

import java.io.File;

import okhttp3.MediaType;

/**
 * 文件上传构建器
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder> {
    private File      file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build() {
        return new PostFileRequest(url, tag, params, headers, file, mediaType, id).build();
    }


}
