package com.creal.nestsaler.actions;

import android.content.Context;

import com.creal.nestsaler.Constants;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAction extends AbstractAction {
    private String appNumber;
    private String mPassword;
    private String mActivationCode;
    private boolean isRegister;

    public LoginAction(Context context, boolean isRegister, String appNumber, String pwd, String mActivationCode) {
        super(context);
        this.appNumber = appNumber;
        this.mPassword = pwd;
        this.mActivationCode = mActivationCode;
        this.mServiceId = isRegister? "ACTIVATION" :"LOGIN";
        this.isRegister = isRegister;
        mURL = isRegister? Constants.URL_ACTIVE_ACCOUNT : Constants.URL_LOGIN;
    }

    protected JSONObject getRequestBody(String timeStr) throws JSONException{
        JSONObject parameters = new JSONObject();
        parameters.put("app_number", appNumber);
        if( isRegister ){
            //TODO: double confirm the double md5.
            parameters.put("pay_password", Utils.md5(mPassword));
        }else{
            String key = PreferenceUtil.getString(mAppContext, Constants.APP_BINDING_KEY, Constants.APP_DEFAULT_KEY);
            parameters.put("password", Utils.md5(Utils.md5(mPassword) + timeStr + key));
        }
        return parameters;
    }

    @Override
    protected Object createRespObject(JSONObject response) throws JSONException {
        if(isRegister && response.has(Constants.KEY_KEY)) {
            PreferenceUtil.saveString(mAppContext, Constants.APP_USER_APP_NUM, appNumber);
            PreferenceUtil.saveString(mAppContext, Constants.APP_BINDING_KEY, response.getString(Constants.KEY_KEY));
            PreferenceUtil.saveString(mAppContext, Constants.APP_ACCOUNT_ACTIVE, Boolean.TRUE.toString());
        }
        return null;
    }

    protected String getEncryptKey(){
        return isRegister?  mActivationCode : super.getEncryptKey();
    }
}
