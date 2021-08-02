package com.example.slbapplogin.msg;

import android.text.NoCopySpan;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class NoRefCopySpan extends ClickableSpan implements NoCopySpan {

    @Override
    public void onClick(@NonNull View widget) {

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
    }

}
