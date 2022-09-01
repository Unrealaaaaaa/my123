package com.wyz.my123.datasource;

import com.wyz.my123.room.entity.User;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface UserDataSource {

    Flowable<User> login(String username, String password);

    Single<Long> register(User user);
}
