package com.haiersmart.mobilelife.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.haiersmart.mobilelife.R;
import com.haiersmart.mobilelife.constant.ConstantNetUtil;
import com.haiersmart.mobilelife.constant.ConstantUtil;
import com.haiersmart.mobilelife.constant.MobileLifeApplication;
import com.haiersmart.mobilelife.domain.Activity_listNew;
import com.haiersmart.mobilelife.domain.AppBean;
import com.haiersmart.mobilelife.domain.Channel_listNew;
import com.haiersmart.mobilelife.domain.DeviceBean;
import com.haiersmart.mobilelife.domain.Myfamily;
import com.haiersmart.mobilelife.domain.NetMessage;
import com.haiersmart.mobilelife.domain.UserInfoMainPageNew;
import com.haiersmart.mobilelife.domain.User_infoNew;
import com.haiersmart.mobilelife.domain.VerifyInfo;
import com.haiersmart.mobilelife.ui.activities.personal.MyCollectActivity;
import com.haiersmart.mobilelife.ui.activities.personal.PersonalActivity;
import com.haiersmart.mobilelife.ui.animation.Constant;
import com.haiersmart.mobilelife.ui.animation.SwitchAnimationUtil;
import com.haiersmart.mobilelife.ui.base.BaseNetWorkTabFragmentActivity;
import com.haiersmart.mobilelife.ui.fragment.Fragment_main_live;
import com.haiersmart.mobilelife.ui.fragment.Fragment_main_message;
import com.haiersmart.mobilelife.ui.fragment.Fragment_main_shop1;
import com.haiersmart.mobilelife.ui.fragment.LookaroundFragment;
import com.haiersmart.mobilelife.ui.fragment.QuickHomeFragment;
import com.haiersmart.mobilelife.util.DataProvider;
import com.haiersmart.mobilelife.util.MyLogUtil;
import com.haiersmart.mobilelife.util.ToastUtil;
import com.haiersmart.mobilelife.util.jsonParserUtils.JsonUtils;
import com.haiersmart.mobilelife.view.adapter.MyCenterGridViewAdapter;
import com.haiersmart.mobilelife.view.adapter.MyCenterListViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


public class MobileMainActivity extends BaseNetWorkTabFragmentActivity implements View.OnClickListener {

    public static final String TAG_BX = "MobileMainActivity_bx";
    public static final String TAG_QB = "MobileMainActivity_qb";
    public static final String TAG_grzx = "MobileMainActivity_grzx";
    //    private ViewPager mViewPager;
//    private FragmentPagerAdapter mAdapter;
//    private List<BaseChildNetWorkFragment> mDatas;
    /**
     * 加载的布局文件
     **/
    private LinearLayout ll_header2;
    private RelativeLayout nodata;
    private LinearLayout tailView;
    private TextView tv_nodata;
    private ListView shining_listView;
    private MyCenterListViewAdapter shining_list_adapter;
    //GridView
    private List<Myfamily> myRatingsListItem;
    private MyCenterGridViewAdapter shining_grid_adapter;
    private GridView shining_gridview;
    private UserInfoMainPageNew uimp;
    private User_infoNew user_info;
    private List<Channel_listNew> channel_list;
    private List<Activity_listNew> activity_list;


    //个人中心
    private RelativeLayout rl_my;//个人中心
    private RelativeLayout search_title;//逛逛搜索头
    private LinearLayout ll_login_and_register; //登录与注册
    private LinearLayout code_layout; //登录完成后显示
    private TextView tv_my_wdqb_string;//钱包
    private LinearLayout ll_my_wddd,//我的订单
            ll_my_wdqb,//我的钱包
            ll_my_wdxx,//我的消息
            ll_my_wdsc,//我的收藏
            ll_my_jtjk,//家庭健康
            ll_my_sjc,//私家车
            ll_my_wdbx,//我的冰箱
            ll_my_sz;//设置
    private ImageView codeView;//头像
    private TextView tv_my_name,//名字
            tv_my_family,//我的家庭成员
            tv_my_account,//账户余额
            tv_my_address;//地址


    public static final String TAG = "MyActivity";
    private ImageView mNewUpdate; // 新版本提醒
    private boolean hasNewUpdate = false; // 是否有新版本
    private Dialog mDialog;
    private TextView mBtnConfirm;
    private TextView mBtnCancel;
    private Button login_btn_outlogin;// 注销


