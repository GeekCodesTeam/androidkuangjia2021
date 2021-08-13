package com.example.slbapplogin.msg;

/**
 * Created by wq on 2018/10/8.
 * https://blog.csdn.net/mazhidong/article/details/72675828
 */

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 观察者对象
 */
public class SmsObserver extends ContentObserver {

    private Context mContext;
    private Handler mHandler;

    public SmsObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;

    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        Log.d("====main", "SMS has changed!");
        Log.d("====main", uri.toString());
        // 短信内容变化时，第一次调用该方法时短信内容并没有写入到数据库中,return
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        getValidateCode();//获取短信验证码
//        getValidateCode2();//获取短信验证码

    }

    /**
     * 获取短信验证码
     */
    private void getValidateCode2() {
        Cursor cursor = null;
        Uri inboxUri = Uri.parse("content://sms/inbox");
        // 读取收件箱中含有某关键词的短信
        ContentResolver contentResolver = mContext.getContentResolver();
        cursor = contentResolver.query(inboxUri, new String[]{
                        "_id", "address", "body", "read"}, "body like ? and read=?",
                new String[]{"%合象教育%", "0"}, "date desc");
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.moveToFirst()) {
                String smsbody = cursor
                        .getString(cursor.getColumnIndex("body"));
                String regEx = "[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(smsbody);
                String smsContent = m.replaceAll("").trim();
                if (!TextUtils.isEmpty(smsContent)) {
                    mHandler.obtainMessage(1, smsContent).sendToTarget();
                }
            }
        }
    }

    /**
     * 获取短信验证码
     * 这里拿不到小米手机里面分类的短信
     */
    private void getValidateCode() {
        String code = "";
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");//
        if (c != null) {
            if (c.moveToFirst()) {
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                //13162364720为发件人的手机号码
                /*if (!address.equals("15860526788")) {
                    return;
                }*/
                Log.d("====main", "发件人为:" + address + " ," + "短信内容为:" + body);

                Pattern pattern = Pattern.compile("(\\d{4})");
                Matcher matcher = pattern.matcher(body);

                if (matcher.find()) {
                    code = matcher.group(0);
                    Log.d("====main", "验证码为: " + code);
                    mHandler.obtainMessage(1, code).sendToTarget();
                }

            }
            c.close();
        }
    }
}
