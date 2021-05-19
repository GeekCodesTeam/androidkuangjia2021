package com.example.slbappcomm.pop.bottompay;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.slbappcomm.R;

import me.shaohui.bottomdialog.BottomDialog;

public class BottomPay {
    public void set(AppCompatActivity activity) {
        BottomDialog.create(activity.getSupportFragmentManager())//getSupportFragmentManager()
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        // // You can do any of the necessary the operation with the view
                    }
                })
                .setLayoutRes(R.layout.activity_bottom_pay)
                .setDimAmount(0.1f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                .setCancelOutside(false)     // click the external area whether is closed, default is : true
                .setTag("BottomPayDialog")     // setting the DialogFragment tag
                .show();
    }
}
