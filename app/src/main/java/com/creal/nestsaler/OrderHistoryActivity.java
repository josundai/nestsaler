package com.creal.nestsaler;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.creal.nestsaler.actions.CommonPaginationAction;
import com.creal.nestsaler.actions.PaginationAction;
import com.creal.nestsaler.model.OrderRecord;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;
import com.creal.nestsaler.views.ptr.PTRListFragment;

import java.util.HashMap;
import java.util.Map;

public class OrderHistoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, new OrderListFragment()).commit();
        }
    }


    public static class OrderListFragment extends PTRListFragment<OrderRecord> {
        @Override
        public PaginationAction<OrderRecord> getPaginationAction() {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("app_number", PreferenceUtil.getString(getActivity(), Constants.APP_USER_APP_NUM, null));
            return new CommonPaginationAction(getActivity(), 1, Constants.PAGE_SIZE, Constants.URL_GET_ORDER_RECORD, parameters, OrderRecord.class);
        }

        public View getListItemView(OrderRecord item, View convertView, ViewGroup parent, LayoutInflater inflater) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.view_list_item_order_history, parent, false);
                holder = new ViewHolder();
                holder.card = (TextView) convertView.findViewById(R.id.id_txt_shopping_card);
                holder.status = (TextView) convertView.findViewById(R.id.id_txt_shopping_status);
                holder.time = (TextView) convertView.findViewById(R.id.id_txt_shopping_time);
                holder.cost = (TextView) convertView.findViewById(R.id.id_txt_shopping_cost);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.card.setText(item.getSellerName());
            holder.status.setText(item.getState().toString());
            holder.time.setText(String.format(getString(R.string.shop_time), item.getOrderTime()));
            holder.cost.setText(Utils.formatMoney(item.getAmount()) + "元");
            return convertView;
        }

        @Override
        public int getTitleResId() {
            return R.string.order_history;
        }

        class ViewHolder {
            TextView card;
            TextView status;
            TextView time;
            TextView cost;
        }
    }
}
