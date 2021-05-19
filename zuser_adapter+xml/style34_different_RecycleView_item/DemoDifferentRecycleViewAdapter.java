package com.haiersmart.sfnation.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haiersmart.commonbizlib.glide.GlideOptionsFactory;
import com.haiersmart.commonbizlib.glide.GlideUtil;
import com.haiersmart.commonbizlib.net.Net;
import com.haiersmart.sfnation.R;
import com.haiersmart.sfnation.api.DeliveryApi;
import com.haiersmart.sfnation.bizutils.DataProvider;
import com.haiersmart.sfnation.bizutils.ParamsUtils;
import com.haiersmart.sfnation.bizutils.ShowLoadingUtil;
import com.haiersmart.sfnation.bizutils.ToastUtil;
import com.haiersmart.sfnation.domain.DeliveryDetail;
import com.haiersmart.sfnation.domain.HomedDeliveryMeal;
import com.haiersmart.sfnation.params.DeliveryDetailParams;
import com.haiersmart.sfnation.popwindows.MyDeliveryPopupWindow;
import com.haiersmart.sfnation.ui.delivery.DeliveryMakeSureOrderActivity;
import com.haiersmart.sfnation.ui.delivery.MyDeliveryActivity;
import com.haiersmart.sfnation.ui.mine.order.MyOrderActivity;
import com.haiersmart.sfnation.ui.onekeybuy.HouseDetail;

import org.loader.glin.Callback;
import org.loader.glin.Result;

import java.util.ArrayList;
import java.util.List;

import static com.haiersmart.sfnation.constant.ConstantUtil.INTENT_FROM;
import static com.haiersmart.sfnation.constant.ConstantUtil.INTENT_INFO2;
import static com.haiersmart.utilslib.data.ToStringUtil.getString;

public class DemoDifferentRecycleViewAdapter extends RecyclerView.Adapter<DemoDifferentRecycleViewAdapter.CommHolder> {

    public static final int TYPE_OPEN_DOWN = 0;//已送&方向下
    public static final int TYPE_OPEN_UP = 1;//已送&方向上
    public static final int TYPE_CLOSE_DOWN = 3;//未送&方向下
    public static final int TYPE_CLOSE_UP = 4;//未送&方向上

    private LayoutInflater inflater;
    private Context context;
    private List<HomedDeliveryMeal> mratings;

