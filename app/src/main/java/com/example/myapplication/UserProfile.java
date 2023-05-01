package com.example.myapplication;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.localfarm.R;

public class UserProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile); // charge la page XML

        // récupère le bouton par son ID
        Button addContactButton = (Button) findViewById(R.id.add_contact);

        // configure l'action onClick pour le bouton
        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = ((EditText)findViewById(R.id.name_edittext)).getText().toString();
                String mail = ((EditText)findViewById(R.id.email_edittext)).getText().toString();
                String phone_number = ((EditText)findViewById(R.id.phone_edittext)).getText().toString();

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, nom);
                startActivity(intent);

            }
        });
    }
}
