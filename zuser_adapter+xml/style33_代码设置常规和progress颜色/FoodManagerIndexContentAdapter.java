package com.haiersmart.sfnation.ui.cookbook.foodmanagernew.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haiersmart.commonbizlib.glide.GlideOptionsFactory;
import com.haiersmart.commonbizlib.glide.GlideUtil;
import com.haiersmart.sfnation.R;
import com.haiersmart.sfnation.bizutils.ToastUtil;
import com.haiersmart.sfnation.constant.ConstantUtil;
import com.haiersmart.sfnation.domain.FmNewFoodItemBean;
import com.haiersmart.sfnation.ui.cookbook.foodmanagernew.FoodManagerIndexActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.haiersmart.sfnation.R.drawable.iv_sku_checked;
import static com.haiersmart.sfnation.R.drawable.iv_sku_unchecked;
import static com.haiersmart.sfnation.R.id.rlvis;
import static com.haiersmart.sfnation.R.id.tv_fm_choose;

public class FoodManagerIndexContentAdapter extends RecyclerView.Adapter<FoodManagerIndexContentAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<FmNewFoodItemBean> mratings;
    private Map<String, String> hashMap = new HashMap<String, String>();//批量删除过期bufen
    private Map<String, String> hashMapAllChoose = new HashMap<String, String>();//批量删除bufen
    private int selectIndex = -1;//当前选中的条目
    private boolean isallchoose;//全选的状态值bufen

    public void setSelectIndex(int i) {
        selectIndex = i;
    }

    public boolean isallchoose() {
        return isallchoose;
    }

    public void setIsallchoose(boolean isallchoose) {
        this.isallchoose = isallchoose;
    }

    public FoodManagerIndexContentAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mratings = new ArrayList<FmNewFoodItemBean>();
    }

    public void setContacts(List<FmNewFoodItemBean> ratings) {
        this.mratings = ratings;
    }

    public void addConstacts(List<FmNewFoodItemBean> ratings) {
        this.mratings.addAll(ratings);
    }

    public List<FmNewFoodItemBean> getMratings() {
        return mratings;
    }

    @Override
    public int getItemCount() {
        if (mratings == null)
            return 0;
        return mratings.size();
    }

    public Object getItem(int position) {
        return mratings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_foodmanager_new_food_item_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.tv_fm_tips = (TextView) view.findViewById(R.id.tv_fm_tips);
        viewHolder.tv_fm_name = (TextView) view.findViewById(R.id.tv_fm_name);
        viewHolder.pb = (ProgressBar) view.findViewById(R.id.pb);
        viewHolder.rl_fm_choose = (RelativeLayout) view.findViewById(R.id.rl_fm_choose);
        viewHolder.rlvis = (RelativeLayout) view.findViewById(rlvis);
        viewHolder.iv_fm_img = (ImageView) view.findViewById(R.id.iv_fm_img_content);
        viewHolder.tv_fm_choose = (TextView) view.findViewById(tv_fm_choose);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final FmNewFoodItemBean ratings = mratings.get(position);
        GlideUtil.display(context, viewHolder.iv_fm_img, ratings.getFood_image(), GlideOptionsFactory.get(GlideOptionsFactory.Type.DEFAULT));
        //Rfidbufen
        viewHolder.tv_fm_name.setText(ratings.getFood_name());
        if (ratings.getFood_rfid() != null && !ratings.getFood_rfid().equals("")) {
            //显示rfidbufen
            Drawable drawable = context.getResources().getDrawable(R.drawable.rfid_icons);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            viewHolder.tv_fm_name.setCompoundDrawables(drawable, null, null, null);
        } else {
            //隐藏rfidbufen
            viewHolder.tv_fm_name.setCompoundDrawables(null, null, null, null);
        }
        //收集过期idbufen
        if (ratings.getFood_time_label().equals("已过期") /*&& (ratings.getFood_rfid() == null || ratings.getFood_rfid().equals(""))*/) {
            hashMap.put(ratings.getFridge_food_id(), ratings.getFridge_food_id());
        }
        //判断是否显示清除all
        if (!isallchoose) {
            mOnItemClickLitener2.onItemClick2(getfoodstr(hashMap) != null && !getfoodstr(hashMap).equals(""));
        }
        //显示过期tipsbufen
        if (ratings.getFood_time_label() != null && !ratings.getFood_time_label().equals("")) {
            viewHolder.pb.setVisibility(View.GONE);
            viewHolder.tv_fm_tips.setVisibility(View.VISIBLE);
            //显示tipsbufen
            viewHolder.tv_fm_tips.setTextSize(15.0f);
            viewHolder.tv_fm_tips.setText(ratings.getFood_time_label());
            viewHolder.tv_fm_tips.setBackground(context.getResources().getDrawable(R.drawable.hongbao_color));
            viewHolder.tv_fm_tips.setTag(false);
            //viewHolder.tv_fm_tips.setPadding(5, 1, 5, 1);
            //context.getResources().getColor(R.color.orange_color)
            viewHolder.tv_fm_tips.setTextColor(context.getResources().getColor(R.color.white));//Color.parseColor("#FFFFFF")
            // tag.getCoupon_color().substring(1)= #FFFFFF  tag.getCoupon_color().substring(1)
            int color = Integer.parseInt(ratings.getTag_color().substring(1), 16);//tag.getCoupon_color().substring(1)  FF5001
            color = ConstantUtil.COLOR_SIXTEEN + color;
            viewHolder.tv_fm_tips.getBackground().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        } else {
            //显示新鲜度bufen
            if (ratings.getFood_fresh_rate() != null && !ratings.getFood_fresh_rate().equals("")) {
                viewHolder.pb.setVisibility(View.VISIBLE);
                viewHolder.pb.setProgressDrawable(context.getResources().getDrawable(R.drawable.layer_list_progress_drawable));
                int color = Integer.parseInt(ratings.getTag_color().substring(1), 16);//tag.getCoupon_color().substring(1)  FF5001
                color = ConstantUtil.COLOR_SIXTEEN + color;
                LayerDrawable drawable = (LayerDrawable) viewHolder.pb.getProgressDrawable();
                drawable.getDrawable(1).setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
                viewHolder.pb.setProgress(Integer.valueOf(ratings.getFood_fresh_rate()));
            }
            viewHolder.tv_fm_tips.setVisibility(View.GONE);
        }
        //设置显示选中框颜色
        viewHolder.rl_fm_choose.setBackground(ContextCompat.getDrawable(context, R.drawable.hongbao_color2));
        if (ratings.getTag_color() != null && !ratings.getTag_color().equals("")) {
            int color2 = Integer.parseInt(ratings.getTag_color().substring(1), 16);//tag.getCoupon_color().substring(1)  FF5001
            color2 = ConstantUtil.COLOR_SIXTEEN + color2;
            viewHolder.rl_fm_choose.getBackground().setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_IN));
        } else {
            int color2 = Integer.parseInt("#000000".substring(1), 16);//tag.getCoupon_color().substring(1)  FF5001
            color2 = ConstantUtil.COLOR_SIXTEEN + color2;
            viewHolder.rl_fm_choose.getBackground().setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_IN));
        }
        //当前位置选中，其他不选中
        if (position == selectIndex) {
            viewHolder.rl_fm_choose.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rl_fm_choose.setVisibility(View.GONE);
        }
        //设置对号显示bufen
        if (ratings.ischoose()) {
            //设置选中
            viewHolder.tv_fm_choose.setBackgroundResource(iv_sku_checked);
        } else {
            //设置未选中
            viewHolder.tv_fm_choose.setBackgroundResource(iv_sku_unchecked);
        }
        //全选状态如何显示对号bufen
        if (isallchoose) {
            //非RFID和RFID显示bufen
            viewHolder.rlvis.setVisibility(View.VISIBLE);
        } else {
            //设置隐藏对号bufen
            viewHolder.rlvis.setVisibility(View.GONE);
        }
        //TODO 点击对号bufen
        viewHolder.rlvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FmNewFoodItemBean ratings = mratings.get(position);
                if (ratings.ischoose()) {
                    //已选
                    ratings.setIschoose(false);
                    viewHolder.tv_fm_choose.setBackgroundResource(iv_sku_unchecked);
                    removeAllfoodid(ratings.getFridge_food_id());
                } else {
                    //未选
                    ratings.setIschoose(true);
                    viewHolder.tv_fm_choose.setBackgroundResource(iv_sku_checked);
                    addAllfoodid(ratings.getFridge_food_id());
                }
                //new
                layout_is_vis(0);
