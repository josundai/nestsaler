package com.creal.nestsaler.actions;

import android.content.Context;

import com.creal.nestsaler.Constants;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MD5PwdAction extends JSONObjectAction {
    private String mPwdKey;
    public MD5PwdAction(Context context, String url, Map parameters, String pwdKey) {
        super(context, url, parameters);
        mPwdKey = pwdKey;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(parameters.has(mPwdKey)) {
            String password = parameters.getString(mPwdKey);
            String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
            parameters.put(mPwdKey, Utils.md5(Utils.md5(password) + timeStr + key));
        }
        return parameters;
    }
}