    private TextView mShop;//逛逛
    private TextView mIneed;//我要
    private TextView mDynamics;//动态
    private TextView mLive;//好邻国
    private TextView mCar;//私家车
    private TextView tv_kk;//title

    private ImageView mTabLine1;
    private ImageView mTabLine2;
    private ImageView mTabLine3;
    private ImageView mTabLine4;
    private ImageView mTabLine5;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mLayout;
    //    private TextView tv_ineed;


    private int mCurrentPageIndex;
    private TextView tv_head_left, tv_head_right, tv_head_right2, tv_head_right3;


//    private TextView tv_head2_middle_food, tv_head2_middle_life, tv_head2_middle_sale;
//    private RelativeLayout rl_head2_middle_search;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        // 初始化动画部分
        Constant.mType = SwitchAnimationUtil.AnimationType.HORIZION_RIGHT;

        new SwitchAnimationUtil().startAnimation(getWindow().getDecorView(),
                Constant.mType);


        initScreenInfo();
        //teat
        findviews();
        addlisteners();

        setAPPandDeviceInfo(mContext);
        if (savedInstanceState == null) {
            addFirstFragment(Fragment_main_shop1.class, ConstantUtil.NEW_TAB_00);
            mShop.setTextColor(getResources().getColor(R.color.green2));
        }
//        doNetWorkUser();
        // TODO
        // mLocationClient = getApplication().mLocationClient;
        // latitude = ((GlobalApplication) getApplication()).mlatitude;
        // longitude = ((GlobalApplication) getApplication()).mlongitude;
        // baiduLocation = ((GlobalApplication) getApplication()).baiduLocation;

//        initData();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//        mLocationClient = ((MobileLifeApplication)getApplication()).mLocationClient;

    }

    public void switch2Second(Bundle bundle) {
        mCurrentPageIndex = 1;
        switchTab(LookaroundFragment.class, ConstantUtil.NEW_TAB_01, bundle);
        enableTab();
        enableView(mIneed, false);
        //title
        tv_kk.setText(getString(R.string.main_foot_mineed));
        control_top2(mCurrentPageIndex);
        setTopRightButtonVisiable(View.VISIBLE, View.VISIBLE, View.GONE);
        search_title.setVisibility(View.VISIBLE);
    }

    public static void setAPPandDeviceInfo(Context mContext) {
        DataProvider.app = new AppBean(mContext, DataProvider.getInstance().getUser_id());
        DataProvider.device = new DeviceBean(mContext);
        DataProvider.verifyInfo = new VerifyInfo(DataProvider.app, DataProvider.device);
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<VerifyInfo>() {
        }.getType();
        String body = new Gson().toJson(DataProvider.verifyInfo, type);
        DataProvider.verifyInfoJSON = JsonUtils.getJSONObjectByJSONString(body);
    }

    private LocationClient mLocationClient;

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
//        mLocationClient.stop();
        super.onStop();
    }

    private void initScreenInfo() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        float density = dm.density;
        int screenWidth = (int) (widthPixels / density);
        int screenHeight = (int) (heightPixels / density);
        MyLogUtil.e(TAG, "widthPixels" + widthPixels);
        MyLogUtil.e(TAG, "heightPixels" + heightPixels);
        MyLogUtil.e(TAG, "density" + density);
        MyLogUtil.e(TAG, "screenWidth" + screenWidth);
        MyLogUtil.e(TAG, "screenHeight" + screenHeight);
    }

    private void findviews() {
        tv_head_left = (TextView) findViewById(R.id.tv_head_left);
        tv_head_right = (TextView) findViewById(R.id.tv_head_right);
        tv_head_right2 = (TextView) findViewById(R.id.tv_head_right2);
        tv_head_right3 = (TextView) findViewById(R.id.tv_head_right3);

//        tv_head2_middle_food = (TextView) findViewById(R.id.tv_head2_middle_food);
//        tv_head2_middle_life = (TextView) findViewById(R.id.tv_head2_middle_life);
//        tv_head2_middle_sale = (TextView) findViewById(R.id.tv_head2_middle_sale);
//
//        rl_head2_middle_search = (RelativeLayout) findViewById(R.id.rl_head2_middle_search);

        mShop = (TextView) findViewById(R.id.tv_mShop);
        mIneed = (TextView) findViewById(R.id.tv_mIneed);
        mDynamics = (TextView) findViewById(R.id.tv_mDynamics);
        mLive = (TextView) findViewById(R.id.tv_mLive);
        mCar = (TextView) findViewById(R.id.tv_mCar);
        tv_kk = (TextView) findViewById(R.id.tv_kk);

        mTabLine1 = (ImageView) findViewById(R.id.iv_tabLine1);
        mTabLine2 = (ImageView) findViewById(R.id.iv_tabLine2);
        mTabLine3 = (ImageView) findViewById(R.id.iv_tabLine3);
        mTabLine4 = (ImageView) findViewById(R.id.iv_tabLine4);
        mTabLine5 = (ImageView) findViewById(R.id.iv_tabLine5);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        mLayout = (LinearLayout) findViewById(R.id.left_drawer);
        mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //个人中心
        // 添加head
        ll_header2 = (LinearLayout) View.inflate(mContext,
                R.layout.activity_main_left_mycenter_new_head, null);
        ll_login_and_register = (LinearLayout) ll_header2.findViewById(R.id.ll_login_and_register);
        code_layout = (LinearLayout) ll_header2.findViewById(R.id.code_layout);
        rl_my = (RelativeLayout) ll_header2.findViewById(R.id.rl_add);
        codeView = (ImageView) ll_header2.findViewById(R.id.code_img);
        tv_my_name = (TextView) ll_header2.findViewById(R.id.tv_my_name);
        tv_my_family = (TextView) ll_header2.findViewById(R.id.tv_my_family);
        tv_my_account = (TextView) ll_header2.findViewById(R.id.tv_my_account);
        tv_my_address = (TextView) ll_header2.findViewById(R.id.tv_my_address);

//        tv_my_wdqb_string = (TextView) findViewById(R.id.tv_my_wdqb_string);
        search_title = (RelativeLayout) findViewById(R.id.search_title);
//        ll_my_wddd = (LinearLayout) findViewById(R.id.ll_my_wddd);
//        ll_my_wdqb = (LinearLayout) findViewById(R.id.ll_my_wdqb);
//        ll_my_wdxx = (LinearLayout) findViewById(R.id.ll_my_wdxx);
//        ll_my_wdsc = (LinearLayout) findViewById(R.id.ll_my_wdsc);
//        ll_my_jtjk = (LinearLayout) findViewById(R.id.ll_my_jtjk);
//        ll_my_sjc = (LinearLayout) findViewById(R.id.ll_my_sjc);
//        ll_my_wdbx = (LinearLayout) findViewById(R.id.ll_my_wdbx);
//        ll_my_sz = (LinearLayout) findViewById(R.id.ll_my_sz);

        //添加footer
        tailView = (LinearLayout) View.inflate(mContext,
                R.layout.activity_main_left_mycenter_new_footer, null);
        shining_gridview = (GridView) tailView.findViewById(R.id.zhuanjia_gridview);
        shining_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        shining_grid_adapter = new MyCenterGridViewAdapter(this);
        shining_gridview.setAdapter(shining_grid_adapter);

        tv_my_name.setText("");
        tv_my_family.setVisibility(View.GONE);
        tv_my_account.setText("");
        tv_my_address.setVisibility(View.GONE);
        /*tv_ineed = (TextView) findViewById(R.id.tv_ineed);
        tv_ineed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastLong("我要！");
            }
        });*/

//        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);

        nodata = (RelativeLayout) findViewById(R.id.nodata);
        tv_nodata = (TextView) findViewById(R.id.tv_nodata2);
        tv_nodata.setOnClickListener(this);


        shining_listView = (ListView) findViewById(R.id.zhuanjia_keshi_list);

        shining_listView.addHeaderView(ll_header2);
        shining_listView.addFooterView(tailView);
//        which_page = 1;
        shining_list_adapter = new MyCenterListViewAdapter(this);
        shining_listView.setAdapter(shining_list_adapter);


    }

    private void JumpmCar() {
        mCar.performClick();
    }

    private void addlisteners() {
        tv_head_left.setOnClickListener(this);
        tv_head_right.setOnClickListener(this);
        tv_head_right2.setOnClickListener(this);
        tv_head_right3.setOnClickListener(this);
        search_title.setOnClickListener(this);

        //个人中心
        codeView.setOnClickListener(this);
        //
//        rl_my.setOnClickListener(this);
        ll_login_and_register.setOnClickListener(this);

//        ll_my_wddd.setOnClickListener(this);
//        ll_my_wdqb.setOnClickListener(this);
//        ll_my_wdxx.setOnClickListener(this);
//        ll_my_wdsc.setOnClickListener(this);
//        ll_my_jtjk.setOnClickListener(this);
//        ll_my_sjc.setOnClickListener(this);
//        ll_my_wdbx.setOnClickListener(this);
//        ll_my_sz.setOnClickListener(this);
        tv_my_family.setOnClickListener(this);

//        tv_head2_middle_food.setOnClickListener(this);
//        tv_head2_middle_life.setOnClickListener(this);
//        tv_head_right2.setOnClickListener(this);

        mShop.setOnClickListener(this);
        mIneed.setOnClickListener(this);
        mDynamics.setOnClickListener(this);
        mLive.setOnClickListener(this);
        mCar.setOnClickListener(this);

        search_title.setOnClickListener(this);

        shining_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Channel_listNew listItem = (Channel_listNew) adapterView
                        .getItemAtPosition(position);
                if (position == 1) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("我的钱包");
                    Intent intent = new Intent(MobileMainActivity.this, MyPocketActivityNew.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);
                } else if (position == 2) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("我的订单");
                    Intent intent = new Intent(MobileMainActivity.this, OrderActivity.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);
                } else if (position == 3) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("我的收藏");
                    Intent intent = new Intent(MobileMainActivity.this, MyCollectActivity.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);
                } else if (position == 4) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("消息");
                    JumpmCar();
                    if (mDrawerLayout.isDrawerOpen(mLayout)) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        mDrawerLayout.openDrawer(GravityCompat.START);
                    }
