package xyz.doikki.dkplayer.fragment.main;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.pip.AndroidOPiPActivityDk;
import xyz.doikki.dkplayer.activity.pip.PIPActivityDk;
import xyz.doikki.dkplayer.activity.pip.PIPListActivityDk;
import xyz.doikki.dkplayer.activity.pip.TinyScreenActivityDk;
import xyz.doikki.dkplayer.fragment.BaseFragmentDk;

public class PipFragmentDk extends BaseFragmentDk implements View.OnClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_pipdk;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.btn_pip).setOnClickListener(this);
        findViewById(R.id.btn_pip_in_list).setOnClickListener(this);
        findViewById(R.id.btn_pip_android_o).setOnClickListener(this);
        findViewById(R.id.btn_tiny_screen).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_pip) {
            startActivity(new Intent(getActivity(), PIPActivityDk.class));
        } else if (id == R.id.btn_pip_in_list) {
            startActivity(new Intent(getActivity(), PIPListActivityDk.class));
        } else if (id == R.id.btn_pip_android_o) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startActivity(new Intent(getActivity(), AndroidOPiPActivityDk.class));
            } else {
                Toast.makeText(getActivity(), "Android O required.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.btn_tiny_screen) {
            startActivity(new Intent(getActivity(), TinyScreenActivityDk.class));
        }
    }
}
