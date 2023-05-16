package com.example.localfarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.example.localfarm.R;
import com.example.localfarm.data.Productor;
import java.util.Calendar;
import java.util.TimeZone;

public class ProductorCard extends Fragment {
    public Productor producteur;
    private static final String ARG_PARAM1 = "productor";


    public static ProductorCard newInstance(Parcelable param1) {
        System.out.println("newInstance");
        ProductorCard fragment = new ProductorCard();
        System.out.println("args1");
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            System.out.println("args2");
            producteur = getArguments().getParcelable(ARG_PARAM1);
            System.out.println("producteur : "+producteur);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onViewCreated(container, savedInstanceState);
        System.out.println("onCreateView");
        View view = inflater.inflate(R.layout.productor_card, container, false);
        if(getArguments() == null) return view;
        producteur = getArguments().getParcelable(ARG_PARAM1);

        ImageView favoriteIcon = view.findViewById(R.id.ProductorCard_FavoriteIcon);
        ImageView profilePicture = view.findViewById(R.id.ProductorCard_ProfilePicture);
        Button button = view.findViewById(R.id.ProductorCard_button);

        profilePicture.setImageResource(producteur.getProfilePicture());
        button.setOnClickListener(v -> {
            /*Intent intent = new Intent(this, MyActivity.class);
            intent.putExtra("producteur",(Parcelable) producteur);
            startActivity(intent);*/

        });

        updateRating(view);


        return view;
    }
    private void updateRating(View view){
        TextView textRate = view.findViewById(R.id.ProductorCard_textRate);
        ImageView star1 = view.findViewById(R.id.ProductorCard_star1);
        ImageView star2 = view.findViewById(R.id.ProductorCard_star2);
        ImageView star3 = view.findViewById(R.id.ProductorCard_star3);
        ImageView star4 = view.findViewById(R.id.ProductorCard_star4);
        ImageView star5 = view.findViewById(R.id.ProductorCard_star5);
        star1.setImageResource(R.drawable.star_empty);
        star2.setImageResource(R.drawable.star_empty);
        star3.setImageResource(R.drawable.star_empty);
        star4.setImageResource(R.drawable.star_empty);
        star5.setImageResource(R.drawable.star_empty);
        int value;
        switch(value = (int)producteur.getRating()){
            default:
                break;
            case 10:
            case 9:
                if(value==9) star5.setImageResource(R.drawable.star_half);
                else star5.setImageResource(R.drawable.star_full);
            case 8:
            case 7:
                if(value==7) star4.setImageResource(R.drawable.star_half);
                else star4.setImageResource(R.drawable.star_full);
            case 6:
            case 5:
                if(value==5) star3.setImageResource(R.drawable.star_half);
                else star3.setImageResource(R.drawable.star_full);
            case 4:
            case 3:
                if(value==3) star2.setImageResource(R.drawable.star_half);
                else star2.setImageResource(R.drawable.star_full);
            case 2:
            case 1:
                if(value==1) star1.setImageResource(R.drawable.star_half);
                else star1.setImageResource(R.drawable.star_full);
            break;
        }
        textRate.setText((float)value/2+"/5");
    }

}
