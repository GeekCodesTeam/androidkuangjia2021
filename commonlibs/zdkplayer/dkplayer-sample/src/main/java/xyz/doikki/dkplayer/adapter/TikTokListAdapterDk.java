package xyz.doikki.dkplayer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.list.tiktok.TikTok2ActivityDk;
import xyz.doikki.dkplayer.activity.list.tiktok.TikTok3ActivityDk;
import xyz.doikki.dkplayer.activity.list.tiktok.TikTokActivityDk;
import xyz.doikki.dkplayer.bean.TiktokBeanDk;

import java.util.List;

public class TikTokListAdapterDk extends RecyclerView.Adapter<TikTokListAdapterDk.TikTokListViewHolder> {

    public List<TiktokBeanDk> data;

    private int mId;

    public TikTokListAdapterDk(List<TiktokBeanDk> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public TikTokListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tiktok_listdk, parent, false);
        return new TikTokListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TikTokListViewHolder holder, int position) {
        TiktokBeanDk item = data.get(position);
        holder.mTitle.setText(item.title);
        Glide.with(holder.mThumb.getContext())
                .load(item.coverImgUrl)
                .into(holder.mThumb);

        holder.mPosition = position;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setImpl(int id) {
        mId = id;
    }

    public class TikTokListViewHolder extends RecyclerView.ViewHolder {

        public ImageView mThumb;
        public TextView mTitle;

        public int mPosition;

        public TikTokListViewHolder(@NonNull View itemView) {
            super(itemView);
            mThumb = itemView.findViewById(R.id.iv_thumb);
            mTitle = itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mId == R.id.impl_recycler_view) { //RecyclerView
                        TikTokActivityDk.start(itemView.getContext(), mPosition);
                    } else if (mId == R.id.impl_vertical_view_pager) { //VerticalViewPager
                        TikTok2ActivityDk.start(itemView.getContext(), mPosition);
                    } else if (mId == R.id.impl_view_pager_2) { //ViewPager2
                        TikTok3ActivityDk.start(itemView.getContext(), mPosition);
                    }
                }
            });
        }
    }
}
