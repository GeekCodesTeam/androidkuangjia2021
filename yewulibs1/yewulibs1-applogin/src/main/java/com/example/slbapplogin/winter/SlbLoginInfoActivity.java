package com.example.slbapplogin.winter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.libbase.base.SlbBaseActivity;
import com.example.slbapplogin.R;
import com.geek.libutils.SlbLoginUtil;
import com.geek.libutils.app.BaseAppManager;
import com.geek.libutils.etc.DateUtil;
import com.haier.cellarette.baselibrary.jianpan.AutoHideInputMethodFrameLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.aigestudio.wheelpicker.WheelPicker.ALIGN_CENTER;

public class SlbLoginInfoActivity extends SlbBaseActivity implements View.OnClickListener/*, SUpdateUserInfoView, SGetUserInfoView, SConfigureView*/ {

    private AutoHideInputMethodFrameLayout afl1;
    private LinearLayout ll_wheel2;
    private WheelDatePicker wheelData;
    private DatePicker datepicker1;
    private int year0;
    private int monthOfYear0;
    private int dayOfMonth0;
    private Button btn_cancel1;
    private Button btn_sure1;
    private ImageView iv1;
    private TextView tvInfo2;
    private TextView tv_birthday2;
    private Date tv_birthday2date;
    private LinearLayout rl1;
    private EditText edt1;
    private LinearLayout ll22;
    private TextView tvBoy2;
    private TextView tvGirl2;
    private Button tv1;

    private String is_boyOrgirl;// 1 :nan 2:nv

    // 上传
//    private SUpdateUserInfoPresenter presenter1;
    // 获取
//    private SGetUserInfoPresenter presenter2;
//    private SConfigurePresenter sConfigurePresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_slblogininfo;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        donetwork();
        //
        MobclickAgent.onEvent(this, "SlbLoginInfoActivity");
    }

    private void donetwork() {
//        if (TextUtils.equals("1", SPUtils.getInstance().getString(CommonUtils.USER_FORCE_CHILD))) {
//            iv1.setVisibility(View.INVISIBLE);
//        } else {
//            iv1.setVisibility(View.VISIBLE);
//        }

        tv_birthday2date = new Date();
        //
        Calendar calendar = Calendar.getInstance();
        year0 = calendar.get(Calendar.YEAR);
        monthOfYear0 = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth0 = calendar.get(Calendar.DAY_OF_MONTH);

        datepicker1.setMaxDate(System.currentTimeMillis());
        datepicker1.setMinDate(631123200000l);// 315504000000 1980
        datepicker1.updateDate(year0, monthOfYear0, dayOfMonth0);
        //
        datepicker1.init(1980, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                year0 = year;
                monthOfYear0 = monthOfYear;
                dayOfMonth0 = dayOfMonth;
                Calendar calendar = Calendar.getInstance();
                calendar.set(year0, monthOfYear0, dayOfMonth0);
                tv_birthday2date = calendar.getTime();
//                tv_birthday2.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
            }
        });

//        presenter1 = new SUpdateUserInfoPresenter();
//        presenter1.onCreate(this);
//        presenter2 = new SGetUserInfoPresenter();
//        presenter2.onCreate(this);
//        presenter2.getGetUserInfoData();

//        sConfigurePresenter = new SConfigurePresenter();
//        sConfigurePresenter.onCreate(this);

    }

    private void set_boy() {
        is_boyOrgirl = "1";
        select_useful(tvBoy2, R.drawable.baby_boy22);
        select_useful(tvGirl2, R.drawable.baby_girl2);
    }

    private void set_girl() {
        is_boyOrgirl = "2";
        select_useful(tvGirl2, R.drawable.baby_girl22);
        select_useful(tvBoy2, R.drawable.baby_boy2);
    }

    private void select_useful(TextView tv, int drawabless) {
        Drawable drawable = getResources().getDrawable(drawabless);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                drawable.getMinimumHeight());
        tv.setCompoundDrawables(null, drawable, null, null);
    }

    private void onclick() {
        iv1.setOnClickListener(this);
        ll22.setOnClickListener(this);
        tv_birthday2.setOnClickListener(this);
        btn_cancel1.setOnClickListener(this);
        btn_sure1.setOnClickListener(this);
        tvBoy2.setOnClickListener(this);
        tvGirl2.setOnClickListener(this);
        tv1.setOnClickListener(this);
        wheelData.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {
                int i = picker.getId();
                if (i == R.id.main_wheel_default) {
                    String aaa = DateUtil.format(date, DateUtil.FORMATER_YMD_CN);
                    tv_birthday2.setText(aaa);
                    tv_birthday2date = date;
                } else {
                }
            }
        });

    }

    private void findview() {
        afl1 = findViewById(R.id.afl1);
        iv1 = findViewById(R.id.iv1);
        tvInfo2 = findViewById(R.id.tv_info2);
        tvInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheelData.setVisibility(View.VISIBLE);
            }
        });
        tv_birthday2 = findViewById(R.id.tv_birthday2);
        rl1 = findViewById(R.id.rl1);
        edt1 = findViewById(R.id.edt1);
        ll22 = findViewById(R.id.ll22);
        tvBoy2 = findViewById(R.id.tv_boy2);
        tvGirl2 = findViewById(R.id.tv_girl2);
        tv1 = findViewById(R.id.tv1);

        ll_wheel2 = findViewById(R.id.ll_wheel2);
        ll_wheel2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        btn_cancel1 = findViewById(R.id.btn_cancel1);
        btn_sure1 = findViewById(R.id.btn_sure1);
        datepicker1 = findViewById(R.id.datepicker1);
        wheelData = findViewById(R.id.main_wheel_default);
        //setting
        wheelData.setItemTextSize(100);
        wheelData.setItemTextColor(ContextCompat.getColor(this, R.color.color_E6F1FF));
        wheelData.setSelectedItemTextColor(ContextCompat.getColor(this, R.color.white));
        wheelData.setItemSpace(40);
