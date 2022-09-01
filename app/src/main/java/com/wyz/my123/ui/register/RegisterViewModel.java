package com.wyz.my123.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyz.my123.R;
import com.wyz.my123.client.NetClient;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterViewModel extends ViewModel {

    //private final UserDataSource mDataSource;
    // 生命周期监视组件
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();

    // 登录窗体的数据验证状态
    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    /*public RegisterViewModel(UserDataSource dataSource) {
        mDataSource = dataSource;
    }*/

    public Integer register(String username,String password,String displayname) {
        String res = "";
        Integer rowId = 0;
        JSONObject json = new JSONObject();
        try {
            json.put("userName", username);
            json.put("userPwd", password);
            json.put("displayName", displayname);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(json));
            res = NetClient.getNetClient().post("http://100.87.62.205:8080/app/user/register", requestBody);
            rowId = new JSONObject(res).getInt("rowId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowId;
    }

    // 检查登录数据是否有效
    public void registerDataChanged(String username, String password, String displayname) {
        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username,null,null));
        } else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password,null));
        } else if (!isDisplayNameValid(displayname)) {
            registerFormState.setValue(new RegisterFormState(null, null,R.string.invalid_displayname));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // 检查用户名是否有效
    private boolean isUserNameValid(String username) {
        return username != null && username.trim().length() > 5 && username.trim().length() < 18;
    }
    // 检查密码是否有效
    private boolean isPasswordValid(String password) {
        // 不等于空并且长度大于5
        return password != null && password.trim().length() > 5 && password.trim().length() < 18;
    }
    // 检查显示名称是否有效
    private boolean isDisplayNameValid(String displayName) {
        // 不等于空并且长度大于5
        return displayName != null && displayName.trim().length() > 2 && displayName.trim().length() < 9;
    }

}