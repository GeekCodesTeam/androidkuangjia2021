//package com.fosung.xuanchuanlan.common.util;
//
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.fosung.xuanchuanlan.R;
////import com.yuntongxun.plugin.common.RongXinApplicationContext;
////import com.yuntongxun.plugin.common.common.utils.GlideHelper;
////import com.yuntongxun.plugin.common.common.utils.TextUtil;
////import com.yuntongxun.plugin.login.dao.DBContactProfilesTools;
////import com.yuntongxun.plugin.login.dao.bean.ContactProfiles;
//
//
//public class AvatarMangerUtils {
//
//    /**
//     *
//     * @param name
//     * @param memberID
//     */
//    public static void LoadNameTv(final TextView name, final String memberID){
//        LoadNameTv(name,memberID,"","");
//    }
//
//    /**
//     * 加载用户名称
//     * @param name
//     * @param memberID
//     * @param preText 前缀字符串
//     * @param appendText 后缀字符串
//     */
//    public static void LoadNameTv(final TextView name, final String memberID, final String preText, final String appendText){
//        ContactProfiles bean = DBContactProfilesTools.getInstance().queryClientInfo(memberID);
//        if(bean!=null){
//            if(name!=null)name.setText(preText+bean.getNickName()+appendText);
//        }else{
//            if(name!=null)name.setText(memberID);
//        }
//    }
//
//    /**
//     *
//     * @param avatar
//     * @param memberID
//     */
//    public static void LoadImgeViewAvatar(final ImageView avatar, final String memberID){
//        ContactProfiles bean = DBContactProfilesTools.getInstance().queryClientInfo(memberID);
//        if(bean!=null && avatar!=null && !TextUtil.isEmpty(bean.getPhotoUrl())){
//            GlideHelper.displayNormalPhoto(RongXinApplicationContext.getContext(),bean.getPhotoUrl(), avatar, R.drawable.newconf_setting_person);
//
//        }else{
//            if(avatar!=null){
//                GlideHelper.displayDefault(avatar,memberID,memberID);
//            }
//        }
//
//    }
//}
