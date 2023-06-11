package com.example.localfarm.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.activity.CommandDetailActivity;
import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.command.Command;
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.models.common.Date;
import com.example.localfarm.models.common.Time;
import com.example.localfarm.models.products.Products;
import com.example.localfarm.models.products.QuantityUnits;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Command> mOrderList;

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

    public OrderAdapter(List<Command> orderList) {
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
        Command currentItem = mOrderList.get(position);

        holder.mLabelTextView.setText(currentItem.getDate().toString());
        holder.mTotalPriceTextView.setText(String.valueOf(String.format("%.2f",currentItem.getTotalPrice())) + "€");
        holder.mSourceTextView.setText(currentItem.getBuyer().toString());
        holder.mDestinationTextView.setText(currentItem.getSeller().toString());

        String stateText = "";

        switch(currentItem.getStatus()) {
            case Completed:
                stateText = "Livré";
                break;
            case Accepted:
                stateText = "En cours";
                break;
            case Canceled:
                stateText = "Annulé";
                break;
            case Waiting:
                stateText = "en attente";
            default:
                break;
        }
        holder.mStateTextView.setText(stateText);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action à effectuer lors du clic sur l'élément
                // Par exemple, afficher une boîte de dialogue, démarrer une autre activité, etc.
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, CommandDetailActivity.class);
                //command.addProduct(ProductOrder.staticList());
                intent.putExtra(CommandDetailActivity.commandParam,currentItem);
                // Démarrer la nouvelle activité
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}