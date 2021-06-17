package com.example.libbase.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.libbase.netstate.NetState;
import com.example.libbase.netstate.NetconListener;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.BaseViewHelper;

public abstract class SlbBaseFragment extends Fragment implements NetconListener {

    private long mCurrentMs = System.currentTimeMillis();
    protected NetState netState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        setup(rootView, savedInstanceState);
        netState = new NetState();
        netState.setNetStateListener(this, getActivity());
        return rootView;
    }

    protected abstract int getLayoutId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 动态改变每个act的头部
//        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.blue357CD4), 0);
//        StatusBarUtil.setLightMode(getActivity());
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

    }


    public abstract class OnMultiClickListener implements View.OnClickListener {
        // 两次点击按钮之间的点击间隔不能少于1000毫秒
        private final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onMultiClick(View v);

        @Override
        public void onClick(View v) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                // 超过点击间隔后再将lastClickTime重置为当前点击时间
                lastClickTime = curClickTime;
                onMultiClick(v);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobEvent.onEventStart(getActivity(), EventIdConst.PAGE_LOAD_DURATION, TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobEvent.onEventEnd(getActivity(), EventIdConst.PAGE_LOAD_DURATION, TAG);
    }

    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void net_con_none() {

    }

    @Override
    public void net_con_success() {
    }

    @Override
    public void showNetPopup() {
    }

    protected <T extends View> T f(View rootView, @IdRes int resId) {
        return BaseViewHelper.f(rootView, resId);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(getActivity(), activity));
    }

    public void startActivity(String action) {
        Intent intent = new Intent(action);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(getActivity(), activity), requestCode);
    }

    public void startActivityForResult(String action, int requestCode) {
        Intent intent = new Intent(action);
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }



    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //登录和未登录成功状态
        if (SlbLoginUtil.get().login_activity_result(requestCode, resultCode, data)) {

            return;
        }
        onActResult(requestCode, resultCode, data);
    }

    public void onActResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onDestroy() {
        if (netState != null) {
            netState.unregisterReceiver();
        }
        super.onDestroy();
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }

}
