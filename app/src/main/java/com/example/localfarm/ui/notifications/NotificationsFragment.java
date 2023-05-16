package com.example.localfarm.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.localfarm.R;
import com.example.localfarm.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Récupérer le bouton add_contact
        Button addButton = binding.getRoot().findViewById(R.id.add_contact);

        // Récupérer les EditText name_edittext, email_edittext et phone_edittext
        EditText nameEditText = binding.getRoot().findViewById(R.id.name_edittext);
        EditText emailEditText = binding.getRoot().findViewById(R.id.email_edittext);
        EditText phoneEditText = binding.getRoot().findViewById(R.id.phone_edittext);

        //On setup les données dans la page
        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        String email = sharedPrefs.getString("email", "");
        String password = sharedPrefs.getString("password", "");
        String phone = sharedPrefs.getString("phone", "");
        String name = sharedPrefs.getString("name", "");

        nameEditText.setText(name);
        emailEditText.setText(email);
        phoneEditText.setText(phone);

        // Ajouter un OnClickListener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher une boîte de dialogue d'alerte
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Voulez-vous ajouter un contact ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Récupérer les valeurs entrées dans les EditText
                        String name = nameEditText.getText().toString();
                        String email = emailEditText.getText().toString();
                        String phone = phoneEditText.getText().toString();

                        // Ajouter le contact
                        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);

                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Code à exécuter lorsque l'utilisateur clique sur le bouton "Non"
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}