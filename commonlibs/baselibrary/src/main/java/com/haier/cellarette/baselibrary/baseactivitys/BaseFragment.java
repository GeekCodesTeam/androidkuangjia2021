package com.haier.cellarette.baselibrary.baseactivitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.geek.libutils.app.BaseApp;
import com.geek.libutils.app.BaseViewHelper;


public abstract class BaseFragment extends Fragment {

    private long mCurrentMs = System.currentTimeMillis();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        setup(rootView, savedInstanceState);
//        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.color_568EC0));
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
//        HookUtil.hookClick(this);

//        AnnotateUtils.injectViews(getActivity());
//        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(BaseApp.get(), R.color.color_BEDFFF));
//        StatusBarUtilV7.immersive(getActivity(), ContextCompat.getColor(getActivity(), R.color.color_343533), 1.0f);// color_E5F7FF
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

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
        if (intent.resolveActivity(BaseApp.get().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void startActivityForResult(Class<? extends Activity> activity, int requestCode) {
        startActivityForResult(new Intent(getActivity(), activity), requestCode);
    }

    public void startActivityForResult(String action, int requestCode) {
        Intent intent = new Intent(action);
        if (intent.resolveActivity(BaseApp.get().getPackageManager()) != null) {
            startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param activity
     */
    public void targetToIfLogin(final Class<? extends BaseActivity> activity) {
//        UserUtils.get().loginToDo(getActivity(), new Runnable() {
//            @Override
//            public void run() {
//                startActivity(activity);
//            }
//        });
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param intent
     */
    public void targetToIfLogin(final Intent intent) {
//        UserUtils.get().loginToDo(getActivity(), new Runnable() {
//            @Override
//            public void run() {
//                startActivity(intent);
//            }
//        });
    }

    public void targetToIfLogin(final String action) {
//        UserUtils.get().loginToDo(getActivity(), new Runnable() {
//            @Override
//            public void run() {
//                startActivity(action);
//            }
//        });
    }

    /**
     * 跳转到指定activity，如果未登录，则弹出登录窗口
     *
     * @param intent
     */
    public void targetToForResultIfLogin(final Intent intent, final int requestCode) {
//        UserUtils.get().loginToDo(getActivity(), new Runnable() {
//            @Override
//            public void run() {
//                startActivityForResult(intent, requestCode);
//            }
//        });
    }

    public void targetToForResultIfLogin(final String action, final int requestCode) {
//        UserUtils.get().loginToDo(getActivity(), new Runnable() {
//            @Override
//            public void run() {
//                startActivityForResult(action, requestCode);
//            }
//        });
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (UserUtils.get().onActivityResult(requestCode, resultCode, data)) {
//            onUserLogined(UserUtils.get().userId());
//            return;
//        }

        onActResult(requestCode, resultCode, data);
    }

    public void onUserLogined(String userId) {
    }

    public void onActResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onDestroy() {
//        ShowLoadingUtil.onDestory();
        super.onDestroy();
    }

    public String getIdentifier() {
        return getClass().getName() + mCurrentMs;
    }
}
