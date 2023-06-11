package com.example.localfarm.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.localfarm.R;
import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.command.Command;
import com.example.localfarm.models.command.CommandStatus;
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.models.common.Date;
import com.example.localfarm.models.common.Time;
import com.example.localfarm.models.products.Products;
import com.example.localfarm.models.products.QuantityUnits;

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

        //Mocks
        Command command1 = new Command(
            new Account(
                "email",
                "passwd",
                "0000000000",
                "Blanc",
                "Francois",
                "123456"
            ),new Account(
                "email",
                "passwd",
                "0000000000",
                "Blanc",
                "Francois",
                "123456"
            ),
                new Date(2023,6,9),
                new Time()
        );
        Command command2 = new Command(
                new Account(
                        "email",
                        "passwd",
                        "0000000000",
                        "Blanc",
                        "Francois",
                        "123456"
                ),new Account(
                "email",
                "passwd",
                "0000000000",
                "Blanc",
                "Francois",
                "123456"
        ),
                new Date(2023,6,9),
                new Time()
        );
        Command command3 = new Command(
                new Account(
                        "email",
                        "passwd",
                        "0000000000",
                        "Blanc",
                        "Francois",
                        "123456"
                ),new Account(
                "email",
                "passwd",
                "0000000000",
                "Blanc",
                "Francois",
                "123456"
        ),
                new Date(2023,6,9),
                new Time()
        );
        command1.setStatus(CommandStatus.Waiting);
        command2.setStatus(CommandStatus.Canceled);
        command3.setStatus(CommandStatus.Completed);
        command1.addProduct(new ProductOrder(new Products("Tomates Bio",R.drawable.tomate,"tomates de saison",250,1.33f ,QuantityUnits.g),600));
        command1.addProduct(new ProductOrder(new Products("Lait Bio",R.drawable.lait,"Favorise les entreprises locales",4,3.20f ,QuantityUnits.L),6));
        command1.addProduct(new ProductOrder(new Products("Tomates mures",R.drawable.tomate,"tomates de saison",2,1.33f ,QuantityUnits.kg),5));
        command2.addProduct(new ProductOrder(new Products("Tomates \"moches\"",R.drawable.tomate,"cagot de tomates",3,3 ,QuantityUnits.unit),10));
        command2.addProduct(new ProductOrder(new Products("Sac de tomates",R.drawable.tomate,"tomates BIO",1,3.30f ,QuantityUnits.kg),4));
        command3.addProduct(new ProductOrder(new Products("panier fruit de saison",R.drawable.tomate,"panier de tomates",1,8.50f ,QuantityUnits.unit),4));
        command3.addProduct(new ProductOrder(new Products("Lait frais",R.drawable.lait,"lait Ecreme",6,4.25f ,QuantityUnits.L),8));


        Order o1 = new Order("Mentuelle", "Rue Robert 24", "Rue Riquier 23", 100);
        o1.setState(OrderState.Delivered);

        Order o2 = new Order("Livraison frais", "Route Dolines 400", "Rue Riquier 23", 50);
        o2.setState(OrderState.Cancelled);

        ArrayList<Command> commandList = new ArrayList<>();
        commandList.add(command1);
        commandList.add(command2);
        commandList.add(command3);

        mAdapter = new OrderAdapter(commandList);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public OrderAdapter getmAdapter() {
        return mAdapter;
    }
}