//                    Intent intent = new Intent(MobileMainActivity.this, FoodSearchActivityNew.class);
//                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
//                    startActivity(intent);
                } else if (position == 5) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("家庭健康");
                    Intent intent = new Intent(MobileMainActivity.this, MyFamilyActivity.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);
                } else if (position == 6) {
                    if (!isLogin()) {
                        return;
                    }
//            ToastUtil.showToastLong("我的冰箱");
//            Intent intent = new Intent(mContext, AddressEditActivityBase.class);
//            intent.putExtra(ConstantUtil.INTENT_FROM,
//                    AddressActivityBase.TAG2);//
//            startActivity(intent);
                } else if (position == 7) {
                    if (!isLogin()) {
                        return;
                    }
                    //ToastUtil.showToastLong("我的地址");
                    Intent intent = new Intent(MobileMainActivity.this, AddressManagerActivity.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);
                } else if (position == 8) {
//            ToastUtil.showToastLong("设置");
                    Intent intent = new Intent(MobileMainActivity.this, SettionActivityBase.class);
                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
                    startActivity(intent);

                }
//                Intent intent = new Intent(mContext,
//                        MobileMainActivity.class);
//                intent.putExtra(ConstantUtil.INTENT_INFO1, listItem);
//                startActivity(intent);
            }
        });
        shining_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                Activity_listNew listItem = (Activity_listNew) adapterView
                        .getItemAtPosition(position);