//        wheelData.setIndicator(true);
//        wheelData.setIndicatorSize(20);
//        wheelData.setIndicatorColor(ContextCompat.getColor(this, R.color.transparent));

        wheelData.setItemAlignYear(ALIGN_CENTER);
        wheelData.setItemAlignMonth(ALIGN_CENTER);
        wheelData.setItemAlignDay(ALIGN_CENTER);
        wheelData.setCyclic(true);
        wheelData.setCurved(true);
        wheelData.setCurtain(false);
        wheelData.setAtmospheric(true);

        wheelData.getTextViewYear().setText("年");
        wheelData.getTextViewYear().setTextSize(20);
        wheelData.getTextViewMonth().setText("月");
        wheelData.getTextViewMonth().setTextSize(20);
        wheelData.getTextViewDay().setText("日");
        wheelData.getTextViewDay().setTextSize(20);

        List<String> datanew = new ArrayList<>();
//        datanew = Arrays.asList(getResources().getStringArray(R.array.WheelArrayYear));
        int years = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1980; i <= years; i++) {
            datanew.add(i + "");
        }
        wheelData.getWheelYearPicker().setYearFrame(1980, years);
//        wheelData.getWheelMonthPicker().setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayMonth)));
//        wheelData.getWheelDayPicker().setData(Arrays.asList(getResources().getStringArray(R.array.WheelArrayDay)));

    }

    @Override
    public void finish() {
        //
        if (BaseAppManager.getInstance().getAll().size() == 1) {
            startActivity(new Intent("hs.act.slbapp.index"));
        }
        super.finish();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv1) {
            MobclickAgent.onEvent(this, "babyinfo_close_button");
            finish();
        } else if (i == R.id.tv1) {
            // 确定bufen
            MobclickAgent.onEvent(this, "babyinfo_ok_button");
            set_jiekou();
        } else if (i == R.id.btn_cancel1) {
            // 取消bufen
            if (ll_wheel2.getVisibility() == View.VISIBLE) {
                ll_wheel2.setVisibility(View.GONE);
            }
        } else if (i == R.id.btn_sure1) {
            // 确定bufen
            if (ll_wheel2.getVisibility() == View.VISIBLE) {
                ll_wheel2.setVisibility(View.GONE);
            }
            tv_birthday2.setText(year0 + "年" + (monthOfYear0 + 1) + "月" + dayOfMonth0 + "日");
        } else if (i == R.id.tv_birthday2) {
            // 日期bufen
            if (ll_wheel2.getVisibility() == View.GONE) {
                ll_wheel2.setVisibility(View.VISIBLE);
            }
            //

        } else if (i == R.id.tv_boy2) {
            // 男孩bufen
            set_boy();
        } else if (i == R.id.tv_girl2) {
            // 女孩bufen
            set_girl();
        } else {

        }
    }

    private boolean is_yanzheng() {
        if (TextUtils.isEmpty(edt1.getText().toString().trim())) {
            ToastUtils.showLong("请输入宝宝小名");
            return false;
        }
        if (TextUtils.isEmpty(tv_birthday2.getText().toString().trim())) {
            ToastUtils.showLong("请选择出生日期");
            return false;
        }
        return true;
    }

    private void onLoginSuccess() {
        setResult(SlbLoginUtil.LOGIN_RESULT_OK);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ll_wheel2.getVisibility() == View.VISIBLE) {
                ll_wheel2.setVisibility(View.GONE);
            } else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
//        presenter1.onDestory();
//        presenter2.onDestory();
//        sConfigurePresenter.onDestory();
        super.onDestroy();

    }

    /**
     * ----------------------------------下面为业务逻辑--分割线------------------------------
     */

    // 提交数据bufen
    private void set_jiekou() {
        if (!is_yanzheng()) {
            return;
        }
        String a = edt1.getText().toString().trim();
//        String b = tv_birthday2.getText().toString().trim();
        String b = TimeUtils.date2Millis(tv_birthday2date) + "";
        String c = is_boyOrgirl;
        // 接口成功后继续
//        presenter1.getUpdateUserInfoData2(b, c, a);

    }
