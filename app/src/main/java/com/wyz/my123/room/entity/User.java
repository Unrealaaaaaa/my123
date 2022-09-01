package com.wyz.my123.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"user_name"},unique = true)}) // 设置唯一
public class User {

    // 设置主键并自动增长
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_pwd")
    private String userPwd;

    @ColumnInfo(name = "display_name")
    private String displayName;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public User(){
    }

    @Ignore
    public User(Integer id, String userName, String userPwd, String displayName) {
        this.id = id;
        this.userName = userName;
        this.userPwd = userPwd;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
