package com.lxj.xpopupdemo.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.SimpleCallback;
import com.lxj.xpopup.util.XPermission;
import com.lxj.xpopupdemo.XpopupDemoActivity;
import com.lxj.xpopupdemo.XpopupMainActivity;
import com.lxj.xpopupdemo.R;
import com.lxj.xpopupdemo.custom.CustomAttachPopup;
import com.lxj.xpopupdemo.custom.CustomAttachPopup2;
import com.lxj.xpopupdemo.custom.CustomBubbleAttachPopup;
import com.lxj.xpopupdemo.custom.CustomDrawerPopupView;
import com.lxj.xpopupdemo.custom.CustomEditTextBottomPopup;
import com.lxj.xpopupdemo.custom.CustomFullScreenPopup;
import com.lxj.xpopupdemo.custom.CustomHorizontalBubbleAttachPopup;
import com.lxj.xpopupdemo.custom.ListDrawerPopupView;
import com.lxj.xpopupdemo.custom.PagerBottomPopup;
import com.lxj.xpopupdemo.custom.PagerDrawerPopup;
import com.lxj.xpopupdemo.custom.QQMsgPopup;
import com.lxj.xpopupdemo.custom.ZhihuCommentPopup;

/**
 * Description:
 * Create by lxj, at 2018/12/11
 */
