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
import com.example.localfarm.data.ProductCommand;
import com.example.localfarm.data.Products;

import java.util.List;

public class ProductCommandItemAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<ProductCommand> ProductList;
    private AdapterView.OnItemClickListener clickListener;
    public ProductCommandItemAdapter(Context context, List<ProductCommand> productList){ new ProductCommandItemAdapter(context,productList,null);}
    public ProductCommandItemAdapter(Context context, List<ProductCommand> productList, AdapterView.OnItemClickListener clickListener) {
        mInflater = LayoutInflater.from(context);
        this.ProductList = productList;
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
        View layoutItem = convertView == null ? mInflater.inflate(R.layout.product_card, parent, false) : convertView;
        ProductCommand command = ProductList.get(position);
        Products product = command.getProduct();

        //(2) : Récupération des TextView de notre layout
        ImageView productPicture        = layoutItem.findViewById(R.id.cardProductImage);
        TextView productTitle           = layoutItem.findViewById(R.id.cardProductTitle);
        TextView productDescription     = layoutItem.findViewById(R.id.CardProductDescriptionText);
        TextView productQuantity        = layoutItem.findViewById(R.id.cardProductQuantityText);
        TextView productPricePerUnit    = layoutItem.findViewById(R.id.cardProductPricePerUnit);

        //(3) : Renseignement des valeurs
        productPicture.setImageResource(product.mainPicture);
        productTitle.setText(product.name);
        productDescription.setText(product.description);
        productQuantity.setText(command.getQuantity().toString());
        productPricePerUnit.setText(command.getQuantity().getUnit().toString());

        /*convertView.setOnClickListener(() -> {
            if (clickListener != null) {
                clickListener.onItemClick(position);
            }
        });*/

        return layoutItem;
    }


    public void onItemClick(int position) {
        // Gérer l'événement de clic ici
        // ...
    }
}
