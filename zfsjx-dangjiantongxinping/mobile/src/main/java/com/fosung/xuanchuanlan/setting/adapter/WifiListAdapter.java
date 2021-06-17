package com.fosung.xuanchuanlan.setting.adapter;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fosung.xuanchuanlan.R;

import java.util.List;

public class WifiListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<ScanResult> arrayList;
    public WifiListAdapter(Context context,int layout,List<ScanResult>array){
        this.arrayList = array;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder holder = null;
       ScanResult result = arrayList.get(position);
       if (convertView == null){
           convertView = LayoutInflater.from(context).inflate(layout,null);
       }else{
           holder = (ViewHolder)convertView.getTag();
       }
       holder = getHolder(convertView);
        holder.wifiName = (TextView) convertView.findViewById(R.id.wifiname);
        holder.setWifiNameText(result.SSID);
        return convertView;
    }


    public  ViewHolder getHolder(View view){
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null){
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }




    public class ViewHolder {
        public TextView wifiName;
        public ViewHolder(View view){
            wifiName = (TextView) view.findViewById(R.id.wifiname);
        }
        public void setWifiNameText(String text){
            this.wifiName.setText(text);
        }

    }



}
