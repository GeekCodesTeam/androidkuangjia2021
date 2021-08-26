package mi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.plugins.proxy.ProxyActivity;

/**
 * <p class="note">File Note</p>
 * Created by geek on 2017/9/13.
 */

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
//        Intent it = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SearchListActivity");
//        it.putExtra("wid", "53");
//        it.putExtra("adname", "测试测试吧");
//        it.putExtra("admid", "123");
//        startActivity(it);
        //
        Intent intent = new Intent(LauncherActivity.this, ProxyActivity.class);
//        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);
        intent.putExtra("className", "com.example.slbappsearch.SearchListActivity");
        startActivity(intent);
    }
}
