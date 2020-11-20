package com.example.vanken.ui.tools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ConfiguracionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ConfiguracionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment configuraciones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}