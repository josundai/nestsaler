package com.creal.nestsaler.actions;

import android.content.Context;

import com.creal.nestsaler.Constants;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ChangePwdAction extends JSONObjectAction {
    public ChangePwdAction(Context context, String url, Map parameters) {
        super(context, url, parameters);
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(parameters.has("old_pwd")) {
            String password = parameters.getString("old_pwd");
            String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
            parameters.put("old_pwd", Utils.md5(Utils.md5(password) + timeStr + key));
        }
        return parameters;
    }
}