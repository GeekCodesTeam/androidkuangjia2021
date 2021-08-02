/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-8-25 上午9:51
 * https://github.com/jesson1989/AndroidRichText
 * ********************************************************
 */

package com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.fosung.frameutils.util.DisplayUtil;
import com.fosung.frameutils.util.ScreenUtil;
import com.fosung.xuanchuanlan.R;

import java.util.HashSet;


public class GlideImageGeter implements Html.ImageGetter {
    private       HashSet<GifDrawable> gifDrawables;
    private final TextView             mTextView;
    private final Context              mContext;

    public void recycle() {
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }

    public GlideImageGeter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        gifDrawables = new HashSet<>();
        mTextView.setTag(R.id.img_tag, this);
    }

    @Override
    public Drawable getDrawable(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (isGif(url)) {
            GifTarget target = new GifTarget();
            Glide.with(mContext)
                 .asGif()
                 .load(url)
                 .into(target);
            return target.getUrlDrawable();
        } else {
            BitmapTarget target = new BitmapTarget();
            Glide.with(mContext)
                 .asBitmap()
                 .load(url)
                 .into(target);

//            int padding = 0;
//
//            Drawable drawable = mContext.getResources().getDrawable(R.drawable.chatting_item_file_pdf);
//
//            int w = mTextView.getWidth();//需要在延时方法内调用RichTextView.setRichText,否则此处会返回0;
//            int hh = drawable.getIntrinsicHeight();
//            int ww = drawable.getIntrinsicWidth();
//            int high = hh * (w - padding * 2) / ww;
//            int offset = (int) (high * 0.24);
//            Rect rect = new Rect(padding, padding-offset, w, high-offset);
//            drawable.setBounds(rect);
//            return drawable;

            return target.getUrlDrawable();
        }
    }



    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase()
                                 .equals(path.substring(index + 1)
                                             .toUpperCase());
    }

    private class GifTarget extends SimpleTarget<GifDrawable> {
        private UrlDrawable urlDrawable = new UrlDrawable();
        private int         padding     = DisplayUtil.dip2px(mContext, 0);


        public UrlDrawable getUrlDrawable() {
            return urlDrawable;
        }

        @Override
        public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> transition) {
            int w = ScreenUtil.getScreenWidth(mContext);
            int hh = resource.getIntrinsicHeight();
            int ww = resource.getIntrinsicWidth();
            int high = hh * (w - padding * 2) / ww;
            Rect rect = new Rect(padding, padding, w - padding, high);
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(mTextView);
            resource.start();
            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }

    private class BitmapTarget extends SimpleTarget<Bitmap> {
        private final UrlDrawable urlDrawable = new UrlDrawable();
        private       int         padding     = DisplayUtil.dip2px(mContext, 0);

        public UrlDrawable getUrlDrawable() {
            return urlDrawable;
        }

        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
            Drawable drawable = new BitmapDrawable(mContext.getResources(), resource);
//            int screenWidth = ScreenUtil.getScreenWidth(mContext);//原方法不能获取控件宽度，使用下面一行的代码获取。
            int w = mTextView.getWidth();//需要在延时方法内调用RichTextView.setRichText,否则此处会返回0;
            int hh = drawable.getIntrinsicHeight();
            int ww = drawable.getIntrinsicWidth();
            int high = hh * (w - padding * 2) / ww;
            int offsetY = (int) (high * 0.3);
            Rect rect = new Rect(padding, padding-offsetY, w - padding, high-offsetY);
            drawable.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(drawable);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }
}