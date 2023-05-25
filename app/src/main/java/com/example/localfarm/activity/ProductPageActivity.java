package com.example.localfarm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.example.localfarm.R;
import com.example.localfarm.models.actor.Account;
import com.example.localfarm.ui.profile.FragmentAccountCard;
import com.example.localfarm.models.products.Products;

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

        Account account = new Account("email@laposte.fr","secretthings","0123456789","Rallo","Frederic","UltimateDarkFarmer");
        FragmentAccountCard fragment = FragmentAccountCard.newInstance((Parcelable)account);

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
