package mi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;

/**
 * <p class="note">File Note</p>
 * Created by geek on 2017/9/13.
 */

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Intent it = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WelComeActivity");
        it.putExtra("wid", "53");
        it.putExtra("adname", "测试测试吧");
        it.putExtra("admid", "123");
        startActivity(it);
    }
}
