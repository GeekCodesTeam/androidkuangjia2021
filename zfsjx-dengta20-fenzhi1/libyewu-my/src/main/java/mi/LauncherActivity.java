package mi;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;

/**
 * <p class="note">File Note</p>
 * Created by geek on 2020/9/13.
 */

public class LauncherActivity extends AppCompatActivity {


    @Override
    public void onStart() {
        super.onStart();
        // 方法1
        Intent it = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SettingAct");
        it.putExtra("launch", true);
        it.putExtra("query1", "pc");
        it.putExtra("query2", "45464");
        it.putExtra("query3", "aaaa");
        startActivity(it);
        // 方法2
//        Intent intent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("dataability://cs.znclass.com/" +
//                        "com.fosung.lighthouse.myapplication1.hs.act.slbapp.Myapplication1Act?query3=aaaa&query2=45464&query1=pc"));
//        startActivity(intent);
        // 方法3
//        cmd
//        adb shell
//        am start -a android.intent.action.VIEW   -c android.intent.category.BROWSABLE  -d "dataability://cs.znclass.com/com.fosung.lighthouse.myapplication1.hs.act.slbapp.Myapplication1Act?query3=aaaa&query2=45464&query1=pc"
        finish();// 需要循环添加 不需要去掉
    }

}
