package com.creal.nestsaler;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nestsaler.util.UIUtil;
import com.creal.nestsaler.views.HeaderView;

/**
 * Created by dai on 2015/12/16.
 * The Center menu Activity.
 */
public class CenterActivity extends Activity {

    TextView mScan;
    TextView mHistory;
    TextView mChangePassword;
    TextView mAppSetting;
    TextView mAppUpdate;
    TextView mAboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_center);

        HeaderView headerView = (HeaderView) findViewById(R.id.center_header);
        headerView.hideRightImage();
        headerView.setTitleLeft();
        headerView.setTitle(R.string.welcome_sign_in);
        headerView.hideLeftBtn();
        headerView.setRightText(R.string.page_title_exit);
        headerView.setRightTextListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
        headerView.setLeftBtnListener(null);

        mScan = (TextView) findViewById(R.id.id_CenterScan);
        mHistory = (TextView) findViewById(R.id.id_CenterHistory);
        mChangePassword = (TextView) findViewById(R.id.id_CenterChangePassword);
        mAppSetting = (TextView) findViewById(R.id.id_CenterSoftSetting);
        mAppUpdate = (TextView) findViewById(R.id.id_CenterSoftUpdate);
        mAboutUs = (TextView) findViewById(R.id.id_CenterAboutUs);
        addListeners();
    }

    private void logout(){
        UIUtil.showConfirmDialog(this, R.string.confirm_logout,
                R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        dialog.dismiss();
                    }
                },
                R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }
        );
    }

    private void addListeners() {
        mScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CenterActivity.this, ScanInputMoneyActivity.class);
                startActivity(intent);
            }
        });
        mHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CenterActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
        mChangePassword.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CenterActivity.this, ChangePwdActivity.class);
                        startActivity(intent);
                    }

                });
        mAppSetting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(CenterActivity.this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });
        mAppUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(CenterActivity.this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
            }
        });
        mAboutUs.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CenterActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                    }
                });

    }


}