//                Intent intent = new Intent(mContext,
//                        MobileMainActivity.class);
//                intent.putExtra(ConstantUtil.INTENT_INFO1, listItem);
//                startActivity(intent);
            }
        });

    }

    @Override
    protected void changeTabUI(@NonNull String tabId) {
        enableTab();
        if (tabId.equals(ConstantUtil.NEW_TAB_00)) {
            enableView(mShop, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mshop));
            setTopRightButtonVisiable(View.VISIBLE, View.GONE, View.GONE);
        } else if (tabId.equals(ConstantUtil.NEW_TAB_01)) {
            enableView(mIneed, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mineed));
            setTopRightButtonVisiable(View.VISIBLE, View.GONE, View.GONE);
        } else if (tabId.equals(ConstantUtil.NEW_TAB_02)) {
            enableView(mDynamics, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mdynamics));
            setTopRightButtonVisiable(View.GONE, View.GONE, View.GONE);
        } else if (tabId.equals(ConstantUtil.NEW_TAB_03)) {
            enableView(mLive, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mlive));
            setTopRightButtonVisiable(View.GONE, View.GONE, View.GONE);
        } else if (tabId.equals(ConstantUtil.NEW_TAB_04)) {
            enableView(mCar, false);
            //title
            tv_head_left.setVisibility(View.INVISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mcar));
            setTopRightButtonVisiable(View.GONE, View.GONE, View.VISIBLE);
