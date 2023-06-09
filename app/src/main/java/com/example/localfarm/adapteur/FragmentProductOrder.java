package com.example.localfarm.adapteur;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.localfarm.R;
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.databinding.ActivityMainBinding;
import com.google.firebase.database.annotations.NotNull;

import androidx.fragment.app.Fragment;

import java.util.List;

public class FragmentProductOrder extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_product_list_order, container, false);
        List<ProductOrder> productList = getArguments().getParcelableArrayList(Args.param1.toString());
        //ListView
        //--GetListOfProduct
        ProductCommandItemAdapter adapter = new ProductCommandItemAdapter(getActivity(),productList);
        ListView List = getView().findViewById(R.id.productOrderList);
        List.setAdapter(adapter);
/*
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();*/

        Bundle bundle = new Bundle();
        bundle.putString("key", "value");


        return rootView;
    }

    public enum Args{
        param1("param1");

        String argValue;
        Args(@NotNull String value){
            argValue = value;
        }

        @Override
        public String toString() {
            return argValue;
        }
    }

}