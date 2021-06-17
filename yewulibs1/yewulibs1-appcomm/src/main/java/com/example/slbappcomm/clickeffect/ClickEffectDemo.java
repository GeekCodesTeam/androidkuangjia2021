package com.example.slbappcomm.clickeffect;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.example.slbappcomm.R;
import com.example.libbase.agentwebview.activity.BaseActWebActivity;

public class ClickEffectDemo extends BaseActWebActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_click_effect;
    }
    private TextView click5;
    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        click5=findViewById(R.id.click5);
        click5.getPaint().setFlags(click5.getPaint().getFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        click5.setText("￥" + "65.00");
        findViewById(R.id.click1).setOnClickListener(mOnClickListener);
        findViewById(R.id.click2).setOnClickListener(mOnClickListener);
        findViewById(R.id.click3).setOnClickListener(mOnClickListener);
        findViewById(R.id.click4).setOnClickListener(mOnClickListener);
    }

    @Override
    protected ViewGroup getAgentWebParent() {
        return null;
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.click1) {
                ToastUtils.showShort("点击效果1");
            } else if (id == R.id.click2) {
                ToastUtils.showShort("点击效果2");
            } else if (id == R.id.click3) {
            } else if (id == R.id.click4) {
            }
        }
    };
}
