package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;

import android.os.Handler;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext.OnRichImageClickListener;
import com.fosung.xuanchuanlan.xuanchuanlan.widget.richtext.RichTextView;

import java.util.List;

@ActivityParam(isShowToolBar = false)
public class XCLNoticeDetailActivity extends BaseActivity {

    private static final String TAG = "XCLNoticeDetailActivity";
    private TextView noticeTitle;
    private TextView noticeContent;
    private RichTextView noticeContentRichTextView;
    private boolean isRichContent = true;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isRichContent = getIntent().getBooleanExtra("isRichContent",true);
        String title = getIntent().getStringExtra("title");
        final String content = getIntent().getStringExtra("content");

//        final String content = "<p style=\"text-align: center; margin-top: 5px; margin-bottom: 5px; line-height: normal; text-indent: 2em;\">begin<img src=\"http://s.dtdjzx.gov.cn/cloud/oss/download/edu_resource_test/20191025/3375791148034430787.png\" title=\"\" alt=\"\"/>end<br/></p><p style=\"text-indent: 37px; text-align: left;\"><span style=\";font-family:仿宋;font-size:19px\">";
        setContentView(R.layout.activity_xcl_notice_detail);
        noticeTitle = (TextView) findViewById(R.id.notice_title);
        noticeContent = (TextView) findViewById(R.id.notice_content);
        noticeContentRichTextView = (RichTextView)findViewById(R.id.notice_content_rich);


        noticeTitle.setText(title);

        noticeContent.setVisibility(isRichContent? View.GONE:View.VISIBLE);
        noticeContentRichTextView.setVisibility(isRichContent? View.VISIBLE:View.GONE);
        noticeContentRichTextView.setOnImageClickListener(new OnRichImageClickListener() {
            @Override
            public void onImageClick(List<String> imageUrls, int position) {
                Log.i(TAG,"点击富文本图片 position：" + position + " url：" + imageUrls.get(position));
            }
        });
        noticeContentRichTextView.setMovementMethod(LinkMovementMethod.getInstance());//需要添加这句，否则点击图片无反应。
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                noticeContentRichTextView.setRichText(content);//需要延时调用

            }
        },10);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noticeContentRichTextView.onViewDetachedFromWindow(null);
    }
}
