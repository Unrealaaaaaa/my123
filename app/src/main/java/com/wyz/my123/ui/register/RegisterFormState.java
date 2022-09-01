package com.wyz.my123.ui.register;

import androidx.annotation.Nullable;

class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer displayError;
    private boolean isDataValid;

    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer displayError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.displayError = displayError;
        this.isDataValid = false;
    }
    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.displayError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getDisplayError() {
        return displayError;
    }


    boolean isDataValid() {
        return isDataValid;
    }
}