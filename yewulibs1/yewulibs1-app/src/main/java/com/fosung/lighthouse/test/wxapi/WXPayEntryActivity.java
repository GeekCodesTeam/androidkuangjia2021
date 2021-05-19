package com.fosung.lighthouse.test.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

// 支付成功回调
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, "JPushShareUtils.APP_ID");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        BaseReq req1 = req;

    }

    @Override
    public void onResp(BaseResp resp) {
        BaseResp resp1 = resp;
        Log.d("--WXPayEntryActivity---", "onPayFinish, errCode = " + resp.errCode);
        if (resp1.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp1.errCode == BaseResp.ErrCode.ERR_OK) {
//                // 从绘本页面购买VIP
//                if (SPUtils.getInstance().getInt(CommonUtils.HUIBEN_PAYSUCCESS_TAG, -1) == CommonUtils.HUIBEN_PAYSUCCESS_TAG1) {
//                    // 跳转到VIP
//                    SPUtils.getInstance().put(CommonUtils.HUIBEN_PAYSUCCESS, CommonUtils.HUIBEN_PAYSUCCESS_TAG1);
//                    startActivity(new Intent("hs.act.slbapp.PaysuccessActivity"));
//                }
//                // 从绘本页面单本购买
//                else if (SPUtils.getInstance().getInt(CommonUtils.HUIBEN_PAYSUCCESS_TAG, -1) == CommonUtils.HUIBEN_PAYSUCCESS_TAG2) {
//                    // 跳出层 支付成功
//                    SPUtils.getInstance().put(CommonUtils.HUIBEN_PAYSUCCESS, CommonUtils.HUIBEN_PAYSUCCESS_TAG2);
//                } else {
//                    // 从VIP页面购买VIP
//                    startActivity(new Intent("hs.act.slbapp.PaysuccessActivity"));
//                }
                EventBus.getDefault().post("PAY_SUCCESS");
            } else {
                EventBus.getDefault().post("PAY_FAIL");
                ToastUtils.showLong("支付失败");
            }
        }

        finish();
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("提示");
////            builder.setMessage(getString(R.string.app_xbbyzbh, String.valueOf(resp.errCode)));
//            builder.setMessage("微信支付结果：" + resp.errCode);
//            builder.show();
//        }

    }
}