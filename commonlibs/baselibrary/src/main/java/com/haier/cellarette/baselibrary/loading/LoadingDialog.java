package com.haier.cellarette.baselibrary.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import com.haier.cellarette.baselibrary.zothers.NavigationBarUtil;

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        // Dialog 在初始化时会生成新的 Window，先禁止 Dialog Window 获取焦点，等 Dialog 显示后对 Dialog Window 的 DecorView 设置 setSystemUiVisibility ，接着再获取焦点。 这样表面上看起来就没有退出沉浸模式。
        // Set the dialog to not focusable (makes navigation ignore us adding the window)
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        //Show the dialog!
        super.show();

        //Set the dialog to immersive
        NavigationBarUtil.fullScreenImmersive(getWindow().getDecorView());

        //Clear the not focusable flag from the window
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
