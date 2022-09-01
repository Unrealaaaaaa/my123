package com.wyz.my123.ui.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.wyz.my123.ui.login.LoginViewModel;
import com.wyz.my123.ui.register.RegisterViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    /*private final UserDataSource mDataSource;

    public ViewModelFactory(UserDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }*/

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel();
        } else if(modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}