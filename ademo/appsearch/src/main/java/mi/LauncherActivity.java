package mi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Intent it = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SearchListActivity");
        it.putExtra("wid", "53");
        it.putExtra("adname", "测试测试吧");
        it.putExtra("admid", "123");
        startActivity(it);
    }
}
