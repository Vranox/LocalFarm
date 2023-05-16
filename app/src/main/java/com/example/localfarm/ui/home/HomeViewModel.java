package com.example.localfarm.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenue sur LocalFarm ! Veuillez vous connecter avec un compte GMAIL !");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
