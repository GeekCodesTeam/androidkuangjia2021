package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.BaseFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.http.entity.NoticeListReply;
import com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext.RichTextView;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class CenterInfoFragment extends BaseFragment {

    private String[] requestTag = new String[1];
    private TextView centerTitle;
    private RichTextView centerContentRichTextView;
    private Handler handler = new Handler();

    @Override
    protected int getRootViewLayId() {

        return R.layout.fragment_center_info;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        centerTitle = getView(R.id.center_title);
        centerContentRichTextView = getView(R.id.center_content_rich);
        centerContentRichTextView.setMovementMethod(LinkMovementMethod.getInstance());//需要添加这句，否则点击图片无反应。

        requestResources();

    }

    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
    }

    private void requestResources() {

        Map<String, String> map = new HashMap<String, String>();

        requestTag[0] = XCLHttp.postJson(XCLHttpUrlMaster.CENTERINFO, map, new ZResponse<NoticeListReply>(NoticeListReply.class) {

            @Override
            public void onSuccess(Response response, final NoticeListReply resObj) {
                try {
                    if (resObj.datalist != null && resObj != null && !resObj.datalist.isEmpty()) {
                        centerTitle.setText(resObj.datalist.get(0).name);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                centerContentRichTextView.setRichText(resObj.datalist.get(0).content);//需要延时调用

                            }
                        }, 10);
                    } else {
                        Toast.makeText(mActivity, "暂无数据", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    @Override
    public void onDestroy() {
        XCLHttp.cancelRequest(requestTag);
        centerContentRichTextView.onViewDetachedFromWindow(null);
        super.onDestroy();
    }
}
