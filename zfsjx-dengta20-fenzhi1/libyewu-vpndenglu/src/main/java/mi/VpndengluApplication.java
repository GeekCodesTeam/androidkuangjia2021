/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package mi;

import android.content.Context;
import android.widget.Toast;

import com.geek.libbase.AndroidApplication;
import com.geek.libutils.app.AppUtils;
import com.sangfor.sdk.SFMobileSecuritySDK;
import com.sangfor.sdk.base.SFSDKFlags;
import com.sangfor.sdk.base.SFSDKMode;

/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class VpndengluApplication extends AndroidApplication {
    private static SFSDKMode mSDKMode;
    @Override
    public void onCreate() {
        super.onCreate();
        if (!AppUtils.isProcessAs(getPackageName(), this)) {
            return;
        }
        //TODO commonbufen
        configBugly("测试", "3aeeb18e5e");
        configHios();
        configmmkv();
        configShipei();
        configRetrofitNet();
        others();
        //TODO 业务bufen

    
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SFSDKMode sdkMode = SFSDKMode.MODE_VPN_SANDBOX;         //表明同时启用VPN功能+安全沙箱功能,详情参考集成指导文档

        switch (sdkMode) {
            case MODE_VPN: {//只使用VPN功能场景
                int sdkFlags =  SFSDKFlags.FLAGS_HOST_APPLICATION;      //表明是单应用或者是主应用
                sdkFlags |= SFSDKFlags.FLAGS_VPN_MODE_TCP;              //表明使用VPN功能中的TCP模式

                SFMobileSecuritySDK.getInstance().initSDK(base, sdkMode, sdkFlags, null);//初始化SDK
                break;
            }
            case MODE_SANDBOX: {//只使用安全沙箱功能场景
                int sdkFlags =  SFSDKFlags.FLAGS_HOST_APPLICATION;      //表明是单应用或者是主应用
                sdkFlags |= SFSDKFlags.FLAGS_ENABLE_FILE_ISOLATION;     //表明启用安全沙箱功能中的文件隔离功能

                SFMobileSecuritySDK.getInstance().initSDK(base, sdkMode, sdkFlags, null);//初始化SDK
                break;
            }
            case MODE_VPN_SANDBOX: { //同时使用VPN功能+安全沙箱功能场景
                int sdkFlags =  SFSDKFlags.FLAGS_HOST_APPLICATION;      //表明是单应用或者是主应用
                sdkFlags |= SFSDKFlags.FLAGS_VPN_MODE_TCP;              //表明使用VPN功能中的TCP模式
                sdkFlags |= SFSDKFlags.FLAGS_ENABLE_FILE_ISOLATION;     //表明启用安全沙箱功能中的文件隔离功能

                SFMobileSecuritySDK.getInstance().initSDK(base, sdkMode, sdkFlags, null);//初始化SDK
                break;
            }
            default: {
                Toast.makeText(base, "SDK模式错误", Toast.LENGTH_LONG).show();
                return;
            }
        }

        //setAuthTimeout(30);//设置SDK网络超时时间
        mSDKMode = sdkMode; //保存使用的sdk模式
    }

    @Override
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
