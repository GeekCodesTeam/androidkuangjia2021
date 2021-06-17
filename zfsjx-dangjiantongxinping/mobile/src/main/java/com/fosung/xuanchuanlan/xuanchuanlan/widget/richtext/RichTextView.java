/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-8-25 上午9:50
 * ********************************************************
 */

package com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 支持包含图片的富文本显示
 */
public class RichTextView extends AppCompatTextView implements Drawable.Callback, View.OnAttachStateChangeListener {

    private RichViewUtil richView = new RichViewUtil();

    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置图片点击监听
     */
    public void setOnImageClickListener(OnRichImageClickListener onImageClickListener) {
        richView.setOnImageClickListener(onImageClickListener);
    }

    /**
     * 设置富文本
     *
     * @param text 富文本
     */
    public void setRichText(String text) {
        super.setText(richView.getRichText(text, getContext(), this));
    }

    /**
     * 设置富文本, 异步，显示进度框
     *
     * @param text 富文本
     */
    public void setRichTextWithBar(String text) {
        richView.getRichTextAsync(text, getContext(), this, new RichViewUtil.OnFinishListener() {
            @Override
            public void onFinished(SpannableStringBuilder spanned) {
                setText(spanned);
            }
        });

    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        richView.recycle();
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            invalidateOutline();
        }
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
    }
}