package com.fosung.xuanchuanlan.xuanchuanlan.main.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZResponse;
import com.fosung.frameutils.util.AppUtil;
import com.fosung.frameutils.util.FileUtil;
import com.fosung.frameutils.util.GsonUtil;
import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.app.ConfApplication;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.common.consts.PathConst;
import com.fosung.xuanchuanlan.common.util.UpdateMgr;
import com.fosung.xuanchuanlan.mian.http.HttpUrlMaster;
import com.fosung.xuanchuanlan.mian.http.entity.UpdateBean;
import com.fosung.xuanchuanlan.mian.http.entity.UpdateReple;
import com.fosung.xuanchuanlan.xuanchuanlan.daketang.activity.DKTMainActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.activity.LocalProfileActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttp;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.XCLHttpUrlMaster;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.CustomerService;
import com.fosung.xuanchuanlan.xuanchuanlan.main.http.entity.XCLMainNoticeListReplayBean;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.DynamicListActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.NoticeListActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.notice.activity.RedBackplaneListActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity.LoginActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.personalcenter.activity.PersonalCenterActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.tools.BlurPopWin;
import com.fosung.xuanchuanlan.xuanchuanlan.tools.LongClickUtils;
import com.fosung.xuanchuanlan.xuanchuanlan.widget.MarqueeView;
import com.zcolin.gui.ZConfirm;
import com.zcolin.gui.ZDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


@ActivityParam(isShowToolBar = false)
public class MainActivity extends BaseActivity {


    final String ITEM_HEADER_TEXT = "ITEM_HEADER_TEXT";

    final String ITEM_ONE_ICON = "ITEM_ONE_ICON";
    final String ITEM_ONE_TEXT = "ITEM_ONE_TEXT";

    enum LocalVersion {
        DENGTA, RIKAZE
    }

    private LocalVersion localVersion = LocalVersion.DENGTA;

    private Handler handler = new Handler();
    private ListView listView;

    private MarqueeView marqueeView;
    private List<XCLMainNoticeListReplayBean.XCLNoticeData> listMarquee;
    private TextView enter_personalCenter;
    private TextView enter_file;
    private TextView enter_setting;
    private ViewGroup noticeViewGroup;
    private List<Map> data = new ArrayList<Map>() {{
        add(new HashMap<String, Object>() {{

            put(ITEM_HEADER_TEXT, "党建动态");
            put("data", new ArrayList<Map>() {{
                switch (localVersion) {
                    case DENGTA: {

                        //灯塔党建版
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_dengta);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_dengtadangjian);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_dangjianzixun);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_dangjianzixun);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_xinruidazhong);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_xinruidazhong);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_dazhongribao);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_dazhongribao);
                        }});

                    }
                    break;
                    case RIKAZE: {
                        //日喀则版本
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_rikazezhufengapp);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_rikazezhufengapp);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_rikazezhufengwangzhan);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_rikazezhufengwangzhan);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_xizangribao);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_xizangribao);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_rikazexinwenwang);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_rikazexinwenwang);
                        }});
                    }
                    break;
                }


            }});
        }});

        add(new HashMap<String, Object>() {{
            put(ITEM_HEADER_TEXT, "红色资源");
            put("data", new ArrayList<Map>() {{
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_tushuguan);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_tushuguan);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_weishipin);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_weishipin);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_zhuxuanlv);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_zhuxuanlv);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_daketang);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_daketang);
                }});
            }});
        }});
        add(new HashMap<String, Object>() {{
            put(ITEM_HEADER_TEXT, "党群服务");
            put("data", new ArrayList<Map>() {{
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_sanwugongkai);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_sanwugongkai);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_hongsebeiban);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_hongsebeiban);
                }});

                switch (localVersion) {
                    case DENGTA: {

                        //灯塔党建版本
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_teselvyou);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_teselvyou);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_zhengwufuwu);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_zhengwufuwu);
                        }});

                    }
                    break;
                    case RIKAZE: {
                        //日喀则版本
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_xizanglvyou);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_xizanglvyou);
                        }});
                        add(new HashMap<String, Object>() {{
                            put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_xizangzhengwu);
                            put(ITEM_ONE_TEXT, R.string.xcl_home_item_xizangzhengwu);
                        }});
                    }
                    break;
                }


            }});
        }});

        add(new HashMap<String, Object>() {{
            put(ITEM_HEADER_TEXT, "特色内容");
            put("data", new ArrayList<Map>() {{
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_bendigaikuang);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_bendigaikuang);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_gongzuodongtai);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_gongzuodongtai);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_tongzhigonggao);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_tongzhigonggao);
                }});
                add(new HashMap<String, Object>() {{
                    put(ITEM_ONE_ICON, R.drawable.xcl_homeicon_kehufuwu);
                    put(ITEM_ONE_TEXT, R.string.xcl_home_item_kehufuwu);
                }});
            }});
        }});

    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xcl_main);
        enter_personalCenter = (TextView) findViewById(R.id.enter_personalCenter);
        marqueeView = (MarqueeView) findViewById(R.id.marqueeView);
        enter_file = (TextView) findViewById(R.id.enter_file);
        enter_setting = (TextView) findViewById(R.id.enter_setting);
        noticeViewGroup = (ViewGroup) findViewById(R.id.xcl_mian_notice_LL);
