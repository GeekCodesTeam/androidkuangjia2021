package mi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.geek.appplugin.Main1Activity;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Intent it = new Intent(this, Main1Activity.class);
        it.putExtra("wid", "53");
        it.putExtra("adname", "测试测试吧");
        it.putExtra("admid", "123");
        startActivity(it);
    }
}
