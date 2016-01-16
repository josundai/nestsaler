package com.creal.nestsaler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.creal.nestsaler.actions.ParallelTask;
import com.creal.nestsaler.util.PreferenceUtil;

public class WelcomeActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        initialize();
    }

    private void initialize() {
        new ParallelTask<Void>() {
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                    PreferenceUtil.saveString(WelcomeActivity.this, Constants.APP_ACCOUNT_ACTIVE, Boolean.TRUE.toString());
                    PreferenceUtil.saveString(WelcomeActivity.this, Constants.APP_USER_APP_NUM, "123123");
                    PreferenceUtil.saveString(WelcomeActivity.this, Constants.APP_BINDING_KEY, "76065606194009136801671877123861577");
                } catch (InterruptedException e) {
                }
                return null;
            }

            protected void onPostExecute(Void result) {
                showNext();
            }
        }.execute();
    }

    private void showNext() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
