package com.just.agentweb.agentwebview.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.R;

import org.json.JSONObject;

public class JsWebActDemo extends BaseActWebActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_js_webview;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        if (mAgentWeb != null) {
            //注入对象
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, activity));
        }
        findViewById(R.id.callJsNoParamsButton).setOnClickListener(mOnClickListener);
        findViewById(R.id.callJsOneParamsButton).setOnClickListener(mOnClickListener);
        findViewById(R.id.callJsMoreParamsButton).setOnClickListener(mOnClickListener);
        findViewById(R.id.jsJavaCommunicationButton).setOnClickListener(mOnClickListener);
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) this.findViewById(R.id.ll_base_container);
    }

    @Nullable
    @Override
    public String getUrl() {
        String url = "file:///android_asset/html/hello.html";
//        String url = "http://lzzhdj.com.cn:94/#/login";
//        try {
//            HiosHelper.resolveAd(JsWebActDemo.this, JsWebActDemo.this, UrlEncodeUtils.encodeUrl(url));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        return url;
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.callJsNoParamsButton) {
                mAgentWeb.getJsAccessEntrace().quickCallJs("activitySetting", "0");
            } else if (id == R.id.callJsOneParamsButton) {
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidParam", "Hello ! Agentweb");
            } else if (id == R.id.callJsMoreParamsButton) {
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidMoreParams", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.i("Info", "value:" + value);
                    }
                }, getJson(), "msg2", "msg3");
            } else if (id == R.id.jsJavaCommunicationButton) {
                mAgentWeb.getJsAccessEntrace().quickCallJs("callByAndroidInteraction", "你好Js");
            }
        }
    };

    private String getJson() {
        String result = "";
        try {
            JSONObject mJSONObject = new JSONObject();
            mJSONObject.put("id", 1);
            mJSONObject.put("name", "Agentweb");
            mJSONObject.put("age", 18);
            result = mJSONObject.toString();
        } catch (Exception e) {

        }
        return result;
    }

    public class AndroidInterface {
        private AgentWeb agent;
        private Context context;

        public AndroidInterface(AgentWeb agent, Context context) {
            this.agent = agent;
            this.context = context;
        }

        @JavascriptInterface
        public void BackToAndroid(final String str) {
            ToastUtils.showShort("调用了android方法" + str);
        }
    }
}
