package com.example.localfarm.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.localfarm.R;
import com.example.localfarm.models.actor.Account;
import com.example.localfarm.models.command.Command;
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.models.command.CommandStatus;
import com.example.localfarm.databinding.ActivityMainBinding;
import com.example.localfarm.models.common.Date;
import com.example.localfarm.models.common.Time;
import com.example.localfarm.models.products.Products;
import com.example.localfarm.models.products.QuantityUnits;
import com.example.localfarm.order.OrderFragment;
import com.example.localfarm.ui.lists.FragmentProductCommandList;
import com.example.localfarm.utils.NotifHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandDetailActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static String commandParam = "command";
    public static String productorParam = "isProductor";
    Command command;
    boolean isProductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_detail_activity);
        Intent intent = getIntent();
        command = (Command) intent.getParcelableExtra(commandParam);
        isProductor = intent.getBooleanExtra(productorParam,false);
        //ListOfProductOrder
        FragmentProductCommandList fragment = FragmentProductCommandList.newInstance(command.getProductList());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.CommandDetailActivity_ProductCommandList, fragment)
                .commit();

        ((TextView) findViewById(R.id.CommandDetailActivity_Account)).setText(isProductor?"Producteur: "+command.getSeller().getName():"Client: "+command.getBuyer().getName());
        ((TextView) findViewById(R.id.CommandDetailActivity_DeliveryDate)).setText("Date: " + command.getDate().toString());
        ((TextView) findViewById(R.id.CommandDetailActivity_DeliveryLocation)).setText("Adresse: "+command.getSeller().getEmail());

        ((Button) findViewById(R.id.CommandDetailActivity_CalendarButton)).setOnClickListener((event)->{
            startActivity(command.createMeeting());
        });

        ((ImageButton) findViewById(R.id.returnButton)).setOnClickListener((event)->{
            Intent intentTo = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentTo);
        });

        {
            ((TextView) findViewById(R.id.CommandDetailActivity_TotalPrice)).setText("Prix: "+String.format("%.2f", command.getTotalPrice())+"€");
        }
        {
            Spinner spinner = findViewById(R.id.CommandDetailActivity_CommandStateSpinner);
            updateSpinnerValue(spinner);

            {
                Context context = this.getApplicationContext();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CommandStatus selectedStatus = (CommandStatus) parent.getItemAtPosition(position);
                        if(selectedStatus==command.getStatus())return;
                        // Faites quelque chose avec la valeur sélectionnée
                        command.setStatus(selectedStatus);
                        updateSpinnerValue(spinner);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            NotifHandler.createNotification(context,command);
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        spinner.setSelection(0);
                    }
                });
            }

        }
    }
    private void updateSpinnerValue(Spinner spinner) {
        switch (command.getStatus()) {
            case Canceled:
            case Completed:
                spinner.setEnabled(false);
                break;
            default:
                ArrayList<CommandStatus> statusAvailable = new ArrayList<>();
                statusAvailable.add(command.getStatus());
                if (isProductor && command.getStatus() == CommandStatus.Waiting)statusAvailable.add(CommandStatus.Accepted);
                else if (isProductor && command.getStatus() == CommandStatus.Accepted)statusAvailable.add(CommandStatus.Completed);
                statusAvailable.add(CommandStatus.Canceled);

                ArrayAdapter<CommandStatus> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusAvailable);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                break;
        }
    }

}