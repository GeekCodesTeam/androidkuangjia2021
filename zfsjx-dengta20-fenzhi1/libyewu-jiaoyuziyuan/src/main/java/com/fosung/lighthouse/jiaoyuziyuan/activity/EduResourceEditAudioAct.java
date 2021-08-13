package com.fosung.lighthouse.jiaoyuziyuan.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.fosung.eduapi.bean.EduResourceDetailBean;
import com.fosung.eduapi.presenter.EduResDetailPresenter;
import com.fosung.eduapi.view.EduResDetailView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.jiaoyuziyuan.adapter.EduResourceDetailPagerAdapter;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailExtendInfoFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailInfoFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailRecordFragment;
import com.fosung.lighthouse.jiaoyuziyuan.fragment.EduDetailReviewFragment;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.AudioControlView;
import com.fosung.lighthouse.jiaoyuziyuan.widgets.CZBottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.haier.cellarette.baselibrary.toasts3.utils.MSizeUtils;
import com.hjq.toast.ToastUtils;
import com.zcolin.frame.app.ActivityParam;
import com.zcolin.frame.app.BaseActivity;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.frame.util.ScreenUtil;
import com.zcolin.gui.webview.ZWebView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Objects;

import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.player.VideoView;

/**
 * @author fosung
 */
@ActivityParam(isImmerse = false)
public class EduResourceEditAudioAct extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_resources_edit_audio);
        setToolbarTitle("编辑");
        initView();
    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {

    }
}