package com.creal.nestsaler.model;

import com.creal.nestsaler.actions.ActionRespObject;

import org.json.JSONException;
import org.json.JSONObject;

public class SalerAccount implements ActionRespObject<SalerAccount> {

    private String id;
    private String username;
    private String password;
    private String authCode;

    public SalerAccount(String name, String authCode, String password) {
        this.username = name;
        this.password = password;
        this.authCode = authCode;
        this.id = name+password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SalerAccount fillWithJSON(JSONObject jsonObject) throws JSONException {
        return null;
    }
}
