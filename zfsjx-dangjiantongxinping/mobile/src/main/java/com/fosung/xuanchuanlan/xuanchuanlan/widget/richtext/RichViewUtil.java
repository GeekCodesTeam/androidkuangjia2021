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
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import com.zcolin.gui.ZDialogAsyncProgress;

import java.util.ArrayList;
import java.util.List;

class RichViewUtil {

    private OnRichImageClickListener onImageClickListener;//图片点击回调
    private GlideImageGeter          glideImageGeter;


    /**
     * 设置图片点击监听
     */
    void setOnImageClickListener(OnRichImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    /**
     * 设置富文本
     *
     * @param text 富文本
     */
    SpannableStringBuilder getRichText(String text, Context context, TextView textView) {
        if (glideImageGeter == null) {
            glideImageGeter = new GlideImageGeter(context, textView);
        }

        Spanned spanned = Html.fromHtml(text, glideImageGeter, null);
        return processSpanned(spanned);
    }

    void getRichTextAsync(String text, Context context, TextView textView, final OnFinishListener onFinishListener) {
        if (glideImageGeter == null) {
            glideImageGeter = new GlideImageGeter(context, textView);
        }

        final Spanned spanned = Html.fromHtml(text, glideImageGeter, null);
        new ZDialogAsyncProgress(context).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
            @Override
            public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                ZDialogAsyncProgress.ProcessInfo info = new ZDialogAsyncProgress.ProcessInfo();
                info.info = processSpanned(spanned);
                return info;
            }

            @Override
            public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {
                if (onFinishListener != null) {
                    onFinishListener.onFinished((SpannableStringBuilder) info.info);
                }
            }
        }).show();
    }


    private SpannableStringBuilder processSpanned(Spanned spanned) {
        SpannableStringBuilder spannableStringBuilder;
        if (spanned instanceof SpannableStringBuilder) {
            spannableStringBuilder = (SpannableStringBuilder) spanned;
        } else {
            spannableStringBuilder = new SpannableStringBuilder(spanned);
        }

        // 处理图片得点击事件
        ImageSpan[] imageSpans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ImageSpan.class);
        final List<String> imageUrls = new ArrayList<>();
        for (int i = 0, size = imageSpans.length; i < size; i++) {
            ImageSpan imageSpan = imageSpans[i];
            String imageUrl = imageSpan.getSource();
            int start = spannableStringBuilder.getSpanStart(imageSpan);
            int end = spannableStringBuilder.getSpanEnd(imageSpan);
            imageUrls.add(imageUrl);
            final int finalI = i;

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(imageUrls, finalI);
                    }
                }
            };

            ClickableSpan[] clickableSpans = spannableStringBuilder.getSpans(start, end, ClickableSpan.class);
            if (clickableSpans != null && clickableSpans.length != 0) {
                for (ClickableSpan cs : clickableSpans) {
                    spannableStringBuilder.removeSpan(cs);
                }
            }
            spannableStringBuilder.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableStringBuilder;
    }

    void recycle() {
        glideImageGeter.recycle();
        glideImageGeter = null;
    }

    public interface OnFinishListener {
        void onFinished(SpannableStringBuilder spanned);
    }
}