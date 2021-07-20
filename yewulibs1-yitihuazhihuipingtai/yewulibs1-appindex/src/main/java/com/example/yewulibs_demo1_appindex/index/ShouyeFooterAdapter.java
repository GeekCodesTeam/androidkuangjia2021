package com.example.yewulibs_demo1_appindex.index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yewulibs_demo1_appindex.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShouyeFooterAdapter extends RecyclerView.Adapter<ShouyeFooterAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ShouyeFooterBean> mratings;

    public ShouyeFooterAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mratings = new ArrayList<ShouyeFooterBean>();
    }

    public void setContacts(List<ShouyeFooterBean> ratings) {
        this.mratings = ratings;
    }

    public void addConstacts(List<ShouyeFooterBean> ratings) {
        this.mratings.addAll(ratings);
    }

    public List<ShouyeFooterBean> getMratings() {
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
        View view = inflater.inflate(R.layout.activity_shouye_footer_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.ll1 = view.findViewById(R.id.ll1);
        viewHolder.iv_imgurl = view.findViewById(R.id.iv_imgurl);
        viewHolder.tv_content1 = view.findViewById(R.id.tv_content1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final ShouyeFooterBean ratings = mratings.get(position);
        //设置图片bufen

//        GlideUtil.display(context, viewHolder.iv_imgurl, ratings.getSku_image(), GlideOptionsFactory.get(GlideOptionsFactory.Type.RADIUS));
//        Glide.with(context).load(ratings.getSku_image()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(viewHolder.iv_imgurl);
        viewHolder.tv_content1.setText(ratings.getText_content());
        if (ratings.isEnselect()) {
            //选中
            viewHolder.ll1.setBackgroundResource(R.drawable.backgroud_gradient2);
            viewHolder.iv_imgurl.setImageResource(ratings.getText_icon2());
            viewHolder.tv_content1.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            //未选中
            viewHolder.ll1.setBackgroundResource(R.drawable.backgroud_gradient22);
            viewHolder.iv_imgurl.setImageResource(ratings.getText_icon());
            viewHolder.tv_content1.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
        }

        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
                }
            });
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll1;//
        private ImageView iv_imgurl;//ImgUrl
        private TextView tv_content1;//

        ViewHolder(View view) {
            super(view);
        }
    }

    /**
     * ItemClick的回调接口
     *
     * @author geek
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public String formatPrice2(double price) {
        DecimalFormat df = new DecimalFormat("######0.00");

        return df.format(price);
    }
}