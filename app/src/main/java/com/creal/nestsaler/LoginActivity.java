package com.creal.nestsaler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.LoginAction;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.UIUtil;
import com.creal.nestsaler.views.HeaderView;

public class LoginActivity extends Activity {
    private EditText mAppNumber;
    private EditText mPassword;
    private EditText mActiveCode;
    private boolean isRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.login_first_time);
        headerView.setTitleLeft();
        headerView.hideLeftBtn();
        headerView.setLeftBtnListener(null);

        mAppNumber = (EditText)findViewById(R.id.id_txt_appNumber_id);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mActiveCode = (EditText)findViewById(R.id.id_txt_auth_code);

        mAppNumber.setText(PreferenceUtil.getString(this, Constants.APP_USER_APP_NUM, ""));
        mPassword.setText(PreferenceUtil.getString(this, Constants.APP_USER_PWD, ""));
        init(headerView);
    }

    private void init(HeaderView headerView ){
        String active = PreferenceUtil.getString(this, Constants.APP_ACCOUNT_ACTIVE, null);
        if( Boolean.FALSE.toString().equalsIgnoreCase(active)){
            findViewById(R.id.id_first_login_panel).setVisibility(View.GONE);
        }else{
            //已经注册
            headerView.setTitle(R.string.login_account_login);
            isRegister = true;
        }
    }

    public void onLoginClick(View view){
        CharSequence appNumber = mAppNumber.getText();
        if(TextUtils.isEmpty(appNumber)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mAppNumber.startAnimation(shake);
            return;
        }
        CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return;
        }

        CharSequence activationCode = mActiveCode.getText();
        if(TextUtils.isEmpty(activationCode)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mActiveCode.startAnimation(shake);
            return;
        }


        PreferenceUtil.saveString(this, Constants.APP_USER_APP_NUM, appNumber.toString());
        PreferenceUtil.saveString(this, Constants.APP_USER_PWD, password.toString());
        final LoginAction loginAction = new LoginAction(this, isRegister, appNumber.toString(), password.toString(), activationCode.toString() );
        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.signing), false);
        loginAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                progressDialog.dismiss();
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        startNext();
    }

    private void startNext(){
        Intent intent = new Intent(this, CenterActivity.class);
        startActivity(intent);
        finish();
    }
}
