package com.example.pacemaker.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Integer> data;

    public LiveData<Integer> getMutableData() {
        if (data == null)
            data = new MutableLiveData(0);

        return data;

    }

    public void add() {
        data.setValue(data.getValue()+1);
        Log.i("MainViewMoodel", data.getValue().toString());
    }
}
