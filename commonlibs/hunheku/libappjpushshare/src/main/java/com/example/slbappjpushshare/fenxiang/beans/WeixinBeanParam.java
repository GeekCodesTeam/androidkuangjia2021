package com.example.slbappjpushshare.fenxiang.beans;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import cn.jiguang.share.android.api.Platform;
import cn.jiguang.share.android.api.ShareParams;

public class WeixinBeanParam {

    /**
     * 分享文本
     */
    public static ShareParams share_wenben() {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_TEXT);
        shareParams.setText("Text");//必须
        return shareParams;
    }

    /**
     * 分享图片1 路径
     */
    public static ShareParams share_img1(String img_url) {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_IMAGE);
        shareParams.setImagePath(img_url);
        return shareParams;
    }

    /**
     * 分享图片2 bitmap
     */
    public static ShareParams share_img2(Bitmap bitmap) {
        ShareParams shareParams = new ShareParams();
        shareParams.setShareType(Platform.SHARE_IMAGE);
        shareParams.setImageData(bitmap);
        return shareParams;
    }

    /**
     * 分享音乐
     */
    public static ShareParams share_music(String share_title, String share_text, String url, String music_url, String fileurl) {
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(share_title);
        shareParams.setText(share_text);
        shareParams.setShareType(Platform.SHARE_MUSIC);
        shareParams.setUrl(url);
        shareParams.setMusicUrl(music_url);
        shareParams.setImagePath(fileurl);
        return shareParams;
    }

    /**
     * 分享视频
     */
    public static ShareParams share_video(String share_title, String share_text, String url, String share_videourl, String fileurl) {
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(share_title);
        shareParams.setText(share_text);
        shareParams.setShareType(Platform.SHARE_VIDEO);
        shareParams.setUrl(share_videourl);
        shareParams.setImagePath(fileurl);
        return shareParams;
    }

    /**
     * 分享网页
     */
    public static ShareParams share_web(String share_title, String share_text, String url, String fileurl) {
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(share_title);
        shareParams.setText(share_text);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(url);//必须
        shareParams.setImagePath(fileurl);
        return shareParams;
    }

    /**
     * 分享网页
     */
    public static ShareParams share_web2(String share_title, String share_text, String url, Bitmap fileurl) {
        ShareParams shareParams = new ShareParams();
        shareParams.setTitle(share_title);
        shareParams.setText(share_text);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        shareParams.setUrl(url);//必须
        shareParams.setImageData(fileurl);
        return shareParams;
    }

    // APP_ID 替换为你的应用从官方网站申请到的合法appID
//    private static final String APP_ID = "wx211138e4dcf53523";

//    public static void set_xcx(Context context, final String title, final String name, String imgUrl, String url1, final String url2) {
////        //初始化一个WXWebpageObject，填写url
////        WXWebpageObject webpage = new WXWebpageObject();
////        webpage.webpageUrl = url1;
////
////        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
////        WXMediaMessage msg = new WXMediaMessage(webpage);
////        msg.title = title;
////        msg.description = name;
////        Bitmap thumbBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.slb_logo_comm);
////        msg.thumbData = bmpToByteArray(thumbBmp, true);
////
////        //构造一个Req
////        SendMessageToWX.Req req = new SendMessageToWX.Req();
//////        req.transaction = buildTransaction("webpage");
////        req.transaction = "";
////        req.message = msg;
////        req.scene = SendMessageToWX.Req.WXSceneSession ;
//////        req.userOpenId = getOpenId();
////
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
//                mediaMessage.thumbData = bmpToByteArray(resource, false);
//                SendMessageToWX.Req req2 = new SendMessageToWX.Req();
//                req2.transaction = "";
//                req2.scene = SendMessageToWX.Req.WXSceneSession;
//                req2.message = mediaMessage;
//                api.sendReq(req2);
//            }
//        });
//        //
////        RequestOptions  options = new RequestOptions()
//////                .signature(new ObjectKey(UUID.randomUUID().toString()))  // 重点在这行
////                .skipMemoryCache(false)
////                .dontAnimate()
////                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                .placeholder(R.drawable.slb_logo_comm)
////                .error(R.drawable.slb_logo_comm)
////                .fallback(R.drawable.slb_logo_comm); //url为空的时候,显示的图片;
////        Glide.with(context).asBitmap().load(imgUrl)
////                .apply(options)
////                .into(new Target<Bitmap>() {
////            @Override
////            public void onLoadStarted(@Nullable Drawable placeholder) {
////
////            }
////
////            @Override
////            public void onLoadFailed(@Nullable Drawable errorDrawable) {
////
////            }
////
////            @Override
////            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
////                mediaMessage.thumbData = bmpToByteArray(resource, true);
////            }
////
////            @Override
////            public void onLoadCleared(@Nullable Drawable placeholder) {
////
////            }
////
////            @Override
////            public void getSize(@NonNull SizeReadyCallback cb) {
////
////            }
////
////            @Override
////            public void removeCallback(@NonNull SizeReadyCallback cb) {
////
////            }
////
////            @Override
////            public void setRequest(@Nullable Request request) {
////
////            }
////
////            @Nullable
////            @Override
////            public Request getRequest() {
////                return null;
////            }
////
////            @Override
////            public void onStart() {
////
////            }
////
////            @Override
////            public void onStop() {
////
////            }
////
////            @Override
////            public void onDestroy() {
////
////            }
////        });
//
//    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