//        enter_personalCenter.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));
//                return false;
//            }
//        });
        LongClickUtils.setLongClick(handler, enter_personalCenter, 5000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));
                return false;
            }
        });

        LongClickUtils.setLongClick(handler, enter_file, 3000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.android.rockchip", "com.android.rockchip.RockExplorer");
                startActivity(intent);
                return false;
            }
        });

        LongClickUtils.setLongClick(handler, enter_setting, 3000, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.moos.launcher3.rk3288", "com.android.setting.iot.SettingsActivity");
                startActivity(intent);
                return false;
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        //listView.addHeaderView(getLayoutInflater().inflate(R.layout.listview_xcl_header_main,null));

        listView.setAdapter(new MainListAdapter());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (logged()) {
            requestForMainNotice();
        } else {
            noticeViewGroup.setVisibility(View.GONE);
        }

        updateApp();
    }

    private void requestForMainNotice() {

        Map json = new HashMap();
        XCLHttp.postJson(XCLHttpUrlMaster.XCLMainNoitceList, json, new ZResponse<XCLMainNoticeListReplayBean>(XCLMainNoticeListReplayBean.class) {

            @Override
            public void onSuccess(Response response, XCLMainNoticeListReplayBean resObj) {
                if (resObj != null && resObj.datalist != null && !resObj.datalist.isEmpty()) {
                    listMarquee = resObj.datalist;
//                    setVideoCoverImageForCurrentIndex();
                    initMarquee();
                    noticeViewGroup.setVisibility(View.VISIBLE);
                } else {
                    noticeViewGroup.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
                noticeViewGroup.setVisibility(View.GONE);
            }
        });
    }


    /**
     * 通知公告纵向走马灯
     */
    private void initMarquee() {
        List<String> titleList = new ArrayList<>();
        for (XCLMainNoticeListReplayBean.XCLNoticeData data : listMarquee) {
            titleList.add(data.title);
        }
        marqueeView.startWithList(titleList);

        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                XCLMainNoticeListReplayBean.XCLNoticeData data = listMarquee.get(position);
//                Log.i("首页通知公告", "点击了通知公告：" + data.title);
                Intent intent = new Intent(MainActivity.this, XCLNoticeDetailActivity.class);
                intent.putExtra("title", data.title);
                intent.putExtra("content", data.content);
//                intent.putExtra("isRichContent",false);
                startActivity(intent);
            }
        });
    }

    protected boolean isAppExist(String pkgName) {
        ApplicationInfo info;
        try {
            info = getPackageManager().getApplicationInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            info = null;
        }

        return info != null;
    }

    private void requestCustomerData(final View view) {

        Map<String, String> map = new HashMap<String, String>();

        XCLHttp.post(XCLHttpUrlMaster.CUSTOMERSERVICE, map, new ZResponse<CustomerService>(CustomerService.class, mActivity, "加载中...") {
            @Override
            public void onSuccess(Response response, CustomerService resObj) {
                if (resObj.datalist != null && resObj.datalist.size() > 0) {
                    new BlurPopWin.Builder(MainActivity.this)
//                            .setContent("该配合你演出的我,眼视而不见,在比一个最爱你的人即兴表演")
                            //Radius越大耗时越长,被图片处理图像越模糊
                            .setRadius(1)
//                            .setTitle("我是标题")
                            //设置居中还是底部显示
                            .setIamgeURL(resObj.datalist.get(0).imgurl)
                            .setshowAtLocationType(0)
                            .setOutSideClickable(true)
                            .show(view.getRootView());
                }
            }

            @Override
            public void onError(int code, String error) {
                super.onError(code, error);
            }
        });

    }

    //登录状态
    private boolean logged() {

        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String userId = sp.getString("user_id", null);
        return userId != null;

    }