//            tv_head_right2.setCompoundDrawables(null,null,getApplication().getResources().getDrawable(R.mipmap.setting),null);
//            tv_head_right2.setCompoundDrawablesWithIntrinsicBounds(0,
//                    0,R.mipmap.setting, 0);
//            tv_head_right2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MobileMainActivity.this, MessageSettingActivityOld.class);
//                    intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
//                    startActivity(intent);
//                }
//            });
        }
    }

    private void setTopRightButtonVisiable(int a, int b, int c) {
        tv_head_right.setVisibility(a);
        tv_head_right2.setVisibility(b);
        tv_head_right3.setVisibility(c);
    }

    @Override
    protected void networkCallBack(NetMessage message) {
        switch (message.getRequestCode()) {
            case REQUEST_CODE_NET_1: {//获取用户信息更新到界面上
                if (message.getOk()) {
                    uimp = new UserInfoMainPageNew();
                    user_info = new User_infoNew();
                    channel_list = new ArrayList<Channel_listNew>();
                    activity_list = new ArrayList<Activity_listNew>();

                    uimp = JsonUtils.getBean(message.getResult().toString(), UserInfoMainPageNew.class);
                    user_info = uimp.getUser_info();
                    channel_list = uimp.getChannel_list();
                    activity_list = uimp.getActivity_list();
                    //个人中心
                    if (uimp != null) {
                        nodata.setVisibility(View.GONE);
//                        mSwipeLayout.setVisibility(View.VISIBLE);
                        shining_listView.setVisibility(View.VISIBLE);
                        //head
                        if (user_info != null) {

                            MobileLifeApplication.getInstance().getSpUtil().setUserHeadUrl(user_info.getUser_face_image());
                            MobileLifeApplication.getInstance().getSpUtil().setUserName(user_info.getUser_nick_name());
                            MobileLifeApplication.getInstance().getSpUtil().setUserPhone(user_info.getUser_phone());

                            tv_my_name.setText(MobileLifeApplication.getInstance().getSpUtil().getUserName());
                            tv_my_account.setText(MobileLifeApplication.getInstance().getSpUtil().getUserPhone());
                            MobileLifeApplication.getImageLoader().displayImage(MobileLifeApplication.getInstance().getSpUtil().getUserHeadUrl(), codeView, MobileLifeApplication.getLoaderOptionsFace2());

                        }
                        //center
                        if (channel_list != null && channel_list.size() > 0) {
                            shining_list_adapter.setContacts(channel_list);
                            shining_list_adapter.notifyDataSetChanged();
                        }
                        //footer
                        if (activity_list != null && activity_list.size() > 0) {
                            shining_grid_adapter.setContacts(activity_list);
                            shining_grid_adapter.notifyDataSetChanged();
                        }
                    }


                } else {
//                    nodata.setVisibility(View.VISIBLE);
////                        mSwipeLayout.setVisibility(View.VISIBLE);
//                    shining_listView.setVisibility(View.GONE);
                }

            }
            break;
            case REQUEST_CODE_NET_2: {

            }
            break;
            default:
                break;
        }
    }


    public boolean isLogin() {
        String uid = DataProvider.getInstance().getUser_id();
        if (TextUtils.isEmpty(uid) || uid.equals("0")) {
            startActivity(new Intent(this, LoginActivity.class));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == tv_head_left) {
//            ToastUtil.showToastLong("个人中心");
//            startActivity(new Intent(mContext, AddressEditActivityBase.class));//AddressActivityBase  WebViewDemoActivityBase
//            Intent intent = new Intent(mContext, AddressActivityBase.class);
//            intent.putExtra(ConstantUtil.INTENT_FROM, AddressActivityBase.TAG2);
//            startActivity(intent);

            if (mDrawerLayout.isDrawerOpen(mLayout)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

        } else if (v == tv_head_right) {
//            ToastUtil.showToastLong("冰箱");
//            startActivity(new Intent(mContext, WebViewDemoActivityBase.class));
            Intent intent = new Intent(mContext, BindNetActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_BX);
            startActivity(intent);
//            Intent intent = new Intent(mContext, NewListViewActivityDemo.class);
//            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_BX);
//            startActivity(intent);
        } else if (v == tv_head_right2) {
//            ToastUtil.showToastLong("钱包");
//            startActivity(new Intent(this, MyMsgActivityBase.class));
            //跳个人中心
            Intent intent = new Intent(mContext, MyPocketActivityNew.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_grzx);
            startActivity(intent);
        } else if (v == tv_head_right3) {
//            ToastUtil.showToastLong("钱包");
//            startActivity(new Intent(this, MyMsgActivityBase.class));
            //跳个人中心
            //TODO
            Intent intent = new Intent(MobileMainActivity.this, MessageSettingActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);

        } else if (v == ll_login_and_register) {
            startActivityForResult(new Intent(this, LoginActivity.class), 1001);
//            startActivity(new Intent(mContext, WebViewDemoActivityBase.class));
//            Intent intent = new Intent(mContext, BindNetActivity.class);
//            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_QB);
//            startActivity(intent);
        } else if (v == codeView) {
//            ToastUtil.showToastLong("头像");
//            startActivity(new Intent(this, LoginActivity.class));
            Intent intent = new Intent(mContext, PersonalActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_grzx);
            startActivity(intent);
        } else if (v == rl_my) {
//            ToastUtil.showToastLong("个人中心1");
            Intent intent = new Intent(mContext, AddressActivityBase.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_grzx);
            startActivity(intent);

        } else if (v == tv_my_family) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("我的家庭");
            Intent intent = new Intent(mContext, MyFamilyActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, MobileMainActivity.TAG_grzx);
            startActivity(intent);

        } else if (v == ll_my_wddd) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("我的订单");
            Intent intent = new Intent(MobileMainActivity.this, OrderActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
            overridePendingTransition(R.anim.open_main, R.anim.close_next);

        } else if (v == ll_my_wdqb) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("我的钱包");
            Intent intent = new Intent(MobileMainActivity.this, MyPocketActivityNew.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == ll_my_wdxx) {
            if (!isLogin()) {
                return;
            }
            //ToastUtil.showToastLong("我的地址");
            Intent intent = new Intent(MobileMainActivity.this, AddressManagerActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == ll_my_wdsc) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("我的收藏");
            Intent intent = new Intent(MobileMainActivity.this, MyCollectActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == ll_my_jtjk) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("家庭健康");
            Intent intent = new Intent(MobileMainActivity.this, MyFamilyActivity.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == ll_my_sjc) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("私家车");
            Intent intent = new Intent(MobileMainActivity.this, FoodSearchActivityNew.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == ll_my_wdbx) {
            if (!isLogin()) {
                return;
            }
//            ToastUtil.showToastLong("我的冰箱");
//            Intent intent = new Intent(mContext, AddressEditActivityBase.class);
//            intent.putExtra(ConstantUtil.INTENT_FROM,
//                    AddressActivityBase.TAG2);//
//            startActivity(intent);
        } else if (v == ll_my_sz) {
//            ToastUtil.showToastLong("设置");
            Intent intent = new Intent(MobileMainActivity.this, SettionActivityBase.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);

        } else if (v == mShop) {
            mCurrentPageIndex = 0;
            switchTab(Fragment_main_shop1.class, ConstantUtil.NEW_TAB_00);
            enableTab();
            enableView(v, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mshop));
            control_top2(mCurrentPageIndex);
            setTopRightButtonVisiable(View.VISIBLE, View.GONE, View.GONE);

            search_title.setVisibility(View.GONE);
        } else if (v == mIneed) {
            mCurrentPageIndex = 1;
            switchTab(LookaroundFragment.class, ConstantUtil.NEW_TAB_01);
            enableTab();
            enableView(v, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mineed));
            control_top2(mCurrentPageIndex);
            setTopRightButtonVisiable(View.VISIBLE, View.GONE, View.GONE);
            search_title.setVisibility(View.VISIBLE);

        } else if (v == mDynamics) {
            mCurrentPageIndex = 2;
            switchTab(QuickHomeFragment.class, ConstantUtil.NEW_TAB_02);
            enableTab();
            enableView(v, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mdynamics));
            control_top2(mCurrentPageIndex);
            setTopRightButtonVisiable(View.VISIBLE, View.GONE, View.GONE);
            search_title.setVisibility(View.GONE);

        } else if (v == mLive) {
            mCurrentPageIndex = 3;
            switchTab(Fragment_main_live.class, ConstantUtil.NEW_TAB_03);
            enableTab();
            enableView(v, false);
            //title
            tv_head_left.setVisibility(View.VISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mlive));
            control_top2(mCurrentPageIndex);
            setTopRightButtonVisiable(View.GONE, View.GONE, View.GONE);
            search_title.setVisibility(View.GONE);

        } else if (v == mCar) {
            mCurrentPageIndex = 4;
            switchTab(Fragment_main_message.class, ConstantUtil.NEW_TAB_04);
            enableTab();
            enableView(v, false);
            //title
            tv_head_left.setVisibility(View.INVISIBLE);
            tv_kk.setText(getString(R.string.main_foot_mcar));
            control_top2(mCurrentPageIndex);
            setTopRightButtonVisiable(View.GONE, View.GONE, View.VISIBLE);
            search_title.setVisibility(View.GONE);
        } /*else if (v == tv_head_right2) {
            Intent intent = new Intent(this, MyCollectActivity.class);
            startActivity(intent);

        }*/ else if (v == search_title) {
            Intent intent = new Intent(this, FoodSearchActivityNew.class);
            intent.putExtra(ConstantUtil.INTENT_FROM, TAG);
            startActivity(intent);
        } else if (v == tv_nodata) {
            doNetWorkUser();
        }
