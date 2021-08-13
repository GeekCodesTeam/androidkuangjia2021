package com.fosung.lighthouse.jiaoyuziyuan.widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.lxj.xpopup.core.BottomPopupView;
import com.zcolin.frame.util.ScreenUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EduUserInfoPopupView extends BottomPopupView {

    private Context context;

    public EduUserInfoPopupView(@NonNull @NotNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.view_user_info_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvUserName = findViewById(R.id.tv_user_name);
        tvUserName.setText(CommonModel.Companion.get().getUserInfo().name);
        TextView tvOrgName = findViewById(R.id.tv_org_name);
        tvOrgName.setText((Objects.requireNonNull(CommonModel.Companion.get().getRolerBean())).manageName);
        findViewById(R.id.tv_close).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EduUserInfoPopupView.this.dismiss();
            }
        });
    }

    @Override
    protected int getMaxHeight() {
        return (int) (ScreenUtil.getWindowHeight((Activity) context) * 0.8);
    }


}
