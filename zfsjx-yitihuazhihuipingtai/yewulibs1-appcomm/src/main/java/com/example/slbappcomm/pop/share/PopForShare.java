//package com.example.slbappcomm.pop.share;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.ColorDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewConfiguration;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//import com.blankj.utilcode.util.ToastUtils;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.Request;
//import com.bumptech.glide.request.RequestOptions;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.bumptech.glide.request.target.SizeReadyCallback;
//import com.bumptech.glide.request.target.Target;
//import com.bumptech.glide.request.transition.Transition;
//import com.example.slbappcomm.R;
//import com.example.slbappjpushshare.fenxiang.JPushShareUtils;
//import com.example.slbappjpushshare.fenxiang.OnShareResultInfoLitener;
//import com.example.slbappjpushshare.fenxiang.beans.WeixinBeanParam;
//import com.geek.libutils.app.MyLogUtil;
//import com.haier.cellarette.baselibrary.btnonclick.view.BounceView;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
//import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import com.umeng.analytics.MobclickAgent;
//
//import java.lang.reflect.Method;
//
//import cn.jiguang.share.android.api.JShareInterface;
//
//// 分享弹窗
//public class PopForShare extends PopupWindow implements OnShareResultInfoLitener {
//
//    private Activity activity;
//    private View mMenuView;
//    private TextView tv1;
//    private ConstraintLayout cons1;
//    //    private LinearLayout ll1a;
//    private ImageView iva1;
//    //    private LinearLayout ll2a;
//    private ImageView iva2;
//    private ImageView iva3;
//    private JPushShareUtils jPushShareUtils;
//    private Resources res;
//    private Bitmap bmp;
//    private RequestOptions options;
//
//    public PopForShare() {
//
//    }
//
//    public PopForShare(final Activity activity, final String title, final String name, final String imgUrl, final String url1, final String url2, final OnFinishResultClickListener mListener) {
//        this(activity, title, name, imgUrl, url1, url2, "", "", "", "", mListener);
//    }
//
//    public PopForShare(final Activity activity, final String title, final String name, final String imgUrl, final String url1, final String url2, String friendMobclick, String wxCircleMobclick, String linkMobclick, String cancleClick, final OnFinishResultClickListener mListener) {
//        this(activity, title, name, imgUrl, url1, url2, "", friendMobclick, wxCircleMobclick, linkMobclick, cancleClick, mListener);
//    }
//
//    public PopForShare(final Activity activity, final String title, final String name, final String imgUrl, final String url1, final String url2, final String url3, final String friendMobclick, final String wxCircleMobclick, final String linkMobclick, final String cancleClick, final OnFinishResultClickListener mListener) {
//        super(activity);
////        AutoSizeConfig.getInstance().setCustomFragment(true);
////        AutoSize.autoConvertDensity((Activity) activity, 375, true);
////        //虚拟键
////        if (NavigationBarUtil.hasNavigationBar(activity)) {
//////            NavigationBarUtil.initActivity(getWindow().getDecorView());
////            NavigationBarUtil.hideBottomUIMenu(activity);
////        }
////        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
////        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
////        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);//这句话导致第一次点击没有反应
//        this.activity = activity;
//        res = activity.getResources();
//        bmp = BitmapFactory.decodeResource(res, R.drawable.slb_logo);
//        jPushShareUtils = new JPushShareUtils(this);
//        LayoutInflater inflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mMenuView = inflater.inflate(R.layout.pop_share_pay, null);
//        cons1 = mMenuView.findViewById(R.id.cons1);
////        MohuReUtil.set_muhu_re(activity, rlm2);
////        ll1a = mMenuView.findViewById(R.id.ll1a);
//        iva1 = mMenuView.findViewById(R.id.iva1);
////        ll2a = mMenuView.findViewById(R.id.ll2a);
//        iva2 = mMenuView.findViewById(R.id.iva2);
//        iva3 = mMenuView.findViewById(R.id.iva3);
//        tv1 = mMenuView.findViewById(R.id.tv1);
//        //
//        BounceView.addAnimTo(iva1);
//        BounceView.addAnimTo(iva2);
//        BounceView.addAnimTo(iva3);
//        BounceView.addAnimTo(tv1);
//
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!TextUtils.isEmpty(cancleClick)) {
//                    MobclickAgent.onEvent(activity, cancleClick);
//                }
//                jPushShareUtils.ondes();
//                dismiss();
//            }
//        });
//        iva1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (mListener != null) {
////                    mListener.onWeChat1();
////                }
//                set_wechat1(title, name, imgUrl, "", url1);
//                if (!TextUtils.isEmpty(friendMobclick)) {
//                    MobclickAgent.onEvent(activity, friendMobclick);
//                }
////                dismiss();
////                activity.startActivity(new Intent("hs.act.phone.DemoMusicActivity"));
//            }
//        });
//        iva2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (mListener != null) {
////                    mListener.onWeChat2();
////                }
//                if (!TextUtils.isEmpty(wxCircleMobclick)) {
//                    MobclickAgent.onEvent(activity, wxCircleMobclick);
//                }
//                set_wechat2(title, name, imgUrl, url1, url2);
//            }
//        });
//        iva3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                if (mListener != null) {
////                    mListener.onWeChat2();
////                }
//                if (!TextUtils.isEmpty(linkMobclick)) {
//                    MobclickAgent.onEvent(activity, linkMobclick);
//                }
//                if (copy(activity, url3)) {
//                    ToastUtils.showLong("已复制到剪切板");
//                    dismiss();
//                }
//            }
//        });
//        //
//        options = new RequestOptions()
////                .signature(new ObjectKey(UUID.randomUUID().toString()))  // 重点在这行
//                .skipMemoryCache(false)
//                .dontAnimate()
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .placeholder(R.drawable.ic_def_loading)
//                .error(R.drawable.ic_def_loading)
//                .fallback(R.drawable.ic_def_loading); //url为空的时候,显示的图片;
//        // 设置SelectPicPopupWindow的View
//        this.setContentView(mMenuView);
//        // 设置SelectPicPopupWindow弹出窗体的宽
//        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体的高
//        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        // 设置SelectPicPopupWindow弹出窗体可点击
//        this.setFocusable(true);
////        this.setOutsideTouchable(false);
////        this.setTouchInterceptor(new View.OnTouchListener() {
////            @Override
////            public boolean onTouch(View v, MotionEvent event) {
////                if (!isOutsideTouchable()) {
////                    View mView = getContentView();
////                    if (null != mView)
////                        mView.dispatchTouchEvent(event);
////                }
////                return isFocusable() && !isOutsideTouchable();
////            }
////        });
//        //
////        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
////        // 设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
////        Bitmap shot = BitmapUtils.takeScreenShot(activity.getWindow().getDecorView());
////        Bitmap blur = BitmapUtils.blur(activity, shot);
////        this.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), blur));
//        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                int height = mMenuView.findViewById(R.id.cons1).getTop();
//                int music_seek_bar_cycle = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (music_seek_bar_cycle < height) {
//                        dismiss();
//                    }
//                }
////                dismiss();
//                return true;
//            }
//        });
//        showPopupWindow();
////        setOnDismissListener(new OnDismissListener() {
////            @Override
////            public void onDismiss() {
////                //虚拟键
////                if (NavigationBarUtil.hasNavigationBar(activity)) {
//////            NavigationBarUtil.initActivity(getWindow().getDecorView());
////                    NavigationBarUtil.hideBottomUIMenu(activity);
////                }
////            }
////        });
//        activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
////                NavigationBarUtil.hideBottomUIMenu(activity);
//                if (visibility == View.SYSTEM_UI_FLAG_FULLSCREEN || visibility == View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN) {
//                    Log.i("TAG", "全屏状态======");
//                } else {
//                    Log.i("TAG", "非全屏状态======");
//                }
//            }
//        });
//
//    }
//
//
//    public void showPopupWindow() {
////        showAtLocation(activity.getWindow().getDecorView(),
////                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL
////                , 0, 0); // 设置layout在PopupWindow中显示的位置
//        setFocusable(false);
//        update();
//        showAtLocation(activity.getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, 0);
////        NavigationBarUtil.fullScreenImmersive(getContentView());
//        setFocusable(true);
//        update();
//
//    }
//
//    public void set_wechat1(final String title, final String name, String imgUrl, final String url1, final String url2) {
//        if (!TextUtils.isEmpty(url1)) {
//            // 微信小程序
////            WeixinBeanParam.set_xcx(activity, title, name, imgUrl, url1, url2);
//            set_xcx(activity, title, name, imgUrl, url1, url2);
//            dismiss();
//        } else {
//            // 微信
////            Glide.with(activity).asBitmap().load(imgUrl).into(new SimpleTarget<Bitmap>() {
////                @Override
////                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
////                    JShareInterface.share(Wechat.Name,
////                            WeixinBeanParam.share_web2(
////                                    title,
////                                    name,
////                                    url2,
////                                    resource),
////                            jPushShareUtils.mShareListener1);
////                }
////            });
//            //
//            Glide.with(activity).asBitmap().load(imgUrl)
//                    .apply(options)
//                    .into(new Target<Bitmap>() {
//                        @Override
//                        public void onLoadStarted(@Nullable Drawable placeholder) {
//                            Log.e("geek", "onLoadStarted");
//                        }
//
//                        @Override
//                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
//                            Log.e("geek", "onLoadFailed");
//                        }
//
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            Log.e("geek", "onResourceReady");
//                            JShareInterface.share("Wechat",
//                                    WeixinBeanParam.share_web2(
//                                            title,
//                                            name,
//                                            url2,
//                                            resource),
//                                    jPushShareUtils.mShareListener1);
////                            JShareInterface.share(Wechat.Name,
////                                    WeixinBeanParam.share_web2(
////                                            title,
////                                            name,
////                                            url2,
////                                            resource),
////                                    jPushShareUtils.mShareListener1);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//                            Log.e("geek", "onLoadCleared");
//                        }
//
//                        @Override
//                        public void getSize(@NonNull SizeReadyCallback cb) {
//                            Log.e("geek", "getSize");
//                            cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//                        }
//
//                        @Override
//                        public void removeCallback(@NonNull SizeReadyCallback cb) {
//                            Log.e("geek", "removeCallback");
//                        }
//
//                        @Override
//                        public void setRequest(@Nullable Request request) {
//                            Log.e("geek", "setRequest");
//                        }
//
//                        @Nullable
//                        @Override
//                        public Request getRequest() {
//                            return null;
//                        }
//
//                        @Override
//                        public void onStart() {
//                            Log.e("geek", "onStart");
//                        }
//
//                        @Override
//                        public void onStop() {
//                            Log.e("geek", "onStop");
//                        }
//
//                        @Override
//                        public void onDestroy() {
//                            Log.e("geek", "onDestroy");
//                        }
//                    });
//        }
//
////        WeixinBeanParam.set_xcx(activity, title, name, "", url1, url2);
////        dismiss();
//
//    }
//
//    public void set_wechat2(final String title, final String name, final String imgUrl, final String url1, final String url2) {
////        Glide.with(activity).asBitmap().load(imgUrl).into(new SimpleTarget<Bitmap>() {
////            @Override
////            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
////                JShareInterface.share(WechatMoments.Name,
////                        WeixinBeanParam.share_web2(
////                                title,
////                                name,
////                                url2,
////                                resource),
////                        jPushShareUtils.mShareListener1);
////            }
////        });
//        //
//        Glide.with(activity).asBitmap().load(imgUrl)
//                .apply(options)
//                .into(new Target<Bitmap>() {
//                    @Override
//                    public void onLoadStarted(@Nullable Drawable placeholder) {
//
//                    }
//
//                    @Override
//                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
//
//                    }
//
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        JShareInterface.share("WechatMoments",
//                                WeixinBeanParam.share_web2(
//                                        title,
//                                        name,
//                                        url2,
//                                        resource),
//                                jPushShareUtils.mShareListener1);
////                        JShareInterface.share(WechatMoments.Name,
////                                WeixinBeanParam.share_web2(
////                                        title,
////                                        name,
////                                        url2,
////                                        resource),
////                                jPushShareUtils.mShareListener1);
//                    }
//
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                    }
//
//                    @Override
//                    public void getSize(@NonNull SizeReadyCallback cb) {
//                        cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//                    }
//
//                    @Override
//                    public void removeCallback(@NonNull SizeReadyCallback cb) {
//
//                    }
//
//                    @Override
//                    public void setRequest(@Nullable Request request) {
//
//                    }
//
//                    @Nullable
//                    @Override
//                    public Request getRequest() {
//                        return null;
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onStop() {
//
//                    }
//
//                    @Override
//                    public void onDestroy() {
//
//                    }
//                });
//
//    }
//
//    @Override
//    public void onResults(String platform, String toastMsg, String data) {
//        ToastUtils.showLong(toastMsg);
//        jPushShareUtils.ondes();
//        dismiss();
//    }
//
//    private OnFinishResultClickListener mListener;
//
//    public interface OnFinishResultClickListener {
//        void onWeChat1();
//
//        void onWeChat2();
//    }
//
//    // APP_ID 替换为你的应用从官方网站申请到的合法appID
////    private static final String APP_ID = "wx211138e4dcf53523";
//
//    public static void set_xcx(Context context, final String title, final String name, String imgUrl, String url1, final String url2) {
//        //调用api接口，发送数据到微信
//        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        final IWXAPI api = WXAPIFactory.createWXAPI(context, JPushShareUtils.APP_ID, true);
//        // 将应用的appId注册到微信
//        api.registerApp(JPushShareUtils.APP_ID);
////        api.sendReq(req);
//
//        //
//        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
////        miniProgram.webpageUrl=url1;//自定义
//        miniProgram.webpageUrl = "http://www.qq.com";//自定义
//        miniProgram.userName = "gh_1d24d5825541";//小程序端提供参数
////        miniProgram.path = "/pages/readPicbook?bookItemId=" + imgUrl;//小程序端提供参数
//        miniProgram.path = url1;//小程序端提供参数
//        final WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
//        mediaMessage.title = title;//自定义
//        mediaMessage.description = name;//自定义
//        //
////        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.slb_logo_comm);
////        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
////        bitmap.recycle();
//        //
//        Glide.with(context).asBitmap().load(imgUrl).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                mediaMessage.thumbData = WeixinBeanParam.bmpToByteArray(resource, false);
//                SendMessageToWX.Req req2 = new SendMessageToWX.Req();
//                req2.transaction = "";
//                req2.scene = SendMessageToWX.Req.WXSceneSession;
//                req2.message = mediaMessage;
//                api.sendReq(req2);
//            }
//        });
//
//    }
//
//
//    protected void hideBottomUIMenu() {
//        //隐藏底部导航栏
//        final View decorView = activity.getWindow().getDecorView();
//        final int uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
//
//        decorView.setSystemUiVisibility(uiOption);
//
//        // This code will always hide the navigation bar
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
//                    decorView.setSystemUiVisibility(uiOption);
//                }
//            }
//        });
//    }
//
//    /**
//     * Desc: 获取虚拟按键高度 放到工具类里面直接调用即可
//     */
//    public int getNavigationBarHeight(Context context) {
//        int result = 0;
//        if (hasNavBar(context)) {
//            Resources res = context.getResources();
//            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
//            if (resourceId > 0) {
//                result = res.getDimensionPixelSize(resourceId);
//            }
//        }
//        MyLogUtil.e("虚拟键盘高度" + result);
//        return result;
//    }
//
//    /**
//     * 检查是否存在虚拟按键栏
//     *
//     * @param context
//     * @return
//     */
//    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//    public boolean hasNavBar(Context context) {
//        Resources res = context.getResources();
//        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
//        if (resourceId != 0) {
//            boolean hasNav = res.getBoolean(resourceId);
//            // check override flag
//            String sNavBarOverride = getNavBarOverride();
//            if ("1".equals(sNavBarOverride)) {
//                hasNav = false;
//            } else if ("0".equals(sNavBarOverride)) {
//                hasNav = true;
//            }
//            return hasNav;
//        } else { // fallback
//            return !ViewConfiguration.get(context).hasPermanentMenuKey();
//        }
//    }
//
//    /**
//     * 判断虚拟按键栏是否重写
//     *
//     * @return
//     */
//    private String getNavBarOverride() {
//        String sNavBarOverride = null;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            try {
//                Class c = Class.forName("android.os.SystemProperties");
//                Method m = c.getDeclaredMethod("get", String.class);
//                m.setAccessible(true);
//                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
//            } catch (Throwable e) {
//            }
//        }
//        return sNavBarOverride;
//    }
//
//    /**
//     * 复制内容到剪切板
//     *
//     * @param copyStr
//     * @return
//     */
//    private boolean copy(Context context, String copyStr) {
//        try {
//            //获取剪贴板管理器
//            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//            // 创建普通字符型ClipData
//            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
//            // 将ClipData内容放到系统剪贴板里。
//            cm.setPrimaryClip(mClipData);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//}
