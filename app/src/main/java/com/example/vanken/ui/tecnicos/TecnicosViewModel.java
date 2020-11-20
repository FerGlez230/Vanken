package com.example.vanken.ui.tecnicos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TecnicosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TecnicosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragment tecnicos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}