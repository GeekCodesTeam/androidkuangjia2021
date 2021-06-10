package xyz.doikki.dkplayer.fragment.main;

import android.content.Intent;
import android.view.View;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.extend.DefinitionPlayerActivityDk;
import xyz.doikki.dkplayer.activity.extend.ADActivityDk;
import xyz.doikki.dkplayer.activity.extend.CacheActivityDk;
import xyz.doikki.dkplayer.activity.extend.CustomExoPlayerActivityDk;
import xyz.doikki.dkplayer.activity.extend.CustomIjkPlayerActivityDk;
import xyz.doikki.dkplayer.activity.extend.DanmakuActivityDk;
import xyz.doikki.dkplayer.activity.extend.FullScreenActivityDk;
import xyz.doikki.dkplayer.activity.extend.PadActivityDk;
import xyz.doikki.dkplayer.activity.extend.PlayListActivityDk;
import xyz.doikki.dkplayer.fragment.BaseFragmentDk;

public class ExtensionFragmentDk extends BaseFragmentDk implements View.OnClickListener {
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_extensiondk;
    }

    @Override
    protected void initView() {
        super.initView();
        findViewById(R.id.btn_fullscreen).setOnClickListener(this);
        findViewById(R.id.btn_danmu).setOnClickListener(this);
        findViewById(R.id.btn_ad).setOnClickListener(this);
        findViewById(R.id.btn_proxy_cache).setOnClickListener(this);
        findViewById(R.id.btn_play_list).setOnClickListener(this);
        findViewById(R.id.btn_pad).setOnClickListener(this);
        findViewById(R.id.btn_custom_exo_player).setOnClickListener(this);
        findViewById(R.id.btn_custom_ijk_player).setOnClickListener(this);
        findViewById(R.id.btn_definition).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_fullscreen) {
            startActivity(new Intent(getActivity(), FullScreenActivityDk.class));
        } else if (id == R.id.btn_danmu) {
            startActivity(new Intent(getActivity(), DanmakuActivityDk.class));
        } else if (id == R.id.btn_ad) {
            startActivity(new Intent(getActivity(), ADActivityDk.class));
        } else if (id == R.id.btn_proxy_cache) {
            startActivity(new Intent(getActivity(), CacheActivityDk.class));
        } else if (id == R.id.btn_play_list) {
            startActivity(new Intent(getActivity(), PlayListActivityDk.class));
        } else if (id == R.id.btn_pad) {
            startActivity(new Intent(getActivity(), PadActivityDk.class));
        } else if (id == R.id.btn_custom_exo_player) {
            startActivity(new Intent(getActivity(), CustomExoPlayerActivityDk.class));
        } else if (id == R.id.btn_custom_ijk_player) {
            startActivity(new Intent(getActivity(), CustomIjkPlayerActivityDk.class));
        } else if (id == R.id.btn_definition) {
            startActivity(new Intent(getActivity(), DefinitionPlayerActivityDk.class));
        }
    }
}
