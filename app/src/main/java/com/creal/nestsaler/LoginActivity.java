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
    private EditText mCardId;
    private EditText mPassword;
    private EditText mAuthCode;
    public static final String INTENT_EXTRA_ACTION_TYPE = "action_type";
    public static final String ACTION_REST_GESTURE_PWD = "reset_gesture_password";

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

        mCardId = (EditText)findViewById(R.id.id_txt_card_id);
        mPassword = (EditText)findViewById(R.id.id_txt_password);
        mAuthCode = (EditText)findViewById(R.id.id_txt_auth_code);

        mCardId.setText(PreferenceUtil.getString(this, Constants.APP_USER_CARD_NUM, ""));
        mPassword.setText(PreferenceUtil.getString(this, Constants.APP_USER_PWD, ""));
        init();
    }

    private void init(){
        String active = PreferenceUtil.getString(this, Constants.APP_ACCOUNT_ACTIVE, null);
        if(Boolean.FALSE.toString().equalsIgnoreCase(active)){
            findViewById(R.id.id_first_login_panel).setVisibility(View.GONE);
        }
    }

    public void onLoginClick(View view){
//        CharSequence cardId = mCardId.getText();
//        if(TextUtils.isEmpty(cardId)){
//            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//            mCardId.startAnimation(shake);
//            return;
//        }
//        CharSequence password = mPassword.getText();
//        if(TextUtils.isEmpty(password)){
//            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//            mPassword.startAnimation(shake);
//            return;
//        }

//        PreferenceUtil.saveString(this, Constants.APP_USER_CARD_NUM, cardId.toString());
//        PreferenceUtil.saveString(this, Constants.APP_USER_PWD, password.toString());
//        final LoginAction loginAction = new LoginAction(this, cardId.toString(), password.toString());
//        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.signing), false);
//        loginAction.execute(new AbstractAction.UICallBack() {
//            public void onSuccess(Object result) {
//                progressDialog.dismiss();
//                finish();
//            }
//
//            public void onFailure(AbstractAction.ActionError error) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        startNext();
    }

    private void startNext(){
        Intent intent = new Intent(this, CenterActivity.class);
        startActivity(intent);
        finish();
    }
}
