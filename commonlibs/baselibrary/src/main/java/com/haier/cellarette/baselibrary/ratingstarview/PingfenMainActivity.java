package com.haier.cellarette.baselibrary.ratingstarview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;

// https://github.com/everhad/AndroidRatingStar
//    api 'com.github.everhad:AndroidRatingStar:v1.0.4'

public class PingfenMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pingfen);

        RatingStarView rsv_rating = (RatingStarView) findViewById(R.id.rsv_rating);
        rsv_rating.setRating(1.5f);
    }
}