//
//    // 上传数据bufen
//    @Override
//    public void OnUpdateUserInfoSuccess(String s) {
//        SPUtils.getInstance().put(CommonUtils.MMKV_NAME, edt1.getText().toString().trim());
//        onLoginSuccess();
//    }
//
//    @Override
//    public void OnUpdateUserInfoNodata(String s) {
//        ToastUtils.showLong(s);
//    }
//
//    @Override
//    public void OnUpdateUserInfoFail(String s) {
//        ToastUtils.showLong(s);
//    }
//
//    // 获取数据bufen
//    @Override
//    public void OnGetUserInfoSuccess(SLoginUserInfoBean bean) {
//        // 用户昵称bufen
//        if (TextUtils.isEmpty(bean.getPbUser().getNickName())) {
////            tvInfo2.setText("");
//        } else {
//            edt1.setText(bean.getPbUser().getNickName());
//        }
//        // 用户日期bufen
//        if (TextUtils.isEmpty(bean.getPbUser().getBirthDate())) {
//            String aaa = DateUtil.format(new Date(), DateUtil.FORMATER_YMD_CN);
//            tv_birthday2.setText(aaa);
//            wheelData.setSelectedDay(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//            wheelData.setSelectedMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
//            wheelData.setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
//            //
//            datepicker1.updateDate(year0, monthOfYear0, dayOfMonth0);
//
//        } else {
////            String curdatestrString = DateUtil.format(
////                    new Date(bean.getPbUser().getCreateTime()), DateUtil.FORMATER_YMD_CN);
//            String aaaa = TimeUtils.millis2String(Long.valueOf(bean.getPbUser().getBirthDate()),
//                    new SimpleDateFormat(DateUtil.FORMATER_YMD_CN, Locale.getDefault()));
//            tv_birthday2.setText(aaaa);
//            String bbbb = TimeUtils.millis2String(Long.valueOf(bean.getPbUser().getBirthDate()),
//                    new SimpleDateFormat(DateUtil.FORMATER_YMD, Locale.getDefault()));
//            String[] str = bbbb.split("-");
//            if (str == null || str.length == 0) {
//                return;
//            }
//            int day1 = Integer.valueOf(str[2]);
//            int month1 = Integer.valueOf(str[1]);
//            int year1 = Integer.valueOf(str[0]);
//            wheelData.setSelectedDay(day1);
//            wheelData.setSelectedMonth(month1);
//            wheelData.setSelectedYear(year1);
//            //
//            year0 = year1;
////            if (month1 == 12) {
////                monthOfYear0 = 0;
////            } else {
////                monthOfYear0 = month1;
////            }
//            monthOfYear0 = month1;
//
//            dayOfMonth0 = day1;
//            datepicker1.updateDate(year0, monthOfYear0 - 1, dayOfMonth0);
//        }
//        // 用户性别bufen
//        if (TextUtils.isEmpty(bean.getPbUser().getGender())) {
//            set_boy();
//        } else {
//            if (TextUtils.equals(bean.getPbUser().getGender(), "1")) {
//                set_boy();
//            }
//            if (TextUtils.equals(bean.getPbUser().getGender(), "2")) {
//                set_girl();
//            }
//        }
//
//        if (TextUtils.equals("1", SPUtils.getInstance().getString(CommonUtils.MMKV_forceLogin)) && !bean.getPbUser().isHasChild()) {
//            iv1.setVisibility(View.INVISIBLE);
//        } else {
//            iv1.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void OnGetUserInfoNodata(String s) {
//        tvInfo2.setText("");
//        String aaa = DateUtil.format(new Date(), DateUtil.FORMATER_YMD_CN);
//        tv_birthday2.setText(aaa);
//        set_boy();
//    }
//
//    @Override
//    public void OnGetUserInfoFail(String s) {
//        tvInfo2.setText("");
//        String aaa = DateUtil.format(new Date(), DateUtil.FORMATER_YMD_CN);
//        tv_birthday2.setText(aaa);
//        set_boy();
//    }

//    @Override
//    public void OnConfigureSuccess(SConfigureBean sConfigureBean) {
//        SPUtils.getInstance().put(CommonUtils.START_COUNT, sConfigureBean.getStartSound());
//        SPUtils.getInstance().put(CommonUtils.USER_FORCE_LOGIN, sConfigureBean.getForceLogin());
//        SPUtils.getInstance().put(CommonUtils.USER_FORCE_CHILD, sConfigureBean.getForceChild());
//        if (TextUtils.equals(sConfigureBean.getForceChild(), "1")) {
//            tv1.setVisibility(View.INVISIBLE);
//        } else {
//            tv1.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void OnConfigureNodata(String s) {
//
//    }
//
//    @Override
//    public void OnConfigureFail(String s) {
//
//    }
}