    public DemoDifferentRecycleViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mratings = new ArrayList<HomedDeliveryMeal>();
    }

    public void setContacts(List<HomedDeliveryMeal> ratings) {
        this.mratings = ratings;
    }

    public void addConstacts(List<HomedDeliveryMeal> ratings) {
        this.mratings.addAll(ratings);
    }

    public List<HomedDeliveryMeal> getMratings() {
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
    public int getItemViewType(int position) {
        return getItemType(position, mratings.get(position));
    }

    @Override
    public CommHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CommHolder.getHolder(parent, getLayoutId(viewType));
    }

    @Override
    public void onBindViewHolder(final CommHolder viewHolder, final int position) {
        final HomedDeliveryMeal item = mratings.get(position);
        int viewType = getItemType(position, item);
        onBind(viewHolder, position, viewType, item);
    }

    private void onBind(CommHolder viewHolder, int position, int viewType, HomedDeliveryMeal item) {
        set_itemview_findviewbyid(viewHolder, viewType, item);
    }

    private void set_itemview_findviewbyid(CommHolder viewHolder, int viewType, HomedDeliveryMeal item) {
        switch (viewType) {
            case TYPE_OPEN_DOWN:
                //已送&方向下
                ImageView image_open_down = viewHolder.getView(R.id.image_open_down);
                TextView date_open_down = viewHolder.getView(R.id.date_open_down);
                TextView btn_open_down = viewHolder.getView(R.id.btn_open_down);
                ImageView gift_open_down = viewHolder.getView(R.id.gift_open_down);
                ImageView lock_open_down = viewHolder.getView(R.id.lock_open_down);
                set_different_data(item, image_open_down, date_open_down, btn_open_down, gift_open_down, lock_open_down);
                break;
            case TYPE_OPEN_UP:
                //已送&方向上
                ImageView image_open_up = viewHolder.getView(R.id.image_open_up);
                TextView date_open_up = viewHolder.getView(R.id.date_open_up);
                TextView btn_open_up = viewHolder.getView(R.id.btn_open_up);
                ImageView gift_open_up = viewHolder.getView(R.id.gift_open_up);
                ImageView lock_open_up = viewHolder.getView(R.id.lock_open_up);
                set_different_data(item, image_open_up, date_open_up, btn_open_up, gift_open_up, lock_open_up);
                break;
            case TYPE_CLOSE_DOWN:
                //未送&方向下
                ImageView image_close_down = viewHolder.getView(R.id.image_close_down);
                TextView date_close_down = viewHolder.getView(R.id.date_close_down);
                TextView btn_close_down = viewHolder.getView(R.id.btn_close_down);
                ImageView gift_close_down = viewHolder.getView(R.id.gift_close_down);
                ImageView lock_close_down = viewHolder.getView(R.id.lock_close_down);
                set_different_data(item, image_close_down, date_close_down, btn_close_down, gift_close_down, lock_close_down);
                break;
            case TYPE_CLOSE_UP:
                //未送&方向上
                ImageView image_close_up = viewHolder.getView(R.id.image_close_up);
                TextView date_close_up = viewHolder.getView(R.id.date_close_up);
                TextView btn_close_up = viewHolder.getView(R.id.btn_close_up);
                ImageView gift_close_up = viewHolder.getView(R.id.gift_close_up);
                ImageView lock_close_up = viewHolder.getView(R.id.lock_close_up);
                set_different_data(item, image_close_up, date_close_up, btn_close_up, gift_close_up, lock_close_up);
                break;
            default:
                break;
        }
    }

    /**
     * 这里大家注意如果你的view是不同的那么就要这样写 如果view是相同的但是位置不同 那么就可以抽出来写成一个方法
     *
     * @param item
     * @param image
     * @param date
     * @param btn
     * @param gift
     * @param lock
     */
    private void set_different_data(final HomedDeliveryMeal item, ImageView image, TextView date, TextView btn, ImageView gift, ImageView lock) {
        date.setText(item.getHome_delivery_time());
        btn.setOnClickListener(null);
        if (HomedDeliveryMeal.TYPE_LOCKED.equals(item.getFlag())) { // 未解锁
            lock.setVisibility(View.VISIBLE);
            gift.setVisibility(View.GONE);
            btn.setVisibility(View.VISIBLE);
            btn.setTextColor(image.getContext().getResources().getColor(R.color.font_999));
            btn.setText(R.string.unlock);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToastShort(getString(R.string.sku_is_unlock));
                }
            });
        } else {
            lock.setVisibility(View.GONE);
            if (HomedDeliveryMeal.TYPE_FREE.equals(item.getFlag())) { // 免费送的
                gift.setVisibility(View.VISIBLE);
            } else {
                gift.setVisibility(View.GONE);
            }
            GlideUtil.display(context, image, item.getMeal_image(), GlideOptionsFactory.get(GlideOptionsFactory.Type.DEFAULT));
            if (HomedDeliveryMeal.TYPE_MEAL.equals(item.getFlag())) { // 未购买
                btn.setVisibility(View.INVISIBLE);
            } else {
                btn.setVisibility(View.VISIBLE);
                btn.setTextColor(image.getContext().getResources().getColor(R.color.orange_red));
                btn.setText(R.string.see_order);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MyOrderActivity.class);
                        intent.putExtra(INTENT_FROM, MyDeliveryActivity.class.getName());
                        intent.putExtra(INTENT_INFO2, item.getOrder_id());
                        context.startActivity(intent);
                    }
                });
            }
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemImageClick(item);
                }
            });
        }
    }

    public int getItemType(int position, HomedDeliveryMeal item) {
        // 最后一个
        if (position == getItemCount() - 1) {
            if (position % 2 == 0) {
                return TYPE_CLOSE_DOWN;
            }
            return TYPE_CLOSE_UP;
        }
        String flag = item.getFlag();
        //0 2 4 6 8 10 向下
        if (position % 2 == 0) {
            if (HomedDeliveryMeal.TYPE_BUY.equalsIgnoreCase(flag)
                    || HomedDeliveryMeal.TYPE_FREE.equalsIgnoreCase(flag)
                    || HomedDeliveryMeal.TYPE_MEAL.equalsIgnoreCase(flag)) {
                return TYPE_OPEN_DOWN;
            }
            return TYPE_CLOSE_DOWN;
        }
        //1 3 5 7 9 向上
        if (HomedDeliveryMeal.TYPE_BUY.equalsIgnoreCase(flag)
                || HomedDeliveryMeal.TYPE_FREE.equalsIgnoreCase(flag)
                || HomedDeliveryMeal.TYPE_MEAL.equalsIgnoreCase(flag)) {
            return TYPE_OPEN_UP;
        }
        return TYPE_CLOSE_UP;
    }

    public int getLayoutId(int itemType) {
        switch (itemType) {
            case TYPE_OPEN_DOWN:
                return R.layout.demo_item_type_open_down;//已送&方向下
            case TYPE_OPEN_UP:
                return R.layout.demo_item_type_open_up;//已送&方向上
            case TYPE_CLOSE_DOWN:
                return R.layout.demo_item_type_close_down;//未送&方向下
            case TYPE_CLOSE_UP:
            default:
                return R.layout.demo_item_type_close_up;//未送&方向下
        }
    }

    public static class CommHolder extends RecyclerView.ViewHolder {
        private View mItemView;
        private SparseArray<View> mViews;

        private CommHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mViews = new SparseArray<>();
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = mItemView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public static CommHolder getHolder(ViewGroup parent, int layoutId) {
            View layout = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new CommHolder(layout);
        }
    }

    /**
     * 弹出套餐详情
     *
     * @param data
     */
    private void onItemImageClick(final HomedDeliveryMeal data) {
        ShowLoadingUtil.showProgressDialog2(context, " ");
        DeliveryDetailParams dd = new DeliveryDetailParams(DataProvider.getUser_id(), data.getMeal_id());
        Net.build(DeliveryApi.class, getClass().getName()).getDeliveryDetail(ParamsUtils.just(dd)).enqueue(new Callback<DeliveryDetail>() {
            @Override
            public void onResponse(Result<DeliveryDetail> result) {
                ShowLoadingUtil.dismissProgressDialog2();
                if (!result.isOK()) {
                    ToastUtil.showToastShort(result.getMessage());
                    return;
                }

                DeliveryDetail res = result.getResult();
                if (res == null) {
                    return;
                }
                MyDeliveryPopupWindow.New().show((AppCompatActivity) context, res, new MyDeliveryPopupWindow.ClickListener() {
                    @Override
                    public void ButtonClick(DeliveryDetail data) {
                        targetOrderConfirmActivity(data);
                    }

                    @Override
                    public void imageClick(DeliveryDetail data) {
                        targetProductDetailActivity(data);
                    }

                    @Override
                    public void titleClick(DeliveryDetail data) {
                        targetProductDetailActivity(data);
                    }
                });
            }
        });
    }

    /**
     * 跳转宅配送订单确认页
     *
     * @param data
     */
    private void targetOrderConfirmActivity(DeliveryDetail data) {
        Intent intent = new Intent(context, DeliveryMakeSureOrderActivity.class);
        intent.putExtra("id", data.getId());
        intent.putExtra("shop_id", data.getShop_id());
        context.startActivity(intent);
    }

    /**
     * 跳转宅配送商品详情页
     *
     * @param data
     */
    private void targetProductDetailActivity(DeliveryDetail data) {
        Intent intent = new Intent(context, HouseDetail.class);
        intent.putExtra("kid", data.getId());
        context.startActivity(intent);
    }

    /**
     * ItemClick的回调接口
     *
     * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}