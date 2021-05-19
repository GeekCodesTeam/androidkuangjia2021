package com.example.shining.libclearmermory.flows;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shining.libutils.utilslib.app.App;

/**
 * Created by shining on 2017/12/13.
 */

public class LxFlowCeng {

    public static final int X_INT = -240;//初始位置
    public static final int Y_INT = -400;
    private static String TAG = "FloatPresenter";
    private static WindowManager wm = null;
    private static WindowManager.LayoutParams wmParams;
    private static ImageView myFV = null;
    private static Button btn1 = null;
    private static LxFloatRelativeLayout linearLayout = null;


    private static Context context;

    //开始时创建
    public static void create(Context activity) {
        context = activity;
        ActivityManager manager = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo runningAppProcessInfo = manager.getRunningAppProcesses().get(0);
        Log.e("TAG", "create: " + runningAppProcessInfo.processName);
        //主进程
        if (runningAppProcessInfo.processName.equals("com.haier.cellarette")) {
            createView(activity);
        }
    }

    //创建view
    private static void createView(Context activity) {
        //获取WindowManager
        wm = (WindowManager) App.get().getSystemService(Context.WINDOW_SERVICE);
        //设置LayoutParams(全局变量）相关参数
        wmParams =  new WindowManager.LayoutParams();

        /**
         *以下都是WindowManager.LayoutParams的相关属性
         * 具体用途可参考SDK文档
         */
        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;   //设置window type
        wmParams.format = PixelFormat.RGBA_8888;   //设置图片格式，效果为背景透明

        //设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            /*
             * 下面的flags属性的效果形同“锁定”。
	         * 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
	         * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL| LayoutParams.FLAG_NOT_FOCUSABLE| LayoutParams.FLAG_NOT_TOUCHABLE;
	         */

//        wmParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;   //调整悬浮窗口至左上角，便于调整坐标
        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x = (int) (X_INT * 0.9);
        wmParams.y = (int) (Y_INT * 0.9);

        //设置悬浮窗口长宽数据
//        wmParams.width = PARAM_WIDTH;
//        wmParams.height = PARAM_HEIGHT;
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = 260;

        linearLayout = new LxFloatRelativeLayout(activity.getApplicationContext());
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                AISpeechUtil.reWakeup(true);
//                ToastUtil.setToastLong("click!");
//            }
//        });
//        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
////                AISpeechUtil.breakVoice();
//                return true;
//            }
//        });
//        tv1 = (TextView) linearLayout.findViewById(R.id.tv1);
//        tv2 = (TextView) linearLayout.findViewById(R.id.tv2);
//        tv3 = (TextView) linearLayout.findViewById(R.id.tv3);
//        btn1 = (Button) linearLayout.findViewById(R.id.btn1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyLogUtil.d("BitmapFactory", "click");
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                for (int i = 0; i < 20; i++) {
//                    MyLogUtil.d("BitmapFactory", "i=" + i);
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeResource(App.get().getResources(), R.drawable.icon_search, options);
//                        int bytes = bitmap.getAllocationByteCount();//Returns the size of the allocated memory used to store this bitmap's pixels.
//                        MyLogUtil.d("BitmapFactory", "bytes=" + bytes);
//                        list.add(bitmap);
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//        });

        //显示myFloatView图像
        wm.addView(linearLayout, wmParams);
    }

    //获得浮动的ImageView
    @SuppressLint("WrongConstant")
    public static ImageView getMyFV() {
        if (linearLayout == null) {
            Log.e(TAG, "getMyFV: null");
            createView(App.get());
        }
        Log.e(TAG, "getMyFV: " + myFV.getVisibility());
        return myFV;
    }

    //获得浮动的layout
    public static LxFloatRelativeLayout getLayout() {
        if (linearLayout == null) {
            createView(App.get());
        }
        return linearLayout;
    }


}
