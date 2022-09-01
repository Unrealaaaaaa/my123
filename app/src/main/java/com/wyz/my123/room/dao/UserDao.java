package com.wyz.my123.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wyz.my123.room.entity.User;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface UserDao {

    @Query("select * from user where user_name = :username and user_pwd = :password")
    Flowable<User> login(String username, String password);

    @Insert(onConflict = OnConflictStrategy.IGNORE)// 相同则忽略 仅对标识了unique的字段有效
    Single<Long> register(User user);
}
