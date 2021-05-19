package com.example.slbwifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.slbwifi.util.WifiDatabaseUtils;
import com.example.slbwifi.util.WifiPwdUtil;
import com.example.slbwifi.util.WifiUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private Context mContext;
    private static final String TAG = ScanResultAdapterNew.class.getSimpleName();
    private List<ScanResult> mScanResult;
    private List<WifiPwdUtil.WifiInfo> mWifiPwdInfo = new ArrayList<WifiPwdUtil.WifiInfo>();
    private int sendBroadcastCount;
    Map<String, String> mWifiPwdMap = new HashMap<String, String>();
    public String lianjie_content;

    public RecycleAdapter(Context context) {
        this.mContext = context;
        mScanResult = new ArrayList<>();
        sendBroadcastCount = 0;
    }

    public void refreshList(List<ScanResult> mResult) {

        mScanResult.clear();
        mScanResult.addAll(mResult);

        notifyDataSetChanged();
    }

    public List<ScanResult> getScanResult() {
        return mScanResult;
    }

    public void refreshPwdList(List<WifiPwdUtil.WifiInfo> mResult) {

        if (mResult == null)
            return;

        mWifiPwdInfo.clear();
        mWifiPwdInfo.addAll(mResult);

        mWifiPwdMap.clear();

        for (int i = 0; i < mWifiPwdInfo.size(); i++) {

//            Log.d(TAG, "ssid = " + mWifiPwdInfo.get(i).Ssid + " pwd = " + mWifiPwdInfo.get(i).Password);

            if (mWifiPwdInfo.get(i) != null) {

                mWifiPwdMap.put(mWifiPwdInfo.get(i).Ssid, mWifiPwdInfo.get(i).Password);
            }
        }

        notifyDataSetChanged();
    }

    public void setmScanResult(List<ScanResult> mScanResult) {
        this.mScanResult = mScanResult;
    }

    public void setLianjie_content(String lianjie_content) {
        this.lianjie_content = lianjie_content;
    }

    @Override
    public int getItemCount() {
        if (mScanResult == null)
            return 0;
        return mScanResult.size();
    }

    public Object getItem(int position) {
        return mScanResult.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.activity_demomain_item, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);
//        viewHolder.iv_imgurl = view.findViewById(R.id.iv_imgurl);
//        viewHolder.tv_content1 = view.findViewById(R.id.tv_content1);

        View convertView = LayoutInflater.from(mContext).inflate(R.layout.scan_result_item_new, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.mSsidTv = convertView.findViewById(R.id.ssid_tv);
        viewHolder.img_level = convertView.findViewById(R.id.img_wifi_level);
        viewHolder.mLevelTv = convertView.findViewById(R.id.level_tv);
        viewHolder.mEncryptTv = convertView.findViewById(R.id.encrypt_tv);
//        TextView mConnectTv = ViewHolder.getView(convertView, R.id.connect_tv);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        WifiInfo mInfo = WifiUtil.getConnectedWifiInfo(mContext);
        if (getItem(position) != null) {
            viewHolder.mEncryptTv.setTextColor(mContext.getResources().getColor(R.color.mine_textcolor));
            viewHolder.mSsidTv.setTextColor(mContext.getResources().getColor(R.color.mine_textcolor));
            ScanResult mResult = (ScanResult) getItem(position);

            viewHolder.mSsidTv.setText(mResult.SSID);
            viewHolder.mLevelTv.setText(mResult.level + "");
            if (mResult.level > -55) {
                viewHolder.img_level.setBackgroundResource(R.mipmap.wifi_leavel11);
            } else if (mResult.level > -70 && mResult.level <= -50) {
                viewHolder.img_level.setBackgroundResource(R.mipmap.wifi_leavel22);
            } else if (mResult.level > -85 && mResult.level <= -70) {
                viewHolder.img_level.setBackgroundResource(R.mipmap.wifi_leavel33);
            } else if (mResult.level > -100 && mResult.level <= -80) {
                viewHolder.img_level.setBackgroundResource(R.mipmap.wifi_leavel44);
            } else if (mResult.level <= -100) {
                viewHolder.img_level.setBackgroundResource(R.mipmap.wifi_leavel55);
            }


            viewHolder.mEncryptTv.setText(WifiUtil.getEncryptString(mResult.capabilities));

            if ("OPEN".equals(WifiUtil.getEncryptString(mResult.capabilities))) {
                viewHolder.mEncryptTv.setText("开放");
            } else {
                viewHolder.mEncryptTv.setText("安全");
            }

            if (WifiDatabaseUtils.isExisted(mResult.SSID)) {
                viewHolder.mEncryptTv.setText("已保存");
            }

            if (mInfo != null) {
                if (mInfo.getSSID() != null && (mInfo.getSSID().equals(mResult.SSID) || mInfo.getSSID().equals("\"" + mResult.SSID + "\""))) {

//                    mConnectTv.setVisibility(View.VISIBLE);

                    int Ip = mInfo.getIpAddress();

//                    Log.d(TAG, "ip = " + Ip);

                    String strIp = "" + (Ip & 0xFF) + "." + ((Ip >> 8) & 0xFF) + "." + ((Ip >> 16) & 0xFF) + "." + ((Ip >> 24) & 0xFF);
                    if (mInfo.getBSSID() != null && mInfo.getSSID() != null /*&& WifiDatabaseUtils.isExisted(mInfo.getSSID())*/ && strIp != null && !strIp.equals("0.0.0.0")) {
                        viewHolder.mEncryptTv.setText(lianjie_content);
                        viewHolder.mEncryptTv.setTextColor(mContext.getResources().getColor(R.color.blue3));
                        viewHolder.mSsidTv.setTextColor(mContext.getResources().getColor(R.color.blue3));
//                        WifiSettingFragment.getInstance().updateWifiListView(mResult, position);
                        //TODO not get pwd
//                        WifiDatabaseUtils.save(mResult.SSID, mResult.capabilities, mEdPwd.getText().toString());
                        if (sendBroadcastCount <= 3) {
                            mContext.sendBroadcast(new Intent("com.haiersmart.app.network_connected"));
                            sendBroadcastCount++;
                        }

                    } else {
                        viewHolder.mEncryptTv.setText(lianjie_content);
                        viewHolder.mEncryptTv.setTextColor(mContext.getResources().getColor(R.color.blue3));
                        viewHolder.mSsidTv.setTextColor(mContext.getResources().getColor(R.color.blue3));
//                        WifiSettingFragment.getInstance().updateWifiListView(mResult, position);
                        if (!WifiDatabaseUtils.isExisted(mResult.SSID)) {
                            if ("OPEN".equals(WifiUtil.getEncryptString(mResult.capabilities))) {
                                viewHolder.mEncryptTv.setText("开放");
                            } else {
                                viewHolder.mEncryptTv.setText("安全");
                            }
                            viewHolder.mEncryptTv.setTextColor(mContext.getResources().getColor(R.color.mine_textcolor));
                            viewHolder.mSsidTv.setTextColor(mContext.getResources().getColor(R.color.mine_textcolor));
                        }
                    }
                } else {
//                    mConnectTv.setVisibility(View.GONE);
                }
            } else {
//                mConnectTv.setVisibility(View.GONE);
            }

        }

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mSsidTv;//
        private ImageView img_level;//ImgUrl
        private TextView mLevelTv;//
        private TextView mEncryptTv;//

        ViewHolder(View view) {
            super(view);
        }
    }

    /**
     * ItemClick的回调接口
     *
     * @author geek
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public String formatPrice2(double price) {
        DecimalFormat df = new DecimalFormat("######0.00");

        return df.format(price);
    }
}