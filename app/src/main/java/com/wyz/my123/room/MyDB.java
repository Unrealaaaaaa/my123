package com.wyz.my123.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.wyz.my123.room.dao.UserDao;
import com.wyz.my123.room.entity.User;

@Database(entities = {User.class},version = 2, exportSchema = false)
public abstract class MyDB extends RoomDatabase {

    // 定义一个抽象方法, 用于用户拿到userDao并操作数据库
    public abstract UserDao getUserDao();

    private static volatile MyDB INSTANCE;

    public static MyDB getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder
                            (context.getApplicationContext(), MyDB.class, "1+2=3")
                            .build();
        }
        return INSTANCE;
    }
}
