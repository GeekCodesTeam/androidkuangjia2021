package com.example.gsydemo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.blankj.utilcode.util.AppUtils;
import com.example.gsyvideoplayer.R;


public class JumpVideoPlayUtils {

    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";

    public static void goToPlayEmptyControlActivity(Activity activity, View view) {
        Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.SimplePlayer");
        intent.putExtra(TRANSITION, true);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Pair pair = new Pair<>(view, IMG_TRANSITION);
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair);
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        }
    }
}
