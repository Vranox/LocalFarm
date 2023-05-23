package com.example.localfarm.adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.localfarm.R;
import com.example.localfarm.data.Products;

import java.util.List;

public class ProductItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Products> ProductList;
    private AdapterView.OnItemClickListener clickListener;
    public ProductItemAdapter(Context context, List<Products> productList){ this(context,productList,null);}
    public ProductItemAdapter(Context context, List<Products> productList, AdapterView.OnItemClickListener clickListener) {
        mInflater = LayoutInflater.from(context);
        ProductList = productList;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return ProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //(1) : Réutilisation des layouts
        View layoutItem = convertView == null ? mInflater.inflate(R.layout.product_command_card, parent, false) : convertView;
        System.out.println("in adapter: "+ProductList.size());
        Products product = ProductList.get(position);


        //(2) : Récupération des TextView de notre layout
        ImageView productPicture        = layoutItem.findViewById(R.id.cardProductImage);
        TextView productTitle           = layoutItem.findViewById(R.id.cardProductTitle);
        TextView productDescription     = layoutItem.findViewById(R.id.CardProductDescriptionText);
        TextView productPricePerUnit    = layoutItem.findViewById(R.id.cardProductPricePerUnit);

        //(3) : Renseignement des valeurs
        productPicture.setImageResource(product.mainPicture);
        productTitle.setText(product.name);
        productDescription.setText(product.description);
        productPricePerUnit.setText("DO SMTH");

        if (clickListener != null) convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position);
            }
        });

        return layoutItem;
    }


    public void onItemClick(int position) {
        // Gérer l'événement de clic ici
        // ...
    }
}
