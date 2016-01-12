package com.creal.nestsaler.actions;

import android.content.Context;

import com.creal.nestsaler.Constants;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAction extends AbstractAction {
    private String mCardNum;
    private String mPassword;

    public LoginAction(Context context, String cardNum, String pwd) {
        super(context);
        this.mCardNum = cardNum;
        this.mPassword = pwd;
        this.mServiceId = "LOGIN";
        mURL = Constants.URL_LOGIN;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put("card_num", mCardNum);
        String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
        parameters.put("password", Utils.md5(Utils.md5(mPassword) + key + timeStr));
        return parameters;
    }

    @Override
    protected Object createRespObject(JSONObject response) throws JSONException {
        PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_ID, response.getString("card_id"));
        PreferenceUtil.saveString(mAppContext, Constants.APP_USER_CARD_NUM, response.getString("card_num"));
        PreferenceUtil.saveString(mAppContext, Constants.APP_USER_AUTHORIZED, Boolean.TRUE.toString());
        return null;
    }
}
