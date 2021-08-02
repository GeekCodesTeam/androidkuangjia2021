package com.haier.cellarette.libretrofit.common;

import android.app.Application;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.MyLogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitNetNew {
    private static RetrofitNetNew sInstance;

    private static Retrofit retrofit;
    private OkHttpClient client;

    public Retrofit init() {
        //添加一个log拦截器,打印所有的log
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        //可以设置请求过滤的水平,body,basic,headers
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(Utils.getApp().getExternalFilesDir(null).getAbsolutePath(), "RetrofitNetNewCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小
        client = new OkHttpClient
                .Builder()
                .addInterceptor(addQueryParameterInterceptor())  //参数添加
                .addInterceptor(addHeaderInterceptor()) // token过滤
                .addInterceptor(new LoggingInterceptor()) //日志,所有的请求响应度看到 LoggingInterceptor
                .cache(cache)  //添加缓存
                .connectTimeout(10l, TimeUnit.SECONDS)
                .readTimeout(10l, TimeUnit.SECONDS)
                .writeTimeout(10l, TimeUnit.SECONDS)
                .build();

//        client.dispatcher().runningCalls().get(0).request().tag()

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

    private RetrofitNetNew() {
        init();
    }

    public static void config() {
        sInstance = new RetrofitNetNew();
    }

    public static RetrofitNetNew getInstance() {
        return sInstance;
    }

    /**
     * 获取网络框架
     *
     * @return
     */
    public Retrofit get() {
        return retrofit;
    }

    /**
     * 创建一个业务请求 <br />
     *
     * @param convertClass 业务请求接口的class
     * @return
     */
    public static <T> T build(Class<T> convertClass, Object tag) {
        return getInstance().get().create(convertClass);
//        return (T) Proxy.newProxyInstance(convertClass.getClassLoader(),
//                new Class<?>[] {convertClass}, new Handler(tag));
    }


    public void cancelAll() {
        client.dispatcher().cancelAll();

    }

    public void cancel(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 设置公共参数
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
//                        .addQueryParameter("phoneSystem", "")
//                        .addQueryParameter("liveClientType", BanbenUtils.getInstance().getLiveClientType())
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    /**
     * 设置头
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                String accessSecret = SPUtils.getInstance().getString("accessSecret", "aaaa");
                String timer = System.currentTimeMillis() + "";
                String accessKey = "V1" + timer + numcode + accessSecret;
                String MD5 = EncryptUtils.encryptMD5ToString(accessKey) + "";
//                String MD5 = EncryptUtils.encryptMD5ToString("123456") + "";
//                MyLogUtil.e("ssssssssss", MD5);
                MyLogUtil.e("ssssssssss", MD5.toUpperCase() + "");
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
//                        .header("token", (String) SpUtils.get("token", ""))
//                        .header("hxAppVersion", BanbenUtils.getInstance().getVersion())
//                        .header("liveClientType", BanbenUtils.getInstance().getLiveClientType())
                        .header("imei", BanbenUtils.getInstance().getImei())
                        .header("platform", SPUtils.getInstance().getString("平台类型", "android_phone"))
                        .header("token", SPUtils.getInstance().getString("用户token", ""))
                        .header("model", DeviceUtils.getManufacturer())
                        .header("version", AppUtils.getAppVersionName())
                        .header("version_code", AppUtils.getAppVersionCode() + "")
                        .header("X-CA-KEY", accessSecret)
                        .header("X-CA-SIGNATURE", MD5.toUpperCase() + "")
                        .header("X-CA-TIMESTAMP", System.currentTimeMillis() + "")
                        .header("X-CA-NONCE", (int) ((Math.random() * 9 + 1) * 100000) + "")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }


    private static Application sInstanceApp;

    private static Application getApp() {
        if (sInstanceApp == null) {
            Application app = null;
            try {
                app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (app == null) {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                }
            } catch (final Exception e) {
                try {
                    app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                } catch (final Exception ex) {
                    e.printStackTrace();
                }
            } finally {
                sInstanceApp = app;
            }
        }

        return sInstanceApp;
    }

}
