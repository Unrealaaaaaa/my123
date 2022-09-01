package com.wyz.my123.ui.login;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.wyz.my123.Injection;
import com.wyz.my123.MainActivity;
import com.wyz.my123.R;
import com.wyz.my123.databinding.ActivityLoginBinding;
import com.wyz.my123.ui.factory.ViewModelFactory;
import com.wyz.my123.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    // 显示声明
    public static LoginActivity loginActivity;

    // 创建登录绑定
    private ActivityLoginBinding binding;

    private ViewModelFactory viewModelFactory;

    private LoginViewModel userViewModel;

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView registerTv;
    SharedPreferences sp;
    CheckBox rememberPwdCB;
    CheckBox autoLoginCB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = this;
        setContentView(R.layout.activity_login);

        // 绑定本activity
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        initView();
    }


    /**
     * 这里要说明为啥重写dispatchTouchEvent，不是重写onTouchEvent
     * dispatchTouchEvent方法用于事件的分发，Android中所有的事件都必须经过这个方法的分发
     * 然后决定是自身消费当前事件还是继续往下分发给子控件处理。返回true表示不继续分发，事件没有被消费。
     * 返回false则继续往下分发，如果是ViewGroup则分发给onInterceptTouchEvent进行判断是否拦截该事件。
     * onTouchEvent方法用于事件的处理，返回true表示消费处理当前事件，返回false则不处理，交给子控件进行继续分发。
     */
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

    private void initView() {

        viewModelFactory = Injection.provideViewModelFactory(this);
        // loginViewModel 绑定 LoginActivity
        userViewModel = new ViewModelProvider(this, viewModelFactory)
                .get(LoginViewModel.class);
        // 获取组件
        usernameEditText = binding.username;
        passwordEditText = binding.password;

        loginButton = binding.login;
        registerTv = binding.toRegister;
        rememberPwdCB = binding.rememberPwd;
        autoLoginCB = binding.autoLogin;
        // 数据回显
        infoCallBack();
        // 检查用户名和密码是否有效
        userViewModel.getLoginFormState().observe(this, (loginFormState) -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
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
                userViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        // 给编辑框绑定文本改变监听器
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        // 当在键盘上点击完成按钮之后
        passwordEditText.setOnEditorActionListener((v, actionId, event) ->
        {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                userViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                loginCheck();
            }
            return false;
        });

        // 给登录按钮绑定点击事件
        loginButton.setOnClickListener((v) -> {
            userViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
            loginCheck();
        });
        // 给注册按钮绑定点击事件
        registerTv.setOnClickListener((view) -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void infoCallBack() {
        if (sp.getBoolean("rememberPwd", false)) {
            usernameEditText.setText(sp.getString("username", ""));
            passwordEditText.setText(sp.getString("password", ""));
            rememberPwdCB.setChecked(true);
            loginButton.setEnabled(true);
        }
        if (sp.getBoolean("autoLogin", false)) {
            autoLoginCB.setChecked(true);
            // 自动登录
            userViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
            loginCheck();
        }
    }

    // 点击登录按钮,查询数据库进行检查
    void loginCheck() {
        // 选择记住密码选项
        if (rememberPwdCB.isChecked()) {
            sp.edit().putString("username", usernameEditText.getText().toString())
                    .putString("password", passwordEditText.getText().toString())
                    .putBoolean("rememberPwd", true)
                    .apply();
        } else {
            sp.edit().putBoolean("rememberPwd", false).apply();
        }
        if (autoLoginCB.isChecked()) {
            sp.edit().putBoolean("autoLogin", true)
                    .apply();
        } else {
            sp.edit().putBoolean("autoLogin", false).apply();
        }
        startActivity(new Intent(this, MainActivity.class)
                .putExtra("displayname", "aa"));
    }

    // 判断当前点击屏幕的地方是否是软键盘
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