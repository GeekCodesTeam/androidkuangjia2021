package com.example.slbappcomm.zhendonghua;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyt on 2018/1/2.  解决帧动画产生的OOM
 * 优化：实现动画一直运行，添加了停止动画方法
 * <p>
 * 此工具类源于stack over flow
 * 原文链接:http://stackoverflow.com/questions/8692328/causing-outofmemoryerror-in-frame-by-frame-animation-in-android
 * 主要使用了BitmapFactory.decodeByteArray方法通过底层C来绘制图片,有效防止OOM
 * 使用了第三方类库:org.apache.commons.io.IOUtils,将Inputstream转为byte字节数组
 */

public class FrameAnimationDrawable {
    private static FrameAnimationDrawable fDrawable;
    private boolean isStop = false;//是否停止动画
    private int frameNumber;//当前执行第几帧动画
    private boolean isLoop = true;

    public FrameAnimationDrawable(boolean isLoop, int resourceId, ImageView imageView) {
        this.isLoop = isLoop;
        animateRawManuallyFromXML(resourceId, imageView);
    }

    public FrameAnimationDrawable(int resourceId, ImageView imageView) {
        this(true, resourceId, imageView);
    }

    public FrameAnimationDrawable() {
    }

    /**
     * 单例模式创建此类
     *
     * @return
     */
    public static FrameAnimationDrawable create() {
        if (fDrawable == null) {
            synchronized (FrameAnimationDrawable.class) {
                if (fDrawable == null) {
                    fDrawable = new FrameAnimationDrawable();
                }
            }
        }
        return fDrawable;
    }

    public static class MyFrame {
        byte[] bytes;
        int duration;
        Drawable drawable;
        boolean isReady = false;
    }

    /***
     * 性能更优
     * 在animation-list中设置时间
     * **/
    public void animateRawManuallyFromXML(int resourceId, ImageView imageView) {
        isStop = false;
        loadFromXml(resourceId, imageView);
    }

    /**
     * XmlPullParser 解析
     *
     * @param resourceId
     * @param imageView
     */
    //2
    private void loadFromXml(final int resourceId, final ImageView imageView) {
        final Context context = imageView.getContext();
        //XmlPullParser 解析
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<MyFrame> myFrames = new ArrayList<MyFrame>();

                XmlResourceParser parser = context.getResources().getXml(
                        resourceId);
                try {
                    int eventType = parser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_DOCUMENT) {

                        } else if (eventType == XmlPullParser.START_TAG) {
                            if (parser.getName().equals("item")) {
                                byte[] bytes = null;
                                int duration = 1000;

                                for (int i = 0; i < parser.getAttributeCount(); i++) {
                                    if (parser.getAttributeName(i).equals(
                                            "drawable")) {
                                        int resId = Integer.parseInt(parser
                                                .getAttributeValue(i)
                                                .substring(1));
                                        bytes = IOUtils.toByteArray(context
                                                .getResources()
                                                .openRawResource(resId));
                                    } else if (parser.getAttributeName(i)
                                            .equals("duration")) {
                                        duration = parser.getAttributeIntValue(
                                                i, 1000);
                                    }
                                }

                                MyFrame myFrame = new MyFrame();
                                myFrame.bytes = bytes;
                                myFrame.duration = duration;
                                myFrames.add(myFrame);
                            }

                        } else if (eventType == XmlPullParser.END_TAG) {

                        } else if (eventType == XmlPullParser.TEXT) {

                        }

                        eventType = parser.next();
                    }
                    //解析完成执行动画
                    animateRawManually(myFrames, imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e2) {
                    e2.printStackTrace();
                }
            }
        }).run();
    }

    // 3
    private void animateRawManually(List<MyFrame> myFrames, ImageView imageView) {
        animateRawManually(myFrames, imageView, 0);
    }

    // 4
    private void animateRawManually(final List<MyFrame> myFrames,
                                    final ImageView imageView, final int currentFrameNumber) {
        if (isStop) {//结束动画
            imageView.clearAnimation();//清除动画
            System.gc();//提醒系统回收之前的
            return;
        }
        frameNumber = currentFrameNumber;
        final MyFrame thisFrame = myFrames.get(frameNumber);

        if (frameNumber == 0) {
            thisFrame.drawable = new BitmapDrawable(imageView.getContext()
                    .getResources(), BitmapFactory.decodeByteArray(
                    thisFrame.bytes, 0, thisFrame.bytes.length));
        } else {
            MyFrame previousFrame = myFrames.get(frameNumber - 1);
            ((BitmapDrawable) previousFrame.drawable).getBitmap().recycle();
            previousFrame.drawable = null;
            previousFrame.isReady = false;
        }

        imageView.setImageDrawable(thisFrame.drawable);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Make sure ImageView hasn't been changed to a different Image
                // in this time
                if (imageView.getDrawable() == thisFrame.drawable) {
                    if (frameNumber + 1 < myFrames.size()) {
                        MyFrame nextFrame = myFrames.get(frameNumber + 1);
                        if (nextFrame.isReady) {
                            // Animate next frame
                            animateRawManually(myFrames, imageView, frameNumber + 1);
                        } else {
                            nextFrame.isReady = true;
                        }
                    } else {
                        if (isLoop) {
                            //重新开始播放
                            frameNumber = -1;
                            animateRawManually(myFrames, imageView, frameNumber + 1);
                        } else {
                            stopAnimation(true);
                        }
                    }
                }
            }
        }, thisFrame.duration);
        // Load next frame
        if (frameNumber + 1 < myFrames.size()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyFrame nextFrame = myFrames.get(frameNumber + 1);
                    nextFrame.drawable = new BitmapDrawable(imageView
                            .getContext().getResources(),
                            BitmapFactory.decodeByteArray(nextFrame.bytes, 0,
                                    nextFrame.bytes.length));
                    if (nextFrame.isReady) {
                        // Animate next frame
                        animateRawManually(myFrames, imageView, frameNumber + 1);
                    } else {
                        nextFrame.isReady = true;
                    }

                }
            }).run();
        }
    }

    /**
     * 停止动画
     *
     * @param isStop
     */
    public void stopAnimation(boolean isStop) {
        this.isStop = isStop;
    }
}
