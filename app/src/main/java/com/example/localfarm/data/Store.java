package com.example.localfarm.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.localfarm.R;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private String adress;
    private ArrayList<Products> productsAvailable = new ArrayList<>();
    private ArrayList<Command> commandsAvailable = new ArrayList<>();

    public Store(String adress){
        this.adress =adress;
    }

}
/*
public class ProductItemAdapter extends BaseAdapter {

    private List<Products> productList = new ArrayList<>();

    public ProductItemAdapter(List<Products> productList) {
        this.productList = productList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layoutItem = convertView == null ? mInflater.inflate(R.layout., parent, false) : convertView;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_list_item, parent, false);
        }
        //Get Data
        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewSubtitle = convertView.findViewById(R.id.textViewSubtitle);

        //Get Fields


        //Set Data Into Fields
        String item = getItem(position);

        textViewTitle.setText(item);
        textViewSubtitle.setText("Subtitle " + position);

        return convertView;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    }
*/