//    //点击某一个图标及标题时调用
//    public void onClickItemOne(View view){
//        Map map = (Map)view.getTag();
//        int row = (Integer) map.get("position");
//        int column = (Integer) map.get("index");
//        Toast.makeText(MainActivity.this,"row index : "+row+",column index : "+column,Toast.LENGTH_SHORT).show();
//    }

    private class MainListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.listview_xcl_item_main, null);

                viewHolder = new ViewHolder();
                viewHolder.headerTextView = (TextView) convertView.findViewById(R.id.headerTextView);
                viewHolder.scrollViewGroup = (ViewGroup) convertView.findViewById(R.id.scrollLinearLayout);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Map<String, Object> map = data.get(position);
            String text = (String) map.get(ITEM_HEADER_TEXT);
            viewHolder.headerTextView.setText(text);
            ArrayList<Map> list = (ArrayList<Map>) map.get("data");


            final ViewGroup viewGroup = viewHolder.scrollViewGroup;
            viewGroup.removeAllViews();

//            DisplayMetrics outMetrics = new DisplayMetrics();
//            int screenWidth = 0;
//            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//            if (outMetrics != null){
//                screenWidth = outMetrics.widthPixels;
//            }

            int oneWidth = 300;//scrollLLContentWidth > 0 ? scrollLLContentWidth/4:0;
            if (list != null) {

                for (int i = 0; i < list.size(); i++) {
                    Map oneMap = list.get(i);
                    final View one = getLayoutInflater().inflate(R.layout.item_xcl_one_main, viewGroup, false);
                    viewGroup.addView(one);
                    OneViewHolder oneViewHolder = new OneViewHolder();
                    oneViewHolder.textView = (TextView) one.findViewById(R.id.oneTextView);
                    oneViewHolder.textView.setText(getString((int) oneMap.get(ITEM_ONE_TEXT)));
                    oneViewHolder.imageView = (ImageView) one.findViewById(R.id.oneImageView);
                    oneViewHolder.imageView.setImageResource((Integer) oneMap.get(ITEM_ONE_ICON));
                    one.setTag(oneViewHolder);


                    one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OneViewHolder oneViewHolder1 = (OneViewHolder) v.getTag();
                            CharSequence text = oneViewHolder1.textView.getText();

                            //通过Content Provider和灯塔App交互
//                            ContentResolver contentResolver = getContentResolver();
//                            Uri uri = Uri.parse("content://com.fosung.lighthouse.providers.loginContentProvider");
//                            Cursor cursor = contentResolver.query(uri,null,null,null,null);
//                            int dtdjIsLogin = 0;//可以通过contentProvider获取灯塔的登录状态。
//                            if (cursor.moveToNext()){ //只放了一条数据
//                                dtdjIsLogin = cursor.getInt(cursor.getColumnIndex("isLogin"));
//                            }

                            SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("SystemInfo", Context.MODE_PRIVATE);
                            String machineMac = sp.getString("machineMac", "");//客户端唯一标识//"3HX0217919003605";


                            SharedPreferences userSp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                            String orgCode = userSp.getString("user_orgCode", "");//客户端唯一标识//"000002000008000001000011000001000002000302000017";

                            if (text.equals(getString(R.string.xcl_home_item_kehufuwu))) {
                                requestCustomerData(one);
                                return;
                            }


                            if (text.equals(getString(R.string.xcl_home_item_dengtadangjian))) {
                                try {
                                    AppUtil.startOtherApp(MainActivity.this, getString(R.string.xcl_home_packagename_dengtadangjian));
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }

                            //珠峰App
                            if (text.equals(getString(R.string.xcl_home_item_rikazezhufengapp))) {
                                try {
                                    AppUtil.startOtherApp(MainActivity.this, getString(R.string.xcl_home_packagename_rikazezhufengdangjian));
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_xinruidazhong))) {
                                try {
                                    AppUtil.startOtherApp(MainActivity.this, getString(R.string.xcl_home_packagename_xinruidazhong));
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_dangjianzixun))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_dangjianzixun));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }
                            //珠峰网站
                            if (text.equals(getString(R.string.xcl_home_item_rikazezhufengwangzhan))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_rikazezhufengwangzhan));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }
                            //西藏日报
                            if (text.equals(getString(R.string.xcl_home_item_xizangribao))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_xizangribao));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            //日喀则新闻网
                            if (text.equals(getString(R.string.xcl_home_item_rikazexinwenwang))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_rikazexinwenwang));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            //西藏旅游
                            if (text.equals(getString(R.string.xcl_home_item_xizanglvyou))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_xizanglvyou));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            //西藏政务
                            if (text.equals(getString(R.string.xcl_home_item_xizangzhengwu))) {
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_xizangzhengwu));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }


                            //大众日报
                            if (text.equals(getString(R.string.xcl_home_item_dazhongribao))) {

                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_dazhongribao));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_tushuguan))) {

                                try {

//                                    if (dtdjIsLogin == 0){
//                                        Intent intent = new Intent();
//                                        intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian),getString(R.string.xcl_home_classname_login));
//                                        startActivity(intent);
//                                        return;
//                                    }

                                    if (!logged()) {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        return;
                                    }

                                    Intent intent = new Intent();
                                    intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian), getString(R.string.xcl_home_classname_tushuguan));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }


                            if (text.equals(getString(R.string.xcl_home_item_weishipin))) {

                                try {


                                    if (!logged()) {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        return;
                                    }
                                    Intent intent = new Intent();
                                    intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian), getString(R.string.xcl_home_classname_weishipin));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_zhuxuanlv))) {

                                try {

//                                    if (dtdjIsLogin == 0){
//                                        Intent intent = new Intent();
//                                        intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian),getString(R.string.xcl_home_classname_login));
//                                        startActivity(intent);
//                                        return;
//                                    }

                                    if (!logged()) {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        return;
                                    }

                                    Intent intent = new Intent();
                                    intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian), getString(R.string.xcl_home_classname_zhuxuanlv));
