package com.creal.nestsaler;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.MD5PwdAction;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.UIUtil;
import com.creal.nestsaler.util.Utils;
import com.creal.nestsaler.views.HeaderView;

import java.util.HashMap;
import java.util.Map;

public class ChangePwdActivity extends Activity {

    private EditText mOldPwd;
    private EditText mNewPwd;
    private EditText mNewPwdAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.change_pwd);
        headerView.setTitleLeft();

        mOldPwd = (EditText)findViewById(R.id.id_txt_old_pwd);
        mNewPwd = (EditText)findViewById(R.id.id_txt_new_pwd);
        mNewPwdAgain = (EditText)findViewById(R.id.id_txt_new_pwd_again);
    }

    public void onSaveChangesClick(View view){
        final CharSequence oldPwd = mOldPwd.getText();
        if(TextUtils.isEmpty(oldPwd)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mOldPwd.startAnimation(shake);
            return;
        }
        final CharSequence newPwd = mNewPwd.getText();
        if(TextUtils.isEmpty(newPwd) || newPwd.length() < 6){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mNewPwd.startAnimation(shake);
            return;
        }
        final CharSequence newPwdAgain = mNewPwdAgain.getText();
        if(TextUtils.isEmpty(newPwdAgain) || (!TextUtils.equals(newPwd, newPwdAgain))){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mNewPwdAgain.startAnimation(shake);
            return;
        }

        final Dialog dialog = UIUtil.showLoadingDialog(this, getString(R.string.change_pwd), false);
        Map parameters = new HashMap();
        String appNum = PreferenceUtil.getString(this, Constants.APP_USER_APP_NUM, null);
        parameters.put("app_number", appNum);
        parameters.put("old_pwd", oldPwd.toString());
        parameters.put("new_pwd", Utils.md5(newPwd.toString()));
        MD5PwdAction action = new MD5PwdAction(this, Constants.URL_CHANGE_PWD, parameters, "old_pwd");
        action.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                dialog.dismiss();
                Toast.makeText(ChangePwdActivity.this, R.string.change_pwd_succ, Toast.LENGTH_SHORT).show();
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
                dialog.dismiss();
                Toast.makeText(ChangePwdActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
