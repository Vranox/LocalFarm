package com.example.localfarm.order;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderViewModel extends AppCompatActivity {
    private List<Order> orderList = new ArrayList<>();

    private RecyclerView orderListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