//                                    Intent intent = new Intent(MainActivity.this, DKTTopicDetailActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }


                            if (text.equals(getString(R.string.xcl_home_item_daketang))) {

                                try {

//                                    if (dtdjIsLogin == 0){
//                                        Intent intent = new Intent();
//                                        intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian),getString(R.string.xcl_home_classname_login));
//                                        startActivity(intent);
//                                        return;
//                                    }

                                    if (!logged()) {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        return;
                                    }


//                                    Intent intent = new Intent();
//                                    intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian),getString(R.string.xcl_home_classname_daketang));


                                    Intent intent = new Intent(MainActivity.this, DKTMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);


                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }
                            if (text.equals(getString(R.string.xcl_home_item_sanwugongkai))) {
                                try {
//                                    Intent intent = new Intent();
//                                    intent.setClassName(getString(R.string.xcl_home_packagename_dengtadangjian),getString(R.string.xcl_home_classname_commonWebActivity));
//                                    intent.putExtra("title", getString(R.string.xcl_home_item_sanwugongkai));
//                                    intent.putExtra("url", XCLHttpUrlMaster.DTDJ_SWGK);
//                                    intent.putExtra("id", "SWGK");
//                                    intent.putExtra("isneedlogin", false);
                                    Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                    intent.putExtra("Url", getString(R.string.xcl_home_url_sanwugongkai));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "未安装应用或指定路径不正确", Toast.LENGTH_SHORT).show();
                                    ;
                                }
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_teselvyou))) {
                                Intent intent = new Intent(MainActivity.this, XCLTravelActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_hongsebeiban))) {
                                if (!logged()) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                Intent intent = new Intent(MainActivity.this, RedBackplaneListActivity.class);
//                                intent.putExtra("Url","file:///android_asset/dist/index.html#/cabinet/index?mac="+machineMac+"&orgCode="+orgCode);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_bendigaikuang))) {
                                if (!logged()) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                Intent intent = new Intent(MainActivity.this, LocalProfileActivity.class);
//                                intent.putExtra("Url","file:///android_asset/dist/index.html#/index?mac="+machineMac+"&orgCode="+orgCode);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_gongzuodongtai))) {
                                if (!logged()) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                Intent intent = new Intent(MainActivity.this, DynamicListActivity.class);
//                                intent.putExtra("Url","file:///android_asset/dist/index.html#/serve/index");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_tongzhigonggao))) {
                                if (!logged()) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                Intent intent = new Intent(MainActivity.this, NoticeListActivity.class);
//                                intent.putExtra("Url","file:///android_asset/dist/index.html#/inform/index");
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }

                            if (text.equals(getString(R.string.xcl_home_item_zhengwufuwu))) {
                                if (!logged()) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    return;
                                }
                                Intent intent = new Intent(MainActivity.this, XCLWebActivity.class);
                                intent.putExtra("Url", getString(R.string.xcl_home_url_zhengwufuwu));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return;
                            }
                            //Toast.makeText(MainActivity.this,"点击了 ： "+oneViewHolder1.enter_personalCenter.getText(),Toast.LENGTH_SHORT).show();
                        }
                    });

