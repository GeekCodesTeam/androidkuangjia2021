/**
 * Copyright 2016,Smart Haier.All rights reserved
 */
package mi;

import com.example.libbase.AndroidApplication;
import com.geek.libutils.app.AppUtils;

/**
 * <p class="note">File Note</p>
 * created by geek at 2017/6/6
 */
public class SearchApplication extends AndroidApplication {

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
    public void onHomePressed() {
        super.onHomePressed();
//        AddressSaver.addr = null;
    }
}
