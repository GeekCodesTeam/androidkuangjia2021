package com.geek.libglide47.glide48;

import android.graphics.drawable.Drawable;
//
//

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.geek.libutils.app.MyLogUtil;

public class CommonTarget<T> implements Target<T> {
    private final int width;
    private final int height;

    public CommonTarget() {
        this.width = Target.SIZE_ORIGINAL;
        this.height = Target.SIZE_ORIGINAL;
    }

    public CommonTarget(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        MyLogUtil.e("--glide48-CommonTarget-","开始下载...");
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable) {
        MyLogUtil.e("--glide48-CommonTarget-","保存失败");
    }

    @Override
    public void onResourceReady(@NonNull T resource, @Nullable Transition<? super T> transition) {
        MyLogUtil.e("--glide48-CommonTarget-","成功保存");

    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

    @Override
    public void getSize(@NonNull SizeReadyCallback cb) {
        cb.onSizeReady(width, height);
    }

    @Override
    public void removeCallback(@NonNull SizeReadyCallback cb) {

    }

    @Override
    public void setRequest(@Nullable Request request) {

    }

    @Nullable
    @Override
    public Request getRequest() {
        return null;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
