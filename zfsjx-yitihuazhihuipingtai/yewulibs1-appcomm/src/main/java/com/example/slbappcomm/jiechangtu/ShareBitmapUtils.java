package com.example.slbappcomm.jiechangtu;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import com.example.slbappcomm.R;
import com.geek.libutils.app.MyLogUtil;

public class ShareBitmapUtils {

    private int mDefaultLogoBgHeight;
    private int mBgHeight;
    private Bitmap mNewb;

    public Bitmap drawComBitmap(View view, Activity activity) {
//        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);// 允许当前窗口保存缓存信息，这样getDrawingCache()方法才会返回一个Bitmap
        if (null != view) {
            //获取顶部的bitmap
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            MyLogUtil.e("--gee11--width---",width+"");
            MyLogUtil.e("--gee11--height--",height+"");

            int statusBarHeight = getStatusBarHeight(activity);
            //去掉状态栏和导航栏的高度
//            int height = view.getMeasuredHeight() - getNavigationBarHeight(activity) - statusBarHeight;
            //拼接图片
            Bitmap logoBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.slb_logo);
            Bitmap codeBitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.slb_logo);
            int logoHeight = logoBitmap.getHeight();
            int codeHeight = codeBitmap.getHeight();
            int codeWidth = codeBitmap.getWidth();
            mDefaultLogoBgHeight = logoHeight * 2;
            mBgHeight = height - mDefaultLogoBgHeight;
            Bitmap drawingCache = view.getDrawingCache();
//            Bitmap topBitmap = Bitmap.createBitmap(drawingCache, 0, statusBarHeight, width, height);
            Bitmap topBitmap = Bitmap.createBitmap(drawingCache, 0, 0, width, height);
            //绘制原始图片
            mNewb = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            //绘制底部背景
            Canvas canvas = new Canvas(mNewb);
            Paint mBitPaint = new Paint();
//            mBitPaint.setColor(Color.WHITE);
            mBitPaint.setColor(ContextCompat.getColor(activity, R.color.blue33b5e5));
            mBitPaint.setStyle(Paint.Style.FILL);
//            canvas.drawRect(0, height, width, mBgHeight, mBitPaint);
            canvas.drawRect(0, height, width, 0, mBitPaint);
            // logo图片
            canvas.drawBitmap(topBitmap, 0, 0, null);
            //绘制二维码和logo
            int mDefaultMargin = 0;
            canvas.drawBitmap(logoBitmap, mDefaultMargin, (height - mDefaultLogoBgHeight / 2) - logoHeight / 2, null);
            canvas.drawBitmap(codeBitmap, width - codeWidth - mDefaultMargin, (height - mDefaultLogoBgHeight / 2) - codeHeight / 2, null);
            canvas.save();
//            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
            //释放资源
            view.destroyDrawingCache();
            drawingCache.recycle();
            logoBitmap.recycle();
            codeBitmap.recycle();
            topBitmap.recycle();
        }
        return mNewb;
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    /**
     * view截图
     *
     * @return
     */
    public Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
//        //根据bitmap生成一个画布
//        Canvas canvas = new Canvas(bitmap);
//        //注意：这里是解决图片透明度问题，给底色上白色，若存储时保存的为png格式的图，则无需此步骤
//        canvas.drawColor(Color.WHITE);
//        //手动将这个视图渲染到指定的画布上
//        v.draw(canvas);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }
}