//        mViewPager.setCurrentItem(mCurrentPageIndex);

    }

    private void initData() {
//        mDatas = new ArrayList<BaseChildNetWorkFragment>();
//        Fragment_main_shop fragment_main_shop = new Fragment_main_shop();
//        Fragment_main_ineed fragment_main_ineed = new Fragment_main_ineed();
//        Fragment_main_dynamics fragment_main_dynamics = new Fragment_main_dynamics();
//        Fragment_main_live fragment_main_live = new Fragment_main_live();
//        Fragment_main_car fragment_main_car = new Fragment_main_car();
//
//        mDatas.add(fragment_main_shop);
//        mDatas.add(fragment_main_ineed);
//        mDatas.add(fragment_main_dynamics);
//        mDatas.add(fragment_main_live);
//        mDatas.add(fragment_main_car);

//        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public int getCount() {
//                return mDatas.size();
//            }
//
//            @Override
//            public Fragment getItem(int position) {
//                return mDatas.get(position);
//            }
//        };
//
//        mViewPager.setAdapter(mAdapter);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                control_top2(position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffSet,
//                                       int positionOffSetPx) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
    }

    /**
     * 滚动条和字体整体部分操作
     */
    private void control_top2(int position) {
        reset_text();
        reset_tabline_color();

        switch (position) {
            case 0:
                // mChat.setTextColor(Color.argb(100, 118, 170, 26));
//                mChat.setTextColor(Color.parseColor("#008000"));
                mShop.setTextColor(getResources().getColor(R.color.green2));
                break;
            case 1:
                mIneed.setTextColor(getResources().getColor(R.color.green2));
                break;
            case 2:
                mDynamics.setTextColor(getResources().getColor(R.color.green2));
                break;
            case 3:
                mLive.setTextColor(getResources().getColor(R.color.green2));
                break;
            case 4:
                mCar.setTextColor(getResources().getColor(R.color.green2));
                break;
            default:
                break;
        }

        switch (position) {
            case 0:
                //TODO VISIBLE
                mTabLine1.setVisibility(View.GONE);
                break;
            case 1:

                mTabLine2.setVisibility(View.GONE);
                break;
            case 2:

                mTabLine3.setVisibility(View.GONE);
                break;
            case 3:

                mTabLine4.setVisibility(View.GONE);
                break;
            case 4:

                mTabLine5.setVisibility(View.GONE);
                break;

            default:
                break;
        }

        mCurrentPageIndex = position;
    }

    /**
     * 滚动条部分 重置操作
     */
    protected void reset_tabline_color() {
        mTabLine1.setVisibility(View.GONE);
        mTabLine2.setVisibility(View.GONE);
        mTabLine3.setVisibility(View.GONE);
        mTabLine4.setVisibility(View.GONE);
        mTabLine5.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1001:
                ll_login_and_register.setVisibility(View.GONE);
                code_layout.setVisibility(View.VISIBLE);
                doNetWorkUser();
                break;
        }

    }

    /**
     * 字体变色部分 重置操作
     */
    protected void reset_text() {
        mShop.setTextColor(getResources().getColor(R.color.gray3));
        mIneed.setTextColor(getResources().getColor(R.color.gray3));
        mDynamics.setTextColor(getResources().getColor(R.color.gray3));
        mLive.setTextColor(getResources().getColor(R.color.gray3));
        mCar.setTextColor(getResources().getColor(R.color.gray3));
    }

