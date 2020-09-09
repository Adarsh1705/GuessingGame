package com.example.android.guessinggame;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class Values extends ViewModel {
    public int Low = 1, High = 101;
    Random random = new Random();
    MutableLiveData<Integer> Number;

    public LiveData<Integer> getValues() {
        if (Number == null) {
            Number = new MutableLiveData<>();
        }
        Number.setValue(random.nextInt(High - Low) + Low);
        return Number;
    }

    public void go() {
        Number.setValue(random.nextInt(High - Low) + Low);
    }
}
