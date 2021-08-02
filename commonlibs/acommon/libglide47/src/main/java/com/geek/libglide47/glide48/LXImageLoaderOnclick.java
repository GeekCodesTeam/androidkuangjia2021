package com.geek.libglide47.glide48;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.File;

public interface LXImageLoaderOnclick {
    void loadImage(@NonNull Object uri, @NonNull ImageView imageView);

    /**
     * 获取图片对应的文件
     *
     * @param context
     * @param uri
     * @return
     */
    File getImageFile(@NonNull Context context, @NonNull Object uri);
}

