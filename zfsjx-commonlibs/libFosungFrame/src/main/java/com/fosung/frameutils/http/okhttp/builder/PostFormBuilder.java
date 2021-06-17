/***********************************************************
 * author   colin
 * company  fosung
 * email    wanglin2046@126.com
 * date     16-7-15 下午4:41
 **********************************************************/

package com.fosung.frameutils.http.okhttp.builder;


import android.text.TextUtils;

import com.fosung.frameutils.http.okhttp.request.PostFormRequest;
import com.fosung.frameutils.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单请求构建器
 */
public class PostFormBuilder extends OkHttpRequestBuilder<PostFormBuilder> implements HasParamsable {
    private List<FileInput> files = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostFormRequest(url, tag, params, headers, files, id, mimeType).build();
    }

    public PostFormBuilder files(String key, File[] arrFile) {
        for (File file : arrFile) {
            String fileName = file.getName();
            if (TextUtils.isEmpty(fileName)) {
                fileName = key;
            }
            this.files.add(new FileInput(key, fileName, file));
        }
        return this;
    }

    public PostFormBuilder files(Map<String, File> files) {
        for (String key : files.keySet()) {
            File file = files.get(key);
            String fileName = file.getName();
            if (TextUtils.isEmpty(fileName)) {
                fileName = key;
            }
            this.files.add(new FileInput(key, fileName, file));
        }
        return this;
    }

    public PostFormBuilder addFile(String key, File file) {
        String fileName = file.getName();
        if (TextUtils.isEmpty(fileName)) {
            fileName = key;
        }
        files.add(new FileInput(key, fileName, file));
        return this;
    }

    public PostFormBuilder addFile(String name, String filename, File file) {
        files.add(new FileInput(name, filename, file));
        return this;
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File   file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }


}