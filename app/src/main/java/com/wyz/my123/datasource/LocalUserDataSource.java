package com.wyz.my123.datasource;

import com.wyz.my123.room.entity.User;
import com.wyz.my123.room.dao.UserDao;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class LocalUserDataSource implements UserDataSource {

    private UserDao dao;

    public LocalUserDataSource(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Flowable<User> login(String username, String password) {
        return dao.login(username, password);
    }

    @Override
    public Single<Long> register(User user) {
        return dao.register(user);
    }
}
