package com.creal.nestsaler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
        if( Boolean.TRUE.toString().equalsIgnoreCase(active)){
            //已经注册
            findViewById(R.id.id_first_login_panel).setVisibility(View.GONE);
            headerView.setTitle(R.string.login_account_login);
        }else{
            ((Button)findViewById(R.id.id_btn_login)).setText(R.string.active);
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
        if(isRegister && TextUtils.isEmpty(activationCode)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mActiveCode.startAnimation(shake);
            return;
        }
        final LoginAction loginAction = new LoginAction(this, isRegister, appNumber.toString(), password.toString(), isRegister? activationCode.toString() : null );
        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.signing), false);
        loginAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                progressDialog.dismiss();
                startNext();
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startNext(){
        Intent intent = new Intent(this, CenterActivity.class);
        startActivity(intent);
    }
}