//                ToastUtil.showToastCenter(getAllString_ids());
            }
        });
        //TODO 长按itembufen
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //初始化选择弹出层和item选中状态bufen
                ((FoodManagerIndexActivity) context).quanxuanchushihua();
                if (isallchoose) {
                    //清空idsbufen
                    setAll_ids();
                    //设置取消对号bufen
                    for (FmNewFoodItemBean ratings : mratings) {
                        ratings.setIschoose(false);
                    }
                    layout_is_vis(1);
                    //设置取消选中
                    isallchoose = false;
                } else {
                    //new
                    for (FmNewFoodItemBean ratings : mratings) {
                        ratings.setIschoose(false);
                    }
                    isallchoose = true;
                }
                notifyDataSetChanged();
                return false;
            }
        });
        viewHolder.rlvis.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //初始化选择弹出层和item选中状态bufen
                ((FoodManagerIndexActivity) context).quanxuanchushihua();
                if (isallchoose) {
                    //清空idsbufen
                    setAll_ids();
                    //设置取消对号bufen
                    for (FmNewFoodItemBean ratings : mratings) {
                        ratings.setIschoose(false);
                    }
                    layout_is_vis(1);
                    //设置取消选中
                    isallchoose = false;
                } else {
                    //new
                    for (FmNewFoodItemBean ratings : mratings) {
                        ratings.setIschoose(false);
                    }
                    isallchoose = true;
                }
                notifyDataSetChanged();
