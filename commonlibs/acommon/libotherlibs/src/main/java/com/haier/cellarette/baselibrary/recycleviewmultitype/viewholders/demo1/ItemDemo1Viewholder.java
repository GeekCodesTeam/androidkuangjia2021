package com.haier.cellarette.baselibrary.recycleviewmultitype.viewholders.demo1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.recycleviewmultitype.models.demo1.ItemDemo1;

import me.drakeet.multitype.ItemViewBinder;


/**
 * Created by shining on 2018/3/16.
 */

public class ItemDemo1Viewholder extends ItemViewBinder<ItemDemo1, ItemDemo1Viewholder.ViewHolder> {

    public String aValueFromOutside;

    public ItemDemo1Viewholder() {

    }

    public ItemDemo1Viewholder(String aValueFromOutside) {
        this.aValueFromOutside = aValueFromOutside;
    }

    @Override
    protected ViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.rec_demo1_item_text, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.textView = view.findViewById(R.id.text);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(final ViewHolder holder, final ItemDemo1 item) {
        holder.textView.setText(item.getContent1());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong(item.getContent2() + ", " +
                        "aValueFromOutside=" + aValueFromOutside);
//                Intent intent = new Intent(holder.itemView.getContext(), RecycleViewMainActivity.class);
//                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
