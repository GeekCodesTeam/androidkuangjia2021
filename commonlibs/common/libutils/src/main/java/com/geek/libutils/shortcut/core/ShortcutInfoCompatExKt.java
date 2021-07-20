package com.geek.libutils.shortcut.core;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.pm.ShortcutInfoCompat.Builder;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.geek.libutils.shortcut.ImageUtils;

public final class ShortcutInfoCompatExKt {

    public static final Builder setIcon(Builder $this$setIcon, Bitmap bitmap, Context context, boolean sharpWithLauncher) {
        if (sharpWithLauncher && canSharpWithLauncher()) {
            $this$setIcon.setIcon(IconCompat.createWithBitmap(ImageUtils.merge(bitmap, context)));
        } else {
            $this$setIcon.setIcon(IconCompat.createWithBitmap(bitmap));
        }

        return $this$setIcon;
    }

    // $FF: synthetic method
    public static Builder setIcon$default(Builder var0, Bitmap var1, Context var2, boolean var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            var3 = true;
        }

        return setIcon(var0, var1, var2, var3);
    }

    private static final boolean canSharpWithLauncher() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static final Builder setIcon(Builder $this$setIcon, Drawable drawable, Context context, boolean sharpWithLauncher) {
        Bitmap drawable2Bitmap = ImageUtils.drawable2Bitmap(drawable);
        return setIcon($this$setIcon, drawable2Bitmap, context, sharpWithLauncher);
    }

    public static final Builder setIcon(Builder $this$setIcon, int drawableIds, Context context, boolean sharpWithLauncher) {
        Resources var10000 = context.getResources();
        Context var10002 = context.getApplicationContext();
        Drawable drawable = ResourcesCompat.getDrawable(var10000, drawableIds, var10002.getTheme());
        Bitmap drawable2Bitmap = ImageUtils.drawable2Bitmap(drawable);
        return setIcon($this$setIcon, drawable2Bitmap, context, sharpWithLauncher);
    }

    public static final Builder setIntent(Builder $this$setIntent, Intent intent, String action) {
        if (TextUtils.isEmpty((CharSequence) intent.getAction())) {
            intent.setAction(action);
        }
        Builder var10000 = $this$setIntent.setIntent(intent);
        return var10000;
    }
}
