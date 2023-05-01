package com.example.myapplication.ui.order;

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

        mOrderList = new ArrayList<>();
        mOrderList.add(new Order("Order 1", "Source 1", "Destination 1", 100));
        mOrderList.add(new Order("Order 2", "Source 2", "Destination 2", 50));
        mOrderList.add(new Order("Order 3", "Source 3", "Destination 3", 40));

        mAdapter = new OrderAdapter(mOrderList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public OrderAdapter getmAdapter() {
        return mAdapter;
    }
}
