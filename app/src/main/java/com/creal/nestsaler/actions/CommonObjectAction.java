package com.creal.nestsaler.actions;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CommonObjectAction<T extends ActionRespObject<T>> extends AbstractAction<T> {
    private static final String tag = "TT-CommonObjectAction";
    private Map<String, String> mReqParams;
    private Class<T> mClass;
    public CommonObjectAction(Context context, String url, Map parameters, Class<T> tClass) {
        super(context);
        mServiceId = url;
        mURL = url;
        mReqParams = parameters;
        mClass = tClass;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        return new JSONObject(mReqParams);
    }

    @Override
    protected T createRespObject(JSONObject response) throws JSONException {
        try {
            if(mClass != null) {
                T t = mClass.newInstance();
                return t.fillWithJSON(response);
            }
            return null;
        } catch (Exception e) {
            Log.e(tag, "Failed to create pagination item : " + mClass.getSimpleName(), e);
            return null;
        }
    }
}