package com.example.localfarm.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> mOrderList;

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView mLabelTextView;
        public TextView mTotalPriceTextView;
        public TextView mSourceTextView;
        public TextView mDestinationTextView;
        public TextView mStateTextView;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mLabelTextView = itemView.findViewById(R.id.label_text_view);
            mTotalPriceTextView = itemView.findViewById(R.id.total_price_text_view);
            mSourceTextView = itemView.findViewById(R.id.source_text_view);
            mDestinationTextView = itemView.findViewById(R.id.destination_text_view);
            mStateTextView = itemView.findViewById(R.id.state_text_view);
        }
    }

    public OrderAdapter(ArrayList<Order> orderList) {
        mOrderList = orderList;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_view, parent, false);
        OrderViewHolder viewHolder = new OrderViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        Order currentItem = mOrderList.get(position);

        holder.mLabelTextView.setText(currentItem.getLabel());
        holder.mTotalPriceTextView.setText(String.valueOf(currentItem.getTotalPrice()) + "€");
        holder.mSourceTextView.setText(currentItem.getSource());
        holder.mDestinationTextView.setText(currentItem.getDestination());

        String stateText = "";

        switch(currentItem.getState()) {
            case Delivered:
                stateText = "Livré";
                break;
            case Ongoing:
                stateText = "En cours";
                break;
            case Cancelled:
                stateText = "Annulé";
                break;
            default:
                break;
        }
        holder.mStateTextView.setText(stateText);
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}