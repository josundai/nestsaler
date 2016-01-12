package com.creal.nestsaler.actions;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class StringAction extends AbstractAction<String> {
    private Map<String, String> mReqParams;
    private String mResponseKey;
    public StringAction(Context context, String url, Map parameters, String responseKey) {
        super(context);
        this.mReqParams = parameters;
        this.mServiceId = url;
        mURL = url;
        mResponseKey = responseKey;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        return new JSONObject(mReqParams);
    }

    @Override
    protected String createRespObject(JSONObject response) throws JSONException {
        if(response.has(mResponseKey)){
            return response.getString(mResponseKey);
        }
        return null;
    }
}