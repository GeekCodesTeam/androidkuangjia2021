package com.haier.cellarette.baselibrary.jianpan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;

public class JianpanAct extends AppCompatActivity {

    public Button btnChangeStyle;
    public Button btnChangeMode;
    public SplitEditTextView splitEdit1;
    public SplitEditTextView splitEdit2;
    public SplitEditTextView splitEdit3;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jianpan);
        btnChangeStyle = findViewById(R.id.btnChangeStyle);
        btnChangeMode = findViewById(R.id.btnChangeMode);
        splitEdit1 = findViewById(R.id.splitEdit1);
        splitEdit2 = findViewById(R.id.splitEdit2);
        splitEdit3 = findViewById(R.id.splitEdit3);
        btnChangeStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (splitEdit1.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_CONNECT) {
                    splitEdit1.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE);
                } else if (splitEdit1.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_SINGLE) {
                    splitEdit1.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_CONNECT);
                } else if (splitEdit1.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE) {
                    splitEdit1.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_SINGLE);
                }
                //
                if (splitEdit2.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_CONNECT) {
                    splitEdit2.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE);
                } else if (splitEdit2.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_SINGLE) {
                    splitEdit2.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_CONNECT);
                } else if (splitEdit2.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE) {
                    splitEdit2.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_SINGLE);
                }
                //
                if (splitEdit3.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_CONNECT) {
                    splitEdit3.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE);
                } else if (splitEdit3.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_SINGLE) {
                    splitEdit3.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_CONNECT);
                } else if (splitEdit3.getInputBoxStyle() == SplitEditTextView.INPUT_BOX_STYLE_UNDERLINE) {
                    splitEdit3.setInputBoxStyle(SplitEditTextView.INPUT_BOX_STYLE_SINGLE);
                }
            }
        });
        btnChangeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (splitEdit1.getContentShowMode() == SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD) {
                    splitEdit1.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_TEXT);
                } else {
                    splitEdit1.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD);
                }
                if (splitEdit2.getContentShowMode() == SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD) {
                    splitEdit2.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_TEXT);
                } else {
                    splitEdit2.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD);
                }
                if (splitEdit3.getContentShowMode() == SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD) {
                    splitEdit3.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_TEXT);
                } else {
                    splitEdit3.setContentShowMode(SplitEditTextView.CONTENT_SHOW_MODE_PASSWORD);
                }
            }
        });
        //
        splitEdit1.setOnInputListener(new OnInputListener() {
            @Override
            public void onInputFinished(String content) {
                ToastUtils.showLong(content);
            }

            @Override
            public void onInputChanged(String text) {
                super.onInputChanged(text);

            }
        });

    }
}
