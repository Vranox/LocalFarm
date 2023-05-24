package com.example.localfarm.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.localfarm.R;
import com.example.localfarm.adapteur.ProductCommandItemAdapter;
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class UserListCommandActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_command_list_activity);

        //ListView
        //--GetListOfProduct
        ProductCommandItemAdapter adapter = new ProductCommandItemAdapter(getApplicationContext(), ProductOrder.staticList());
        ListView productList = findViewById(R.id.ListProductOrders_ProductCommandList);
        productList.setAdapter(adapter);


        //Nav Bar
        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        /*NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);*/
    }
    @Override
    protected void onDestroy() {
        System.out.println("Destroyed");
        super.onDestroy();
    }

}