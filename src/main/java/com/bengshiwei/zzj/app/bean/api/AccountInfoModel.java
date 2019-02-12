package com.bengshiwei.zzj.app.bean.api;

import com.google.gson.annotations.Expose;

public class AccountInfoModel {
    @Expose
    private String name;
    @Expose
    private String password;
    @Expose
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