//                ToastUtil.showToastCenter(getAllString_ids());
                return false;
            }
        });

        //如果设置了回调，则设置点击事件 点击后显示食材弹出层bufen
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position, isallchoose);//ratings.ischoose()
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_fm_tips;
        TextView tv_fm_name;
        ProgressBar pb;
        RelativeLayout rl_fm_choose;
        RelativeLayout rlvis;
        ImageView iv_fm_img;
        TextView tv_fm_choose;

        ViewHolder(View view) {
            super(view);
        }
    }

    /**
     * ItemClick的回调接口1 点击后显示食材弹出层bufen
     *
     * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position, boolean isallchoose);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * ItemClick的回调接口2 获取hashMap 过期食材ids 有值true 显示清除过期食材按钮 空false 隐藏清除过期食材按钮 bufen
     *
     * @author zhy
     */
    public interface OnItemClickLitener2 {
        void onItemClick2(boolean isHaveMapId);
    }

    private OnItemClickLitener2 mOnItemClickLitener2;

    public void setOnItemClickLitener2(OnItemClickLitener2 mOnItemClickLitener2) {
        this.mOnItemClickLitener2 = mOnItemClickLitener2;
    }

    /**
     * 获取过期foodid String
     *
     * @return
     */
    public String getGuoqi_ids() {
        if (hashMap == null || hashMap.size() == 0) {
            return null;
        }
        return getfoodstr(hashMap);
    }

    /**
     * 获取过期foodid map
     *
     * @return
     */
    public Map<String, String> getfoodids() {
        return hashMap;
    }

    /**
     * 清除过期foodid String
     *
     * @return
     */
    public void setGuoqi_ids() {
        if (hashMap != null || hashMap.size() > 0) {
            hashMap.clear();
        }
    }

    public void addfoodid(String id) {
        hashMap.put(id, id);
    }

    public void removefoodid(String id) {
        hashMap.remove(id);
    }

    public String getfoodstr(Map<String, String> ids) {
        StringBuilder result = new StringBuilder();
        if (ids != null && ids.size() > 0) {
            int i = 0;
            for (String id : ids.keySet()) {
                if (i == 0) {
                    result.append(id);
                } else {
                    result.append("," + id);
                }
                i++;
            }
        } else {

        }
        return result.toString();
    }

    /**
     * 获取所有foodid String
     *
     * @return
     */
    public String getAllString_ids() {
        if (hashMapAllChoose == null || hashMapAllChoose.size() == 0) {
            return null;
        }
        return getAllfoodstr(hashMapAllChoose);
    }

    /**
     * 获取所有foodid map
     *
     * @return
     */
    public Map<String, String> getAllMapids() {
        return hashMapAllChoose;
    }

    /**
     * 清除所有foodid String
     *
     * @return
     */
    public void setAll_ids() {
        if (hashMapAllChoose != null || hashMapAllChoose.size() > 0) {
            hashMapAllChoose.clear();
        }
    }

    public void addAllfoodid(String id) {
        hashMapAllChoose.put(id, id);
    }

    public void removeAllfoodid(String id) {
        hashMapAllChoose.remove(id);
    }

    public String getAllfoodstr(Map<String, String> ids) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String id : ids.keySet()) {
            if (i == 0) {
                result.append(id);
            } else {
                result.append("," + id);
            }
            i++;
        }
        return result.toString();
    }

    /**
     * 根据条件判断显示全选布局还是tips布局bufen
     *
     * @param is_quanxuan_or_tips = 0 both 1 全选 2 tips
     */
    private void layout_is_vis(int is_quanxuan_or_tips) {
        if (is_quanxuan_or_tips == 0) {
            if (getAllString_ids() != null && !getAllString_ids().equals("")) {
                ((FoodManagerIndexActivity) context).isallchoosedelete(true);//设置显示全选布局bufen
                ((FoodManagerIndexActivity) context).isalldeleteclear(false);//设置隐藏清除已过期布局bufen
            } else {
                //设置隐藏全选布局bufen
                ((FoodManagerIndexActivity) context).isallchoosedelete(false);
                //如果没有过期的食材 显示tips 有则 显示 清除已过期食材 bufen
                if (getGuoqi_ids() != null && !getGuoqi_ids().equals("")) {
                    ((FoodManagerIndexActivity) context).isalldeleteclear(true);//设置显示清除已过期布局bufen
                } else {
                    ((FoodManagerIndexActivity) context).isalldeletecleartips(true);//设置显示tips布局bufen
                }
            }
        } else if (is_quanxuan_or_tips == 1) {
            //设置隐藏全选布局bufen
            ((FoodManagerIndexActivity) context).isallchoosedelete(false);
            //如果没有过期的食材 显示tips 有则 显示 清除已过期食材 bufen
            if (getGuoqi_ids() != null && !getGuoqi_ids().equals("")) {
                ((FoodManagerIndexActivity) context).isalldeleteclear(true);//设置显示清除已过期布局bufen
            } else {
                ((FoodManagerIndexActivity) context).isalldeletecleartips(true);//设置显示tips布局bufen
            }
        } else if (is_quanxuan_or_tips == 2) {
            //如果全选id收集不为空则为可全选状态 显示布局 隐藏 清除已过期食材布局 bufen
            if (getAllString_ids() != null && !getAllString_ids().equals("")) {
                //设置选中
                isallchoose = true;
                ((FoodManagerIndexActivity) context).isallchoosedelete(true);//设置显示全选布局bufen
                ((FoodManagerIndexActivity) context).isalldeleteclear(false);//设置隐藏清除已过期布局bufen
            } else {
                //如果全选id收集为空则为RFID食材 不可全选状态
                isallchoose = false;
                ToastUtil.showToastCenter("RFID食材不可删除！");
            }
        }
    }


}