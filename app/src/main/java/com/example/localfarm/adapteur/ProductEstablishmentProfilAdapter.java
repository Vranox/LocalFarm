package com.example.localfarm.adapteur;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.localfarm.R;
import com.example.localfarm.models.products.Cart;
import com.example.localfarm.models.products.Products;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ProductEstablishmentProfilAdapter extends RecyclerView.Adapter<ProductEstablishmentProfilAdapter.ViewHolder> implements Observer{
    private List<Products> productsList;
    private Context context;
    private OnProductClickListener listener;
    private Cart cart;

    public ProductEstablishmentProfilAdapter(Context context, List<Products> productsList, Cart cart) {
        this.productsList = productsList;
        this.context = context;
        this.cart = cart;
        cart.addObserver(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = productsList.get(position);
        cart.addObserver(this);
        Glide.with(context).load(product.getMainPicture()).into(holder.imageView);

        holder.nameTextView.setText(product.getName());
        holder.descriptionTextView.setText(product.getDescription());
        holder.priceTextView.setText(String.format("%s per unit", product.pricePerUnit()));

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    System.out.println("La vue notifie le listener du controller que le bouton a été cliqué");
                    listener.onAddToCart(product);
                }
            }
        });
    }
    public void setOnProductClickListener(OnProductClickListener listener) {
        System.out.println("Le listener du controleur est initialisé dans la vue");
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("La vue est notifiée d'un changement dans le modèle et met à jour l'affichage");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            descriptionTextView = itemView.findViewById(R.id.description);
            priceTextView = itemView.findViewById(R.id.price);
            addToCartButton = itemView.findViewById(R.id.add_to_cart_button);
        }

    }
    public interface OnProductClickListener {
        void onAddToCart(Products product);
    }
}

