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
import com.example.localfarm.models.command.ProductOrder;
import com.example.localfarm.models.products.Products;

import java.util.List;

public class ProductCommandItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ProductOrder> ProductList;
    private AdapterView.OnItemClickListener clickListener;
    public ProductCommandItemAdapter(Context context, List<ProductOrder> productList){ this(context,productList,null);}
    public ProductCommandItemAdapter(Context context, List<ProductOrder> productList, AdapterView.OnItemClickListener clickListener) {
        System.out.println("in "+this+" constructor: "+productList.size());
        mInflater = LayoutInflater.from(context);
        ProductList = productList;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        System.out.println("in "+this+" getCount: "+ProductList);
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
        ProductOrder command = ProductList.get(position);
        Products product = command.getProduct();


        //(2) : Récupération des TextView de notre layout
        ImageView productPicture        = layoutItem.findViewById(R.id.cardProductImage);
        TextView productTitle           = layoutItem.findViewById(R.id.cardProductTitle);
        TextView productDescription     = layoutItem.findViewById(R.id.CardProductDescriptionText);
        TextView productQuantity        = layoutItem.findViewById(R.id.cardProductQuantityText);
        TextView totalPrice             = layoutItem.findViewById(R.id.cardProductPricePerUnit);

        //(3) : Renseignement des valeurs
        productPicture.setImageResource(product.mainPicture);
        productTitle.setText(product.name);
        productDescription.setText(product.description);
        productQuantity.setText(String.format("%.3f",command.getQuantity()));
        totalPrice.setText(String.format("%.2f", product.priceFor(command.getQuantity()))+"€");

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
