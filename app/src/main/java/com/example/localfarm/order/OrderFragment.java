package com.example.localfarm.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;

import java.util.ArrayList;

public class OrderFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private OrderAdapter mAdapter;
    private ArrayList<Order> mOrderList;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Order o1 = new Order("Mentuelle", "Rue Robert 24", "Rue Riquier 23", 100);
        o1.setState(OrderState.Delivered);

        Order o2 = new Order("Livraison frais", "Route Dolines 400", "Rue Riquier 23", 50);
        o2.setState(OrderState.Cancelled);

        mOrderList = new ArrayList<>();
        mOrderList.add(o1);
        mOrderList.add(o2);
        mOrderList.add(new Order("Fruits & légumes", "20 Gare St-Pierre", "Rue Riquier 23", 40));

        mAdapter = new OrderAdapter(mOrderList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public OrderAdapter getmAdapter() {
        return mAdapter;
    }
}