public class QuickStartDemo extends BaseFragment implements View.OnClickListener {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_quickstart;
    }

    @Override
    public void init(final View view) {
        view.findViewById(R.id.btnShowConfirm).setOnClickListener(this);
        view.findViewById(R.id.btnBindLayout).setOnClickListener(this);
        view.findViewById(R.id.btnShowPosition1).setOnClickListener(this);
        view.findViewById(R.id.btnShowPosition2).setOnClickListener(this);
        view.findViewById(R.id.btnShowInputConfirm).setOnClickListener(this);
        view.findViewById(R.id.btnShowCenterList).setOnClickListener(this);
        view.findViewById(R.id.btnShowCenterListWithCheck).setOnClickListener(this);
        view.findViewById(R.id.btnShowLoading).setOnClickListener(this);
        view.findViewById(R.id.btnShowBottomList).setOnClickListener(this);
        view.findViewById(R.id.btnShowBottomListWithCheck).setOnClickListener(this);
        view.findViewById(R.id.btnShowDrawerLeft).setOnClickListener(this);
        view.findViewById(R.id.btnShowDrawerRight).setOnClickListener(this);
        view.findViewById(R.id.btnCustomBottomPopup).setOnClickListener(this);
        view.findViewById(R.id.btnPagerBottomPopup).setOnClickListener(this);
        view.findViewById(R.id.btnCustomEditPopup).setOnClickListener(this);
        view.findViewById(R.id.btnFullScreenPopup).setOnClickListener(this);
        view.findViewById(R.id.btnAttachPopup1).setOnClickListener(this);
        view.findViewById(R.id.btnAttachPopup2).setOnClickListener(this);
        view.findViewById(R.id.tv1).setOnClickListener(this);
        view.findViewById(R.id.tv2).setOnClickListener(this);
        view.findViewById(R.id.tv3).setOnClickListener(this);
        view.findViewById(R.id.btnMultiPopup).setOnClickListener(this);
        view.findViewById(R.id.btnShowInBackground).setOnClickListener(this);
        view.findViewById(R.id.btnBubbleAttachPopup1).setOnClickListener(this);
        view.findViewById(R.id.btnBubbleAttachPopup2).setOnClickListener(this);

        // ??????????????????????????????????????????????????????View?????????
        final XPopup.Builder builder = new XPopup.Builder(getContext())
//                .isCenterHorizontal(true)
                .watchView(view.findViewById(R.id.btnShowAttachPoint));
        view.findViewById(R.id.btnShowAttachPoint).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                XPopup.fixLongClick(v);//????????????????????????????????????View????????????
                builder.asAttachList(new String[]{"??????11", "??????", "??????", "????????????????????????"
                        }, null,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                toast("click " + text);
                            }
                        })
                        .show();
                return true;
            }
        });

        drawerPopupView = new CustomDrawerPopupView(getContext());
    }

    CustomDrawerPopupView drawerPopupView;
    AttachPopupView attachPopupView;
    BasePopupView popupView;
    LoadingPopupView loadingPopup;
    CustomAttachPopup2 customAttach2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id = v.getId();//???????????????View???Attach????????????
        if (id == R.id.btnShowConfirm) { //?????????????????????????????????
            /*if(popupView==null)*/
            popupView = new XPopup.Builder(getContext())
//                        .hasNavigationBar(false)
                    .isDestroyOnDismiss(true)
//                        .navigationBarColor(Color.BLUE)
//                        .hasBlurBg(true)
//                         .dismissOnTouchOutside(false)
//                         .autoDismiss(false)
//                        .popupAnimation(PopupAnimation.NoAnimation)
//                        .isLightStatusBar(true)
//                        .setPopupCallback(new DemoXPopupListener())
//                        .asCustom(new LoginPopup(getContext()));
                    .asConfirm("??????", "????????????????????????????????????????????????????????????????????????",
                            "??????", "??????",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    ToastUtils.showShort("click confirm");
                                }
                            }, null, false);
            popupView.show();
        } else if (id == R.id.btnBindLayout) {  //????????????????????????????????????XPopup?????????????????????
            new XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asConfirm("????????????????????????", "?????????????????????????????????????????????XPopup?????????????????????????????????????????????????????????????????????????????????\n" +
                                    "???????????????????????????????????????????????????Id?????????XPopup?????????View???\n????????????????????????Id??????????????????[????????????]?????????",
                            "??????", "XPopup??????",
                            new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    toast("click confirm");
                                }
                            }, null, false, R.layout.my_confim_popup) //????????????????????????????????????
                    .show();
        } else if (id == R.id.btnShowInputConfirm) { //?????????????????????????????????????????????
            new XPopup.Builder(getContext())
                    .hasStatusBarShadow(false)
                    //.dismissOnBackPressed(false)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .autoOpenSoftInput(true)
                    .isDarkTheme(true)
                    .setPopupCallback(new DemoXPopupListener())
//                        .autoFocusEditText(false) //?????????????????????EditText??????????????????????????????true
                    //.moveUpToKeyboard(false)   //??????????????????????????????????????????true
                    .asInputConfirm("????????????", null, null, "????????????Hint??????",
                            new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
//                                new XPopup.Builder(getContext()).asLoading().show();
                                }
                            })
                    .show();
        } else if (id == R.id.btnShowCenterList) { //??????????????????List????????????
            new XPopup.Builder(getContext())
//                        .maxWidth(600)
                    .maxHeight(800)
                    .isDarkTheme(true)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asCenterList("???????????????", new String[]{"??????1", "??????2", "??????3", "??????4", "??????1", "??????2", "??????3", "??????4",
                                    "??????1", "??????2", "??????3", "??????4", "??????1", "??????2", "??????3", "??????4",
                                    "??????1", "??????2", "??????3", "??????4", "??????1", "??????2", "??????3", "??????4",
                                    "??????1", "??????2", "??????3", "??????4", "??????1", "??????2", "??????3", "??????4",
                                    "??????1", "??????2", "??????3", "??????4", "??????1", "??????2", "??????3", "??????4",},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            })
//                        .bindLayout(R.layout.my_custom_attach_popup) //???????????????
                    .show();
        } else if (id == R.id.btnShowCenterListWithCheck) { //??????????????????List??????????????????????????????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asCenterList("???????????????", new String[]{"??????1", "??????2", "??????3", "??????4"},
                            null, 1,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            })
                    .show();
        } else if (id == R.id.btnShowLoading) { //??????????????????Loading?????????
            if (loadingPopup == null) {
                loadingPopup = (LoadingPopupView) new XPopup.Builder(getContext())
                        .dismissOnBackPressed(false)
                        .asLoading("?????????")
                        .show();
            } else {
                loadingPopup.show();
            }
            loadingPopup.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingPopup.setTitle("????????????????????????");
                    loadingPopup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingPopup.setTitle("");
                        }
                    }, 2000);
                }
            }, 2000);
