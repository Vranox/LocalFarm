package com.example.localfarm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.localfarm.R;
import com.example.localfarm.models.Account;
import com.example.localfarm.models.Establishment;
import com.example.localfarm.models.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendCommentActivity extends Activity {

    private Account user;
    private Establishment establishment;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Configuration de la référence à la base de données Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_review);

        EditText reviewText = findViewById(R.id.phone_edit_text);
        RatingBar stars = findViewById(R.id.rating_bar);
        Button sendComment = findViewById(R.id.send_comment_button);

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = reviewText.getText().toString();
                int numberOfStars = stars.getNumStars();
                Review review = new Review(user, establishment, comment, numberOfStars);
                mDatabase.child("comment").push().setValue(review);
            }
        });
    }
}
