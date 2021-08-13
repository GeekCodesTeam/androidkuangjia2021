package com.haier.cellarette.libretrofit.common;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {
    private final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String body = null;
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            assert charset != null;
            body = buffer.readString(charset);
        }
//        MyLogUtil.d("HTTP_LOG", String.format("发送请求\nmethod：%s\nurl：%s\nheaders: %sbody：%s", request.method(), request.url(), request.headers(), body));
        long startNs = System.nanoTime();
        Response response = chain.proceed(request);
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        ResponseBody responseBody = response.body();
        String rBody = null;
        if (HttpHeaders.hasBody(response)) {
            assert responseBody != null;
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            assert charset != null;
            rBody = buffer.clone().readString(charset);
        }
//        MyLogUtil.d("HTTP_LOG", String.format("收到响应 %s%s %ss\n请求url：%s\n请求body：%s\n响应body：%s", response.code(), response.message(), tookMs, response.request().url(), body, rBody));
        Log.d("HTTP_LOG", "                                              ");
        Log.d("HTTP_LOG", "---------------HTTP_LOG请求start--------------");
//        MyLogUtil.d("HTTP_LOG", String.format("发送请求\nmethod：%s\nurl：%s\nheaders: %sbody：%s", request.method(), request.url(), request.headers(), body));
        Log.d("HTTP_LOG", "发送请求");
        Log.d("HTTP_LOG", String.format("method：%s", request.method()));
        Log.d("HTTP_LOG", String.format("url：%s", request.url()));
        Log.d("HTTP_LOG", String.format("请求headers: %s", request.headers()));
        Log.d("HTTP_LOG", String.format("请求body: %s", body));
//        MyLogUtil.d("HTTP_LOG", String.format("收到响应 %s%s %ss\n响应body：%s", response.code(), response.message(), tookMs, rBody));
        Log.d("HTTP_LOG", String.format("收到响应 %s %s %ss", response.code(), response.message(), tookMs));
        Log.d("HTTP_LOG", String.format("响应body：%s", rBody));
        Log.d("HTTP_LOG", "---------------HTTP_LOG请求end----------------");
        Log.d("HTTP_LOG", "                                              ");
        try {
            Gson gson = new Gson();
            ResponseSlbBean responseSlbBean = gson.fromJson(rBody, ResponseSlbBean.class);
            if (null != responseSlbBean && responseSlbBean.getCode() == 2000) {
                set_token_out();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return response;
    }

    private void set_token_out() {
        EventBus.getDefault().post("登录失效，请重新登录！");
     /*   MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_KEY1);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_SEX);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_IMG);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TEL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_NAME);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_forceLogin);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_TOKEN);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_NAME);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_TEL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_DETAIL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_ALL);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_SHENG);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_SHI);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_ADDRESS_QU);
        MmkvUtils.getInstance().remove_common(CommonUtils.MMKV_CLASS);*/
    }

    private void prntResponseLog(Context ctx) {
//        if (!isDebug) { return;}
//
//        StringBuilder builder = new StringBuilder();
//        builder.append("================== Glin Start Response ==================\n\n");
//
//        if (ctx.getResult().isCache()) {
//            builder.append(" is cache-> true \n")
//                    .append(" use cache-> ")
//                    .append(ctx.getCall().getUrl())
//                    .append("\n");
//        } else {
//            builder.append(" is cache-> false \n");
//            builder.append(" status code-> ").append(ctx.getRawResult().getStatusCode()).append("\n");
//            builder.append(" message-> ").append(ctx.getRawResult().getMessage()).append("\n");
//            builder.append(" response-> ").append(ctx.getRawResult().getResponse()).append("\n");
//
//            if (ctx.getCall().shouldCache()) {
//                builder.append(" cache result-> ").append(ctx.getCall().getUrl()).append("\n");
//            }
//        }

//        builder.append("\n================== Glin  End  Response ==================");

//        MyLogUtil.d("Glin", builder.toString());

    }

}

