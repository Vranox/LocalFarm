package com.example.localfarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.example.localfarm.R;
import com.example.localfarm.data.Client;
import com.example.localfarm.data.Productor;
import com.example.localfarm.data.Products;

public class ProductPageActivity extends AppCompatActivity {
    public static final String ProductorArg = "ProductorArg";
    ImageButton returnButton;
    ImageView productImage;
    TextView productTitle;
    TextView pricePerUnit;
    TextView productDescription;

    View fragmentProductor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        Intent intent = getIntent();
        Products product = (Products) intent.getParcelableExtra(ProductorArg);
        if(product==null){
            returnButton();
            return;
        }
        // Trouver les vues par ID
        returnButton = findViewById(R.id.ProductPage_ReturnButton);
        productImage = findViewById(R.id.ProductPage_ProductImage);
        productTitle = findViewById(R.id.ProductPage_ProductTitle);
        pricePerUnit = findViewById(R.id.ProductPage_PricePerUnit);
        productDescription = findViewById(R.id.ProductPage_ProductDescription);

        // Définir le texte des vues
        productTitle.setText(product.name);
        pricePerUnit.setText("XX,xx € / unit");
        productDescription.setText(product.description);
        productImage.setImageResource(product.mainPicture);
        returnButton.setOnClickListener(v -> {
            returnButton();
        });

        Client client = new Client("Rallo", "Frederic");
        Productor productor = new Productor(client,"pauvritech");
        ProductorCard fragment = ProductorCard.newInstance(productor);

        // Commencer la transaction de fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Ajouter le fragment à la transaction
        fragmentTransaction.add(R.id.ProductPage_ProductorCardFragment, fragment);

        // Terminer la transaction
        fragmentTransaction.commit();
    }
    public void returnButton(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
