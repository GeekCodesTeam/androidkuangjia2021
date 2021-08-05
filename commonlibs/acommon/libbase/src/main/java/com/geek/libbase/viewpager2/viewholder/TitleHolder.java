package com.geek.libbase.viewpager2.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.libbase.R;


public class TitleHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public TitleHolder(@NonNull View view) {
        super(view);
        title = view.findViewById(R.id.bannerTitle);
    }
}
