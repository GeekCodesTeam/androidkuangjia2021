package com.example.slbappcomm.saoma.demo1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.slbappcomm.R;
import com.king.zxing.CaptureFragment;

/**
 * Fragment扫码
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CaptureFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saoma1_fragment_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        SaomaStatusBarUtils.immersiveStatusBar(this,toolbar,0.2f);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getIntent().getStringExtra(SaomaAct.KEY_TITLE));

        replaceFragment(CaptureFragment.newInstance());
    }

    public void replaceFragment(Fragment fragment){
        replaceFragment( R.id.fragmentContent,fragment);
    }

    public void replaceFragment(@IdRes int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(id, fragment).commit();
    }

    public void onClick(View v){
        if (v.getId() == R.id.ivLeft) {
            onBackPressed();
        }
    }
}