//                    View one = getLayoutInflater().inflate(R.layout.item_one_main,null);
//                    viewGroup.addView(one,new LinearLayout.LayoutParams(oneWidth,ViewGroup.LayoutParams.MATCH_PARENT));//指定宽度。

                }

            }

            return convertView;
        }


        private class ViewHolder {
            TextView headerTextView;
            ViewGroup scrollViewGroup;
        }

        private class OneViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    //更新App
    private void updateApp() {

        SharedPreferences sp = ConfApplication.APP_CONTEXT.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String user_name = sp.getString("user_tel", "");//yong户唯一标识
        // 更新
        UpdateReple bean = new UpdateReple();
        bean.updateCode = "XCL_01";
        bean.channelCode = "Android";
        bean.versionState = "lts";
        bean.currentVersion = AppUtils.getAppVersionName();
        if (!TextUtils.isEmpty(user_name)) {
            bean.expression = user_name;
        } else {
            bean.expression = "";
        }
        String Jsonbean = GsonUtil.beanToString(bean);
        //  ToastUtil.toastLong(""+Jsonbean);
        ZHttp.postJson(HttpUrlMaster.UPDATEINFO, Jsonbean, new ZResponse<UpdateBean>(UpdateBean.class) {
            @Override
            public void onSuccess(Response response, final UpdateBean resObj) {
                if (resObj != null && resObj.data != null) {
                    if (resObj.data.update) {
                        if ("1".equals(resObj.data.updateMsg.updateStrategy)) {
                            new ZConfirm(mActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage("必须完成本次更新才能继续使用本系统\n\n" + resObj.data.updateMsg.updateContent)
                                    .setCancelBtnText("退出系统")
                                    .setOKBtnText("立即升级")
                                    .setIsCancelAble(false)
                                    .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {
                                        @Override
                                        public boolean submit() {
                                            File file = new File(PathConst.CACHE + "xuanchuanlan.apk");
                                            FileUtil.delete(file);
                                            UpdateMgr.downLoadApp(mActivity, resObj.data.updateMsg.url);
                                            return true;
                                        }
                                    })
                                    .addCancelListener(new ZDialog.ZDialogCancelInterface() {
                                        @Override
                                        public boolean cancel() {
                                            AppUtil.quitSystem();
                                            return true;
                                        }
                                    })
                                    .show();
                        } else {
                            new ZConfirm(mActivity).setTitle("版本更新  " + resObj.data.updateMsg.versionInfo + "版")
                                    .setMessage(resObj.data.updateMsg.updateContent)
                                    .setCancelBtnText("暂不升级")
                                    .setOKBtnText("立即升级")
                                    .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {
                                        @Override
                                        public boolean submit() {
                                            File file = new File(PathConst.CACHE + "xuanchuanlan.apk");
                                            FileUtil.delete(file);
                                            UpdateMgr.downLoadApp(mActivity, resObj.data.updateMsg.url);
                                            return true;
                                        }
                                    })
                                    .show();
                        }
                    }
                }
            }

            @Override
            public void onError(int code, String error) {
//                super.onError(code, error);

            }
        });
    }
}
