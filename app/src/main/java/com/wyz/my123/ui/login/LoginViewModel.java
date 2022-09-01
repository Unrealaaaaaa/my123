package com.wyz.my123.ui.login;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyz.my123.R;
import com.wyz.my123.client.NetClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginViewModel extends ViewModel {

    //private final UserDataSource mDataSource;
    // 生命周期监视组件
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    // 登录窗体的数据验证状态
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    /*public LoginViewModel(UserDataSource dataSource) {
        mDataSource = dataSource;
    }*/

    public void login(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("userName", username);
            json.put("userPwd", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),String.valueOf(json));
        NetClient.getNetClient().postRequest("http://100.87.12.25:8080/app/user/login", requestBody, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                LoginActivity.loginActivity.runOnUiThread(() -> {
                    Toast.makeText(LoginActivity.loginActivity, "请求失败", Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String res = response.body().string();
                LoginActivity.loginActivity.runOnUiThread(() ->{
                    try{
                        JSONObject json = new JSONObject(res);
                        String displayName = json.getString("displayName");
                        Toast.makeText(LoginActivity.loginActivity, "欢迎！" + displayName, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    // 检查登录数据是否有效
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // 检查用户名是否有效
    private boolean isUserNameValid(String username) {
        return username != null && username.trim().length() > 5 && username.trim().length() < 18;
    }

    // 检查用户名是否有效
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5 && password.trim().length() < 18;
    }
}