//                loadingPopup.smartDismiss();
//                loadingPopup.dismiss();
            loadingPopup.delayDismissWith(6000, new Runnable() {
                @Override
                public void run() {
                    toast("?????????????????????");
                }
            });
        } else if (id == R.id.btnShowBottomList) { //????????????????????????????????????????????????
            new XPopup.Builder(getContext())
                    .isDarkTheme(true)
                    .hasShadowBg(true)
//                            .hasBlurBg(true)
//                            .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asBottomList("???????????????", new String[]{"??????1", "??????2", "??????3", "??????4", "??????5", "??????6", "??????7"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            }).show();
        } else if (id == R.id.btnShowBottomListWithCheck) { //????????????????????????????????????????????????,???????????????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asBottomList("??????????????????", new String[]{"??????1", "??????2", "??????3", "??????4", "??????5"},
                            null, 2,
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            })
                    .show();
        } else if (id == R.id.btnCustomBottomPopup) { //????????????????????????
            new XPopup.Builder(getContext())
                    .moveUpToKeyboard(false) //????????????????????????????????????????????????????????????
                    .enableDrag(true)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
//                        .isThreeDrag(true) //???????????????????????????????????????enableDrag(false)?????????
                    .asCustom(new ZhihuCommentPopup(getContext())/*.enableDrag(false)*/)
                    .show();
        } else if (id == R.id.btnPagerBottomPopup) { //????????????????????????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .isViewMode(true)
                    .asCustom(new PagerBottomPopup(getContext()))
                    .show();
        } else if (id == R.id.tv1 || id == R.id.tv2 || id == R.id.tv3) {
            AttachPopupView attachPopupView = new XPopup.Builder(getContext())
                    .hasShadowBg(false)
                    .isClickThrough(true)
//                            .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
//                        .isDarkTheme(true)
//                        .popupAnimation(PopupAnimation.ScrollAlphaFromTop) //NoAnimation??????????????????
//                        .isCenterHorizontal(true) //?????????????????????????????????
//                        .offsetY(60)
//                        .offsetX(80)
//                        .popupPosition(PopupPosition.Top) //???????????????????????????
//                        .popupWidth(500)
                    .atView(v)  // ?????????????????????View???????????????????????????????????????????????????
                    .asAttachList(new String[]{"??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????icon??????icon", "??????????????????",
//                                        "??????", "??????", "??????icon", "??????",
//                                        "??????", "??????", "??????icon", "??????",
//                                        "??????", "??????", "??????icon", "??????"
                            },
                            null,
//                                new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    toast("click " + text);
                                }
                            }, 0, 0/*, Gravity.LEFT*/);
            ;
            attachPopupView.show();
        } else if (id == R.id.btnAttachPopup1) { //???????????????Attach???????????????????????????????????????????????????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
//                        .offsetX(50) //??????10
//                        .offsetY(10)  //????????????10
//                        .popupPosition(PopupPosition.Right) //???????????????????????????????????????
                    .hasShadowBg(false) // ?????????????????????
                    .atView(v)
                    .asCustom(new CustomAttachPopup(getContext()))
                    .show();
        } else if (id == R.id.btnAttachPopup2) {/*if(customAttach2==null)*/
            customAttach2 = new CustomAttachPopup2(getContext());
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .atView(v)
                    .isViewMode(true)
                    .hasShadowBg(false) // ?????????????????????
                    .asCustom(customAttach2)
                    .show();
        } else if (id == R.id.btnBubbleAttachPopup1) { //???????????????????????????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .atView(v)
                    .hasShadowBg(false) // ?????????????????????
                    .asCustom(new CustomHorizontalBubbleAttachPopup(getContext()))
                    .show();
        } else if (id == R.id.btnBubbleAttachPopup2) { //???????????????????????????
            new XPopup.Builder(getContext())
//                        .isCenterHorizontal(true)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .atView(v)
                    .hasShadowBg(false) // ?????????????????????
                    .asCustom(new CustomBubbleAttachPopup(getContext())
//                                .setBubbleBgColor(Color.RED)
//                                .setArrowWidth(XPopupUtils.dp2px(getContext(), 20))
//                                .setArrowHeight(XPopupUtils.dp2px(getContext(), 20))
//                                .setBubbleRadius(100)
                    )
                    .show();
        } else if (id == R.id.btnShowDrawerLeft) { //???DrawerLayout?????????Drawer??????
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
//                        .asCustom(new CustomDrawerPopupView(getContext()))
//                        .hasShadowBg(false)
                    .isViewMode(true) //?????????Fragment???????????????View??????
                    .asCustom(new PagerDrawerPopup(getContext()))
//                        .asCustom(new ListDrawerPopupView(getContext()))
                    .show();
        } else if (id == R.id.btnShowDrawerRight) {
            new XPopup.Builder(getContext())
                    .dismissOnTouchOutside(false)
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .popupPosition(PopupPosition.Right)//??????
                    .hasStatusBarShadow(true) //?????????????????????
                    .asCustom(new ListDrawerPopupView(getContext()))
                    .show();
        } else if (id == R.id.btnFullScreenPopup) { //???????????????????????????Activity
            new XPopup.Builder(getContext())
//                        .hasStatusBar(false)
//                        .hasStatusBarShadow(false)
//                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .autoOpenSoftInput(true)
                    .asCustom(new CustomFullScreenPopup(getContext()))
                    .show();
        } else if (id == R.id.btnCustomEditPopup) { //????????????????????????????????????Bottom??????
            new XPopup.Builder(getContext())
                    .autoOpenSoftInput(true)
                    //.isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .asCustom(new CustomEditTextBottomPopup(getContext()))
                    .show();
        } else if (id == R.id.btnShowPosition1) {
            new XPopup.Builder(getContext())
//                        .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .offsetY(300)
                    .offsetX(-100)
                    .popupAnimation(PopupAnimation.TranslateFromLeft)
                    .asCustom(new QQMsgPopup(getContext()))
                    .show();
        } else if (id == R.id.btnShowPosition2) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                    .isCenterHorizontal(true)
                    .offsetY(200)
                    .asCustom(new QQMsgPopup(getContext()))
                    .show();
        } else if (id == R.id.btnMultiPopup) {
            startActivity(new Intent(getContext(), XpopupDemoActivity.class));
        } else if (id == R.id.btnShowInBackground) {//?????????????????????
            XPopup.requestOverlayPermission(getContext(), new XPermission.SimpleCallback() {
                @Override
                public void onGranted() {
                    ToastUtils.showShort("??????2????????????XPopup?????????");
                    ActivityUtils.startHomeActivity();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new XPopup.Builder(getContext())
                                    .isDestroyOnDismiss(true) //???????????????????????????????????????????????????
                                    .enableShowWhenAppBackground(true)  //???????????????????????????
                                    .asConfirm("XPopup??????", "XPopup??????????????????????????????", new OnConfirmListener() {
                                        @Override
                                        public void onConfirm() {
                                            startActivity(new Intent(getContext(), XpopupMainActivity.class));
                                        }
                                    }).show();
                        }
                    }, 2000);
                }

                @Override
                public void onDenied() {
                    ToastUtils.showShort("??????????????????????????????????????????");
                }
            });
        }
    }


    static class DemoXPopupListener extends SimpleCallback {
        @Override
        public void onCreated(BasePopupView pv) {
            Log.e("tag", "onCreated");
        }

        @Override
        public void onShow(BasePopupView popupView) {
            Log.e("tag", "onShow");
        }

        @Override
        public void onDismiss(BasePopupView popupView) {
            Log.e("tag", "onDismiss");
        }

        @Override
        public void beforeDismiss(BasePopupView popupView) {
            Log.e("tag", "beforeDismiss");
        }

        //???????????????????????????????????????????????????????????????????????????true??????
        @Override
        public boolean onBackPressed(BasePopupView popupView) {
            Log.e("tag", "????????????????????????????????????XPopup???????????????");
            ToastUtils.showShort("onBackPressed??????true???????????????????????????????????????XPopup???????????????");
            return true;
        }

        @Override
        public void onKeyBoardStateChanged(BasePopupView popupView, int height) {
            super.onKeyBoardStateChanged(popupView, height);
            Log.e("tag", "onKeyBoardStateChanged height: " + height);
        }
    }
}
