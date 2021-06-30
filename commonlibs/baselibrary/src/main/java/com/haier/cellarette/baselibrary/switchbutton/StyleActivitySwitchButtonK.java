package com.haier.cellarette.baselibrary.switchbutton;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;

public class StyleActivitySwitchButtonK extends AppCompatActivity {

    private SwitchButtonK mFlymeSb, mMIUISb, mCustomSb, mDefaultSb, mSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styleswitchbuttonk);

        SwitchButtonK disableSb = (SwitchButtonK) findViewById(R.id.sb_disable_control);
        SwitchButtonK disableNoEventSb = (SwitchButtonK) findViewById(R.id.sb_disable_control_no_event);
        mFlymeSb = (SwitchButtonK) findViewById(R.id.sb_custom_flyme);
        mMIUISb = (SwitchButtonK) findViewById(R.id.sb_custom_miui);
        mCustomSb = (SwitchButtonK) findViewById(R.id.sb_custom);
        mDefaultSb = (SwitchButtonK) findViewById(R.id.sb_default);
        mSB = (SwitchButtonK) findViewById(R.id.sb_ios);

        if (disableSb != null) {
            disableSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mFlymeSb.setEnabled(isChecked);
                    mMIUISb.setEnabled(isChecked);
                    mCustomSb.setEnabled(isChecked);
                    mDefaultSb.setEnabled(isChecked);
                    mSB.setEnabled(isChecked);
                }
            });
        }
        if (disableNoEventSb != null) {
            disableNoEventSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mFlymeSb.setEnabled(isChecked);
                    mMIUISb.setEnabled(isChecked);
                    mCustomSb.setEnabled(isChecked);
                    mDefaultSb.setEnabled(isChecked);
                    mSB.setEnabled(isChecked);
                }
            });
            disableNoEventSb.setCheckedImmediatelyNoEvent(false);
        }
    }

}
