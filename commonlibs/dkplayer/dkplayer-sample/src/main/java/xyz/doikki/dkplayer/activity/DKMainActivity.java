package xyz.doikki.dkplayer.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.fragment.main.ApiFragmentDk;
import xyz.doikki.dkplayer.fragment.main.ExtensionFragmentDk;
import xyz.doikki.dkplayer.fragment.main.ListFragmentDk;
import xyz.doikki.dkplayer.fragment.main.PipFragmentDk;
import xyz.doikki.dkplayer.util.PIPManagerDk;
import xyz.doikki.dkplayer.util.TagDk;
import xyz.doikki.dkplayer.util.UtilsDk;
import xyz.doikki.dkplayer.util.cache.ProxyVideoCacheManagerDk;
import xyz.doikki.dkplayer.widget.videoview.ExoVideoViewDk;
import xyz.doikki.dkplayer.widget.videoview.IjkVideoViewDk;
import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory;
import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.AndroidMediaPlayerFactory;
import xyz.doikki.videoplayer.player.PlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DKMainActivity extends BaseActivityDk implements BottomNavigationView.OnNavigationItemSelectedListener {

    private List<Fragment> mFragments = new ArrayList<>();
    public static int mCurrentIndex;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dkmain;
    }

    @Override
    protected boolean enableBack() {
        return false;
    }

    @Override
    protected void initView() {
        super.initView();
        //播放器配置，注意：此为全局配置，按需开启
        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                .setLogEnabled(true) //调试的时候请打开日志，方便排错
                /** 软解，支持格式较多，可通过自编译so扩展格式，结合 {@link IjkVideoViewDk} 使用更佳 */
                .setPlayerFactory(IjkPlayerFactory.create())
//                .setPlayerFactory(AndroidMediaPlayerFactory.create()) //不推荐使用，兼容性较差
                /** 硬解，支持格式看手机，请使用CpuInfoActivity检查手机支持的格式，结合 {@link ExoVideoViewDk} 使用更佳 */
//                .setPlayerFactory(ExoMediaPlayerFactory.create())
                // 设置自己的渲染view，内部默认TextureView实现
//                .setRenderViewFactory(SurfaceRenderViewFactory.create())
                // 根据手机重力感应自动切换横竖屏，默认false
//                .setEnableOrientation(true)
                // 监听系统中其他播放器是否获取音频焦点，实现不与其他播放器同时播放的效果，默认true
//                .setEnableAudioFocus(false)
                // 视频画面缩放模式，默认按视频宽高比居中显示在VideoView中
//                .setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT)
                // 适配刘海屏，默认true
//                .setAdaptCutout(false)
                // 移动网络下提示用户会产生流量费用，默认不提示，
                // 如果要提示则设置成false并在控制器中监听STATE_START_ABORT状态，实现相关界面，具体可以参考PrepareView的实现
//                .setPlayOnMobileNetwork(false)
                // 进度管理器，继承ProgressManager，实现自己的管理逻辑
//                .setProgressManager(new ProgressManagerImpl())
                .build());
        //
        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .start();

        //检测当前是用的哪个播放器
        Object factory = UtilsDk.getCurrentPlayerFactory();
        if (factory instanceof ExoMediaPlayerFactory) {
            setTitle(getResources().getString(R.string.dkapp_name) + " (ExoPlayer)");
        } else if (factory instanceof IjkPlayerFactory) {
            setTitle(getResources().getString(R.string.dkapp_name) + " (IjkPlayer)");
        } else if (factory instanceof AndroidMediaPlayerFactory) {
            setTitle(getResources().getString(R.string.dkapp_name) + " (MediaPlayer)");
        } else {
            setTitle(getResources().getString(R.string.dkapp_name) + " (unknown)");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mFragments.add(new ApiFragmentDk());
        mFragments.add(new ListFragmentDk());
        mFragments.add(new ExtensionFragmentDk());
        mFragments.add(new PipFragmentDk());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content, mFragments.get(0))
                .commitAllowingStateLoss();

        mCurrentIndex = 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.close_float_window) {
            PIPManagerDk.getInstance().stopFloatWindow();
            PIPManagerDk.getInstance().reset();
        } else if (itemId == R.id.clear_cache) {
            if (ProxyVideoCacheManagerDk.clearAllCache(this)) {
                Toast.makeText(this, "清除缓存成功", Toast.LENGTH_SHORT).show();
            }
        } else if (itemId == R.id.cpu_info) {
            CpuInfoActivityDk.start(this);
        }

        if (itemId == R.id.ijk || itemId == R.id.exo || itemId == R.id.media) {
            //切换播放核心，不推荐这么做，我这么写只是为了方便测试
            VideoViewConfig config = VideoViewManager.getConfig();
            try {
                Field mPlayerFactoryField = config.getClass().getDeclaredField("mPlayerFactory");
                mPlayerFactoryField.setAccessible(true);
                PlayerFactory playerFactory = null;
                if (itemId == R.id.ijk) {
                    playerFactory = IjkPlayerFactory.create();
                    setTitle(getResources().getString(R.string.dkapp_name) + " (IjkPlayer)");
                } else if (itemId == R.id.exo) {
                    playerFactory = ExoMediaPlayerFactory.create();
                    setTitle(getResources().getString(R.string.dkapp_name) + " (ExoPlayer)");
                } else if (itemId == R.id.media) {
                    playerFactory = AndroidMediaPlayerFactory.create();
                    setTitle(getResources().getString(R.string.dkapp_name) + " (MediaPlayer)");
                }
                mPlayerFactoryField.set(config, playerFactory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int index = 0;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.tab_api) {
            index = 0;
        } else if (itemId == R.id.tab_list) {
            index = 1;
        } else if (itemId == R.id.tab_extension) {
            index = 2;
        } else if (itemId == R.id.tab_pip) {
            index = 3;
        }

        if (mCurrentIndex != index) {
            //切换tab，释放正在播放的播放器
            if (mCurrentIndex == 1) {
                getVideoViewManager().releaseByTag(TagDk.LIST);
                getVideoViewManager().releaseByTag(TagDk.SEAMLESS, false);//注意不能移除
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment fragment = mFragments.get(index);
            Fragment curFragment = mFragments.get(mCurrentIndex);
            if (fragment.isAdded()) {
                transaction.hide(curFragment).show(fragment);
            } else {
                transaction.add(R.id.layout_content, fragment).hide(curFragment);
            }
            transaction.commitAllowingStateLoss();
            mCurrentIndex = index;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getVideoViewManager().onBackPress(TagDk.LIST)) return;
        if (getVideoViewManager().onBackPress(TagDk.SEAMLESS)) return;
        super.onBackPressed();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
