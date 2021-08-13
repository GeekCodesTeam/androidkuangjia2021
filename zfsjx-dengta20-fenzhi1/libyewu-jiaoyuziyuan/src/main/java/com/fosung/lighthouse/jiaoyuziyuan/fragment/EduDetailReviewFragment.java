package com.fosung.lighthouse.jiaoyuziyuan.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fosung.eduapi.bean.EduResourceExamTypeBean;
import com.fosung.eduapi.presenter.EduResAuditSubmitPresenter;
import com.fosung.eduapi.presenter.EduResCancelLockPresenter;
import com.fosung.eduapi.presenter.EduResExamTypePresenter;
import com.fosung.eduapi.presenter.EduResLockAuditPresenter;
import com.fosung.eduapi.view.EduResAuditSubmitView;
import com.fosung.eduapi.view.EduResCancelLockView;
import com.fosung.eduapi.view.EduResExamTypeView;
import com.fosung.eduapi.view.EduResLockAuditView;
import com.fosung.lighthouse.jiaoyuziyuan.R;
import com.fosung.lighthouse.manage.common.utils.CommonModel;
import com.haier.cellarette.baselibrary.loading.ShowLoadingUtil;
import com.haier.cellarette.libretrofit.common.ResponseSlbBean1;
import com.hjq.toast.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.tencent.openqq.protocol.imsdk.msg;
import com.zcolin.frame.app.BaseFragment;
import com.zcolin.frame.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EduDetailReviewFragment extends BaseFragment implements EduResExamTypeView, EduResLockAuditView, EduResCancelLockView,EduResAuditSubmitView, View.OnClickListener{

    private static final String ARG_RED_ID = "resourceId";
    private static final String ARG_LOCK = "lockStatus";
    private static final String ARG_AUDIT_ID = "auditId";
    private static final String ARG_SIGN = "sign";
    private String resourceId;
    private String auditId;
    private String assigneeStatus;
    private String sign;
    private boolean lockStatus;

    private EduResExamTypePresenter eduResExamTypePresenter;
    private EduResLockAuditPresenter eduResLockAuditPresenter;
    private EduResCancelLockPresenter eduResCancelLockPresenter;
    private EduResAuditSubmitPresenter eduResAuditSubmitPresenter;

    private TextView tv_sh_name;
    private TextView tv_sh_yes_name;
    private TextView tv_sh_no_name;

    private LinearLayout ll_sh_yes;
    private LinearLayout ll_sh_no;

    private RadioButton rb_yes;
    private RadioButton rb_no;

    private RadioButton btn_no_c;
    private RadioButton btn_no_edit;
    private RadioButton btn_no_disable;

    private RadioButton btn_sh_yes;
    private RadioButton btn_sh_yes_finish;

    private LinearLayout ll_suggestion;
    private TextView tv_sug_name;
    private EditText et_sug;

    private LinearLayout ll_sh_result;

    private RadioGroup rg_sh_result;
    private ImageView iv_sh_dialog;
    private ImageView iv_sh_dialog_no;
    private String shDialogTitle;
    private String shNoDialogTitle;

    private TextView tv_status;
    private TextView tv_submit;

    private String status;
    private String auditStatus;
    private String resourceGrade;
    private LoadingPopupView loadingPopup;

    private List<RadioButton> yesList = new ArrayList<>();
    private List<EduResourceExamTypeBean.DatalistBean.ChildBean> yesDatas = new ArrayList<>();

    private List<RadioButton> noList = new ArrayList<>();
    private List<EduResourceExamTypeBean.DatalistBean.ChildBean> noDatas = new ArrayList<>();

    private List<EduResourceExamTypeBean.DatalistBean.ChildBean> shResultDatas = new ArrayList<>();
    private int [] shIds = {R.id.tv_a, R.id.tv_b, R.id.tv_c, R.id.tv_d, R.id.tv_e};
    private int [] shRIds = {R.id.rb_a, R.id.rb_b, R.id.rb_c, R.id.rb_d, R.id.rb_e};

    @SuppressWarnings("unused")
    public static EduDetailReviewFragment newInstance(String resourceId,String auditId, String lockStatus, String sign) {
        EduDetailReviewFragment fragment = new EduDetailReviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_RED_ID, resourceId);
        args.putString(ARG_AUDIT_ID, auditId);
        args.putString(ARG_LOCK, lockStatus);
        args.putString(ARG_SIGN, sign);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            resourceId = getArguments().getString(ARG_RED_ID);
            auditId = getArguments().getString(ARG_AUDIT_ID);
            assigneeStatus = getArguments().getString(ARG_LOCK);
            sign = getArguments().getString(ARG_SIGN);
        }

        eduResExamTypePresenter = new EduResExamTypePresenter();
        eduResExamTypePresenter.onCreate(this);

        eduResLockAuditPresenter = new EduResLockAuditPresenter();
        eduResLockAuditPresenter.onCreate(this);

        eduResCancelLockPresenter = new EduResCancelLockPresenter();
        eduResCancelLockPresenter.onCreate(this);

        eduResAuditSubmitPresenter = new EduResAuditSubmitPresenter();
        eduResAuditSubmitPresenter.onCreate(this);
    }

    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
        eduResExamTypePresenter.getEduResExamTypeRequest(resourceId);
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_edu_detail_review;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        tv_sh_name = getView(R.id.tv_sh_name);
        tv_sh_yes_name = getView(R.id.tv_sh_yes_name);
        tv_sh_no_name = getView(R.id.tv_sh_no_name);
        rb_yes = getView(R.id.rb_yes);
        rb_no = getView(R.id.rb_no);

        ll_sh_yes = getView(R.id.ll_sh_yes);
        ll_sh_no = getView(R.id.ll_sh_no);

        rg_sh_result = getView(R.id.rg_sh_result);

        btn_no_c = getView(R.id.btn_no_c);
        btn_no_edit = getView(R.id.btn_no_edit);
        btn_no_disable = getView(R.id.btn_no_disable);
        noList.add(btn_no_c);
        noList.add(btn_no_edit);
        noList.add(btn_no_disable);

        btn_sh_yes = getView(R.id.btn_sh_yes);
        btn_sh_yes_finish = getView(R.id.btn_sh_yes_finish);

        yesList.add(btn_sh_yes);
        yesList.add(btn_sh_yes_finish);

        ll_suggestion = getView(R.id.ll_suggestion);
        tv_sug_name = getView(R.id.tv_sug_name);
        et_sug = getView(R.id.et_sug);

        ll_sh_result = getView(R.id.ll_sh_result);

        iv_sh_dialog = getView(R.id.iv_sh_dialog);
        iv_sh_dialog.setOnClickListener(this);

        iv_sh_dialog_no = getView(R.id.iv_sh_dialog_no);
        iv_sh_dialog_no.setOnClickListener(this);

        tv_submit = getView(R.id.tv_submit);
        tv_submit.setOnClickListener(this);

        tv_status = getView(R.id.tv_status);
        if ("CLAIMED".equals(assigneeStatus) && sign.equals(CommonModel.Companion.get().getUserInfo().userId)) {
            tv_status.setText("取消锁定");
            lockStatus = true;
        } else {
            tv_status.setText("锁定审核");
            lockStatus = false;
        }
        tv_status.setOnClickListener(this);

        rg_sh_result.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_yes) {
                    status = "PASS";
                    if (yesDatas.size() > 0) {
                        ll_sh_yes.setVisibility(View.VISIBLE);
                    } else {
                        ll_sh_yes.setVisibility(View.GONE);
                    }
                    ll_sh_no.setVisibility(View.GONE);
                } else {
                    status = "REJECT";
                    ll_sh_yes.setVisibility(View.GONE);
                    if (noDatas.size() > 0) {
                        ll_sh_no.setVisibility(View.VISIBLE);
                    } else {
                        ll_sh_no.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void OnEduResExamTypeSuccess(EduResourceExamTypeBean bean) {
        for (EduResourceExamTypeBean.DatalistBean datalistBean : bean.getDatalist()) {
            if ("audit".equals(datalistBean.getKey())) {
                tv_sh_name.setText(datalistBean.getName());
            } else if ("PASS".equals(datalistBean.getKey())) {
                tv_sh_yes_name.setText(datalistBean.getName());
                if ("false".equals(datalistBean.getDisplay())) {
                    rb_yes.setVisibility(View.GONE);
                    tv_sh_yes_name.setVisibility(View.GONE);
                } else {
                    rb_yes.setVisibility(View.VISIBLE);
                    status = datalistBean.getId();
                    tv_sh_yes_name.setVisibility(View.VISIBLE);
                    if (yesList.size() <= datalistBean.getChild().size()) {
                        for (int i = 0; i < yesList.size(); i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean = datalistBean.getChild().get(i);
                            RadioButton radioButton = yesList.get(i);
                            radioButton.setText(childBean.getName());
                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                            } else {
                                if (i == 0) {
                                    auditStatus = childBean.getId();
                                }
                                radioButton.setVisibility(View.VISIBLE);
                                yesDatas.add(childBean);
                                shDialogTitle = (shDialogTitle == null ? "" : shDialogTitle + "\n")  + childBean.getRemark();
                            }
                        }
                    } else {
                        for (int i = 0; i < datalistBean.getChild().size(); i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean = datalistBean.getChild().get(i);
                            RadioButton radioButton = yesList.get(i);
                            radioButton.setText(childBean.getName());
                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                            } else {
                                if (i == 0) {
                                    auditStatus = childBean.getId();
                                }
                                radioButton.setVisibility(View.VISIBLE);
                                yesDatas.add(childBean);
                                shDialogTitle = (shDialogTitle == null ? "" : shDialogTitle + "\n")  + childBean.getRemark();
                            }
                        }
                    }
                }

                if (datalistBean.getChild().size() > 0) {
                    ll_sh_yes.setVisibility(View.VISIBLE);
                } else {
                    ll_sh_yes.setVisibility(View.GONE);
                }

            } else if ("REJECT".equals(datalistBean.getKey())) {
                tv_sh_no_name.setText(datalistBean.getName());
                if ("false".equals(datalistBean.getDisplay())) {
                    rb_no.setVisibility(View.GONE);
                    tv_sh_no_name.setVisibility(View.GONE);
                } else {
                    rb_no.setVisibility(View.VISIBLE);
                    tv_sh_no_name.setVisibility(View.VISIBLE);
                    if (noList.size() <= datalistBean.getChild().size()) {
                        for (int i = 0; i < noList.size(); i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean = datalistBean.getChild().get(i);
                            RadioButton radioButton = noList.get(i);
                            radioButton.setText(childBean.getName());
                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                            } else {
                                radioButton.setVisibility(View.VISIBLE);
                                noDatas.add(childBean);
                                shNoDialogTitle = (shNoDialogTitle == null ? "" : shNoDialogTitle + "\n") + childBean.getRemark();
                            }
                        }
                    } else {
                        for (int i = 0; i < datalistBean.getChild().size(); i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean = datalistBean.getChild().get(i);
                            RadioButton radioButton = noList.get(i);
                            radioButton.setText(childBean.getName());
                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                            } else {
                                radioButton.setVisibility(View.VISIBLE);
                                noDatas.add(childBean);
                                shNoDialogTitle = (shNoDialogTitle == null ? "" : shNoDialogTitle + "\n") + childBean.getRemark();
                            }
                        }
                    }
                }

//                if (datalistBean.getChild().size() > 0) {
//                    ll_sh_no.setVisibility(View.VISIBLE);
//                } else {
//                    ll_sh_no.setVisibility(View.GONE);
//                }

            } else if ("suggestion".equals(datalistBean.getKey())) {
                if ("false".equals(datalistBean.getDisplay())) {
                    ll_suggestion.setVisibility(View.GONE);
                } else {
                    ll_suggestion.setVisibility(View.VISIBLE);
                }
                tv_sug_name.setText(datalistBean.getName());
                et_sug.setHint("请输入" + datalistBean.getName());
            } else if ("resourceGrade".equals(datalistBean.getKey())) {
                if ("false".equals(datalistBean.getDisplay())) {
                    ll_sh_result.setVisibility(View.GONE);
                } else {
                    ll_sh_result.setVisibility(View.VISIBLE);
                    if (shIds.length <= datalistBean.getChild().size()) {
                        for (int i = 0; i < shIds.length; i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean =  datalistBean.getChild().get(i);
                            int shId = shIds[i];
                            int shRId = shRIds[i];

                            RadioButton radioButton = getView(shRId);
                            TextView textView = getView(shId);

                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                            } else {
                                resourceGrade = childBean.getId();
                                radioButton.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(childBean.getName());
                                shResultDatas.add(childBean);
                            }
                        }
                    } else {
                        for (int i = 0; i < datalistBean.getChild().size(); i++) {
                            EduResourceExamTypeBean.DatalistBean.ChildBean childBean =  datalistBean.getChild().get(i);
                            int shId = shIds[i];
                            int shRId = shRIds[i];

                            RadioButton radioButton = getView(shRId);
                            TextView textView = getView(shId);

                            if ("false".equals(childBean.getDisplay())) {
                                radioButton.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                            } else {
                                resourceGrade = childBean.getId();
                                radioButton.setVisibility(View.VISIBLE);
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(childBean.getName());
                                shResultDatas.add(childBean);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void OnEduResExamTypeFail(String msg) {
        ToastUtil.toastShort(msg);
    }


    @Override
    public void OnEduResLockAuditSuccess(ResponseSlbBean1 bean) {
        loadingPopup.dismiss();
        ToastUtils.show("锁定成功");
        lockStatus = true;
        tv_status.setText("取消锁定");
    }

    @Override
    public void OnEduResLockAuditFail(String msg) {
        loadingPopup.dismiss();
        ToastUtils.show(msg);
    }

    @Override
    public void OnEduResCancelLockSuccess(ResponseSlbBean1 bean) {
        loadingPopup.dismiss();
        ToastUtils.show("取消成功");
        lockStatus = false;
        tv_status.setText("锁定审核");
    }

    @Override
    public void OnEduResCancelLockFail(String msg) {
        loadingPopup.dismiss();
        ToastUtils.show(msg);
    }


    @Override
    public void OnEduResAuditSubmitSuccess(ResponseSlbBean1 bean) {
        loadingPopup.dismiss();
        ToastUtils.show("提交成功");
    }

    @Override
    public void OnEduResAuditSubmitFail(String msg) {
        loadingPopup.dismiss();
        ToastUtils.show(msg);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_sh_dialog) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .asConfirm("提醒", shDialogTitle,
                            null, "确定",
                            null, null, true)
                    .show();
        } else if(view.getId() == R.id.iv_sh_dialog_no) {
            new XPopup.Builder(getContext())
                    .isDestroyOnDismiss(true)
                    .asConfirm("提醒", shNoDialogTitle,
                            null, "确定",
                            null, null, true)
                    .show();
        } else if(view.getId() == R.id.tv_status) {
            getPopupView().show();
            if (lockStatus) {
                eduResCancelLockPresenter.cancelLockEduResRequest(
                        auditId,
                        CommonModel.Companion.get().getUserInfo().name,
                        CommonModel.Companion.get().getUserInfo().userId);
            } else {
                eduResLockAuditPresenter.lockAuditEduResRequest(
                        auditId,
                        CommonModel.Companion.get().getUserInfo().name,
                        CommonModel.Companion.get().getUserInfo().userId);
            }
        } else if(view.getId() == R.id.tv_submit) {
            getPopupView().show();
            eduResAuditSubmitPresenter.submitAuditEduResRequest(
                    CommonModel.Companion.get().getUserInfo().name,
                    CommonModel.Companion.get().getUserInfo().userId,
                    status, auditStatus, et_sug.getText().toString(),
                    resourceGrade,
                    "",
                    "",
                    auditId,
                    resourceId);
        }
    }

    private LoadingPopupView getPopupView() {
        if (loadingPopup == null){
            loadingPopup = (LoadingPopupView) new XPopup.Builder(getContext())
                    .dismissOnBackPressed(false)
                    .asLoading("加载中");
        }
        return loadingPopup;
    }
}