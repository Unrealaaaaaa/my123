package com.wyz.my123.ui.register;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.wyz.my123.Injection;
import com.wyz.my123.R;
import com.wyz.my123.databinding.ActivityRegisterBinding;
import com.wyz.my123.ui.factory.ViewModelFactory;
import com.wyz.my123.ui.login.LoginActivity;


public class RegisterActivity extends AppCompatActivity {

    public static RegisterActivity registerActivity;

    // 创建登录绑定
    private ActivityRegisterBinding binding;

    private ViewModelFactory viewModelFactory;

    private RegisterViewModel registerViewModel;

    EditText usernameEditText;
    EditText passwordEditText;
    EditText displayNameEditText;
    Button registerButton;
    ProgressBar loadingProgressBar;
    Intent intent;

    //private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerActivity = this;

        // 绑定本activity
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModelFactory = Injection.provideViewModelFactory(this);
        // loginViewModel 绑定 LoginActivity
        registerViewModel = new ViewModelProvider(this, viewModelFactory)
                .get(RegisterViewModel.class);
        // 获取组件
        usernameEditText = binding.username;
        passwordEditText = binding.password;
        displayNameEditText = binding.displayname;
        registerButton = binding.register;
        loadingProgressBar = binding.loading;
        // 检查用户名和密码是否有效
        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if(registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if(registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if(registerFormState.getDisplayError() != null) {
                    displayNameEditText.setError(getString(registerFormState.getDisplayError()));
                }
                if (registerButton.isEnabled()) {
                    /*registerUser.setUserName(usernameEditText.getText().toString());
                    registerUser.setUserPwd(passwordEditText.getText().toString());
                    registerUser.setDisplayName(displayNameEditText.getText().toString());*/
                }
            }
        });
        // 监听编辑框文本
        TextWatcher afterTextChangedListener = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        displayNameEditText.getText().toString());
            }
        };
        // 给编辑框绑定文本改变监听器
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        displayNameEditText.addTextChangedListener(afterTextChangedListener);
        // 当在键盘上点击完成按钮之后
        displayNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 匹配enterId
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    registerCheck(registerViewModel.register(
                            usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(),
                            displayNameEditText.getText().toString()));
                }
                return false;
            }
        });

        // 给注册按钮绑定点击事件
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCheck(registerViewModel.register(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        displayNameEditText.getText().toString()));
            }
        });
    }

    private void registerCheck(Integer rowId) {
        if (rowId == -1) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.register_error)
                    .setMessage(R.string.register_failed)
                    .setPositiveButton("确定", (dialogInterface,i) -> {
                    }).create().show();
        } else {
            Toast.makeText(registerActivity, "注册成功！", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //获取当前获得当前焦点所在View
            View view = getCurrentFocus();
            if (isShouldHideInput(view, event)) {
                //如果不是edittext，则隐藏键盘
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    //隐藏键盘
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(event);
        }
        /**
         * 看源码可知superDispatchTouchEvent  是个抽象方法，用于自定义的Window
         * 此处目的是为了继续将事件由dispatchTouchEvent(MotionEvent event)传递到onTouchEvent(MotionEvent event)
         * 必不可少，否则所有组件都不能触发 onTouchEvent(MotionEvent event)
         */
        if (getWindow().superDispatchTouchEvent(event)) {
            return true;
        }
        return onTouchEvent(event);
    }

    //判断当前点击屏幕的地方是否是软键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}