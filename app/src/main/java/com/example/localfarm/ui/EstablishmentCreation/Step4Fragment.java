package com.example.localfarm.ui.EstablishmentCreation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.localfarm.R;
import com.example.localfarm.models.Establishment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Step4Fragment extends Fragment {
    Establishment establishment;
    private DatabaseReference establishmentRef;
    private StorageReference storageRef;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private OnDataChangeListener mOnDataChangeListener;
    private static final int MAX_UPLOAD_RETRIES = 5;
    boolean isUploading = false;
    Button nextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step4, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        establishmentRef = database.getReference("establishment");
        storageRef = FirebaseStorage.getInstance().getReference();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = requireActivity().findViewById(R.id.select_image);
        Button selectImageButton = requireActivity().findViewById(R.id.buttonadd);
        establishment = mOnDataChangeListener.getEstablishment();

        if (establishment.imageUri != null) {
            Picasso.get().load(establishment.getImageUri()).into(imageView);
        }

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            imageView.setImageURI(imageUri);
                            StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());
                            uploadFileWithRetries(imageRef, imageUri, MAX_UPLOAD_RETRIES);
                        } else {
                            // The user didn't choose an image, so enable the next button
                            nextButton.setEnabled(true);
                            nextButton.setAlpha(1f);
                            isUploading = false;
                        }
                    } else {
                        // The user didn't choose an image, so enable the next button
                        nextButton.setEnabled(true);
                        nextButton.setAlpha(1f);
                        isUploading = false;
                    }
                }
        );
        nextButton = requireActivity().findViewById(R.id.next_button);
        selectImageButton.setOnClickListener(view1 -> {
            nextButton.setEnabled(false);
            nextButton.setAlpha(0.5f);
            isUploading = true;
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainer);
        Navigation.setViewNavController(nextButton, navController);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = establishmentRef.push().getKey();
                establishmentRef.child(key).setValue(establishment)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("Establishment added successfully "+establishment.getImageUri());
                                } else {
                                    System.out.println("Failed to add establishment: " + task.getException());
                                }
                            }
                        });
                requireActivity().finish();
            }
        });

        Button prevButton = requireActivity().findViewById(R.id.prev_button);
        Navigation.setViewNavController(prevButton, navController);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = Step4FragmentDirections.actionStep4ToStep3();
                Navigation.findNavController(view).navigate(action);
                nextButton.setText("Suivant");
                nextButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                ImageView circle1 = requireActivity().findViewById(R.id.step4Circle);
                circle1.setImageResource(R.drawable.baseline_circle_variant_24);
                ImageView circle2 = requireActivity().findViewById(R.id.step3Circle);
                circle2.setImageResource(R.drawable.baseline_circle_24);
            }
        });
    }
    private void uploadFileWithRetries(StorageReference imageRef, Uri fileUri, int retriesLeft) {
        UploadTask uploadTask = imageRef.putFile(fileUri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                establishment.setImageUri(downloadUrl);
                System.out.println("Upload success: " + downloadUrl);
                mOnDataChangeListener.onDataChanged(establishment);
            });
            isUploading = false;
            nextButton.setEnabled(true);
            nextButton.setAlpha(1f);
        }).addOnFailureListener(e -> {
            System.out.println("Upload failed: " + e.getMessage());
            if (retriesLeft > 0) {
                System.out.println("Retrying upload... Retries left: " + (retriesLeft - 1));
                uploadFileWithRetries(imageRef, fileUri, retriesLeft - 1);
            } else {
                isUploading = false;
                nextButton.setEnabled(true);
                nextButton.setAlpha(1f);
                System.out.println("Max upload attempts reached. Please check your connection and try again.");
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnDataChangeListener = (OnDataChangeListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDataChangeListener");
        }
    }
}