//    /**
//     * 字体变色部分 重置操作2
//     */
//    protected void reset_middle_text() {
//        tv_head2_middle_food.setBackgroundResource(R.anim.activity_main_head2_middle022);
//        tv_head2_middle_life.setBackgroundResource(R.anim.activity_main_head2_middle022);
//        tv_head2_middle_sale.setBackgroundResource(R.anim.activity_main_head2_middle022);
//        tv_head2_middle_food.setTextColor(getResources().getColor(R.color.black));
//        tv_head2_middle_life.setTextColor(getResources().getColor(R.color.black));
//        tv_head2_middle_sale.setTextColor(getResources().getColor(R.color.black));
//    }

    private void enableView(View v, Boolean enable) {
        if (enable) {
            if (!v.isEnabled()) {
                v.setEnabled(enable);//false
            }
        } else {
            if (v.isEnabled()) {
                v.setEnabled(enable);//true
            }
        }
    }

    private void enableTab() {
        mShop.setEnabled(true);
        mIneed.setEnabled(true);
        mDynamics.setEnabled(true);
        mLive.setEnabled(true);
        mCar.setEnabled(true);
    }
//    private void enableTab_head_middle() {
//        tv_head2_middle_food.setEnabled(true);
//        tv_head2_middle_life.setEnabled(true);
//        tv_head2_middle_sale.setEnabled(true);
//    }

    long firstTime;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mLayout)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {
//            mDrawerLayout.openDrawer(Gravity.START);
            if (System.currentTimeMillis() - firstTime < 3000) {
                super.onBackPressed();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MobileLifeApplication.closeApp();
                    }
                });
            } else {
                firstTime = System.currentTimeMillis();
                ToastUtil.showToastLong(getString(R.string.main_close));
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.haiersmart.mobilelife.ui.activities/http/host/path")
//        );
//        AppIndex.AppIndexApi.start(client, viewAction);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        Action viewAction = Action.newAction(
//                Action.TYPE_VIEW, // TODO: choose an action type.
//                "Main Page", // TODO: Define a title for the content shown.
//                // TODO: If you have web page content that matches this app activity's content,
//                // make sure this auto-generated web page URL is correct.
//                // Otherwise, set the URL to null.
//                Uri.parse("http://host/path"),
//                // TODO: Make sure this auto-generated app deep link URI is correct.
//                Uri.parse("android-app://com.haiersmart.mobilelife.ui.activities/http/host/path")
//        );
//        AppIndex.AppIndexApi.end(client, viewAction);
//        client.disconnect();
//    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
        if ("0".equals(DataProvider.getInstance().getUser_id()) || TextUtils.isEmpty(DataProvider.getInstance().getUser_id())) {
            ll_login_and_register.setVisibility(View.VISIBLE);
            code_layout.setVisibility(View.GONE);
            doNetWorkUser();
        } else {
            doNetWorkUser();
            ll_login_and_register.setVisibility(View.GONE);
            code_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    private void doNetWorkUser() {
        JSONObject params = new JSONObject();
        JSONObject bodystring = new JSONObject();
        try {
            bodystring.put("user_id", DataProvider.getInstance().getUser_id());

            params.put(ConstantUtil.PARAM_VERIFY_INFO, DataProvider.verifyInfoJSON);// 请求头
            params.put(ConstantUtil.PARAM_DATA, bodystring);//请求body
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestJSONObjectPostMsg(REQUEST_CODE_NET_1, ConstantNetUtil.MAINPAGE_HOME_INFO_GET, params);
    }
   /* public void updateShoppingCart(List<CartNumModify> cartNumModifies) {
        JSONObject params = new JSONObject();
        JSONObject data = new JSONObject();
        Gson gson = new Gson();
        try {
            data.put("uuid", "xianghan");
            data.put("user_id", DataProvider.getInstance().getUser_id());
            data.put("cell", JsonUtils.getJSONArrayByJSONString(gson.toJson(cartNumModifies)));
            params.put(ConstantUtil.PARAM_VERIFY_INFO, DataProvider.verifyInfoJSON);
            params.put(ConstantUtil.PARAM_DATA, data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestJSONObjectPostMsg(REQUEST_CODE_NET_2, ConstantNetUtil.SHOPPING_CART_UPDATE, params, getString(R.string.progress_loading));

    }*/
}
