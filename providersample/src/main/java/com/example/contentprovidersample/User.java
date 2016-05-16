/**
 * 功    能：TODO
 * 类 列 表：User
 * 作　　者：齐 涛
 * 创建日期：2016/3/24  8:53
 * 注　　意：TODO
 * Copyright (c) ：2015 by Xiao2Lang.版权所有.<br>
 */

package com.example.contentprovidersample;

/**
 * 功    能：TODO  <br>
 * 作　　者：齐 涛  <br>
 * 创建日期：2016/3/24  8:53 <br>
 * 注　　意：TODO <br>
 */
public class User {
    private int userId;
    private String name;
    private boolean male;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", male=" + male +
                '}';
    }
}
