package com.fosung.xuanchuanlan.xuanchuanlan.localprofile.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fosung.xuanchuanlan.R;
import com.fosung.xuanchuanlan.common.base.ActivityParam;
import com.fosung.xuanchuanlan.common.base.BaseActivity;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment.CenterInfoFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment.OrgStructureFragment;
import com.fosung.xuanchuanlan.xuanchuanlan.localprofile.fragment.ServiceTeamFragment;

import java.util.ArrayList;
import java.util.List;

@ActivityParam(isShowToolBar = false)
public class LocalProfileActivity extends BaseActivity {
    //当前显示的fragment
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";

    private FragmentManager fragmentManager;

    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    private String[] typeList = {"中心简介","组织架构","服务团队"};
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_profile);
        mRadioGroup = (RadioGroup) findViewById(R.id.id_notice_radiogroup);

        addRadioButtons();
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) { // “内存重启”时调用

            //获取“内存重启”时保存的索引下标
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT,0);

            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));

            //恢复fragment页面
            restoreFragment();


        }else{      //正常启动时调用

            fragments.add(new CenterInfoFragment());
            fragments.add(new OrgStructureFragment());
            fragments.add(new ServiceTeamFragment());

            showFragment();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //“内存重启”时保存当前的fragment名字
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    //动态添加视图
    public void addRadioButtons(){

        int index = 0;
        for(String name:typeList){
            setRaidBtnAttribute(name,index);
            index++;
        }

    }

    private void setRaidBtnAttribute(String typename, final int id){

        final RadioButton radioButton = (RadioButton) LayoutInflater.from(this).inflate(R.layout.custom_radiobutton_layout,null);
        radioButton.setId( id );
        radioButton.setText(typename);
        if (id == 0) {
            radioButton.setChecked(true);
        }else {
            radioButton.setChecked(false);
        }

        radioButton.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                currentIndex = id;
                showFragment();

            }
        });

        mRadioGroup.addView(radioButton);

        if (id == typeList.length - 1) {
            return;
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) radioButton
                .getLayoutParams();
        layoutParams.setMargins(0, 0,  30, 0);
        radioButton.setLayoutParams(layoutParams);

    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(){

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //如果之前没有添加过
        if(!fragments.get(currentIndex).isAdded()){
            transaction
                    .hide(currentFragment)
                    .add(R.id.id_fragment_local,fragments.get(currentIndex),""+currentIndex);  //第三个参数为添加当前的fragment时绑定一个tag

        }else{
            transaction
                    .hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }

        currentFragment = fragments.get(currentIndex);

        transaction.commit();

    }

    /**
     * 恢复fragment
     */
    private void restoreFragment(){

        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {

            if(i == currentIndex){
                mBeginTreansaction.show(fragments.get(i));
            }else{
                mBeginTreansaction.hide(fragments.get(i));
            }
        }

        mBeginTreansaction.commit();

        //把当前显示的fragment记录下来
        currentFragment = fragments.get(currentIndex);

    }
}
