package com.wyz.my123;

import android.content.Context;

import com.wyz.my123.ui.factory.ViewModelFactory;

public class Injection {

    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory();
    }
}
