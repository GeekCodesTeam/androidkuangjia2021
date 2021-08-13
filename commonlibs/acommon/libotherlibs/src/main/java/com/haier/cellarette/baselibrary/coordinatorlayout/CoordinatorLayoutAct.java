package com.haier.cellarette.baselibrary.coordinatorlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;

public class CoordinatorLayoutAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayoutact);

    }

    public void BTN1(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct1.class));
    }

    public void BTN2(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct2.class));
    }

    public void BTN3(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct3.class));
    }

    public void BTN4(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct4.class));
    }

    public void BTN5(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct5.class));
    }

    public void BTN6(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct6.class));
    }

    public void BTN7(View view) {
        startActivity(new Intent(this, CoordinatorLayoutAct7.class));
    }


}