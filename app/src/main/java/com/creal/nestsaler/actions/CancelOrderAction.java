package com.creal.nestsaler.actions;

import android.content.Context;


import com.creal.nestsaler.Constants;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CancelOrderAction extends JSONObjectAction {
    public CancelOrderAction(Context context, String url, Map parameters) {
        super(context, url, parameters);
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = super.getRequestBody(timeStr);
        if(parameters.has("password")) {
            String password = parameters.getString("password");
            String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
            parameters.put("password", Utils.md5(Utils.md5(password) + timeStr + key));
        }
        return parameters;
    }
}