

package com.fosung.xuanchuanlan.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;
import com.fosung.frameutils.http.ZHttp;
import com.fosung.frameutils.http.response.ZFileResponse;
import com.fosung.frameutils.util.ToastUtil;
import com.fosung.xuanchuanlan.common.consts.PathConst;

import java.io.File;
import okhttp3.Call;
import okhttp3.Response;

public class UpdateMgr {

    /**
     * 下载App
     */
    public static void downLoadApp(final Activity activity, String url) {
        String fileName = "xuanchuanlan.apk";
        if (fileName != null) {
            ZHttp.downLoadFile(url, new ZFileResponse(PathConst.CACHE + fileName, activity, "正在下载……") {
                @Override
                public void onError(int code, Call call, Exception e) {
                    ToastUtil.toastShort("下载失败！");
                }

                @Override
                public void onSuccess(Response response, File resObj) {
//                    AppUtil.installBySys(App.APP_CONTEXT, resObj);
                    installDownloadApp(activity, resObj);
                }

                @Override
                public void onProgress(float progress, long total) {
                    super.onProgress(progress, total);
//                    setBarMsg("正在下载……" + (int) (progress * 100) + "/" + 100);
                    setBarMsg("正在下载……");
                }
            });
        } else {
            ToastUtil.toastShort("下载URL不合法");
        }
    }
    private static void installDownloadApp(Context mContext, File apkfile){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(mContext, AppUtils.getAppPackageName() +".provider", apkfile);  // 这个地方 关键
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            mContext.startActivity(install);
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                    "application/vnd.android.package-archive");
            mContext.startActivity(i);
        }
    }

    private static String getFileNameByUrl(String url) {
        String fileName = null;
        if (!TextUtils.isEmpty(url)) {
            try {
                int start = url.lastIndexOf(".com/") + 5;
                int end =  url.lastIndexOf("?");
                fileName = url.substring(start,end);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}
