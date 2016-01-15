package com.creal.nestsaler;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.UIUtil;
import com.creal.nestsaler.views.HeaderView;

/**
 * Created by dai on 2015/12/16.
 * The Center menu Activity.
 */
public class CenterActivity extends Activity {

    TextView mScan;
    TextView mHistory;
    TextView mRebind;
    TextView mChangePassword;
    TextView mCancelOrder;
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
        mRebind = (TextView) findViewById(R.id.id_rebind);
        mChangePassword = (TextView) findViewById(R.id.id_CenterChangePassword);
        mCancelOrder = (TextView) findViewById(R.id.id_cancel_order);
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
        mRebind.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        UIUtil.showConfirmDialog(CenterActivity.this, R.string.rebind_confirm, R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                PreferenceUtil.removeKey(CenterActivity.this, Constants.APP_ACCOUNT_ACTIVE);
                                PreferenceUtil.removeKey(CenterActivity.this, Constants.APP_USER_APP_NUM);
                                PreferenceUtil.removeKey(CenterActivity.this, Constants.APP_BINDING_KEY);
                                Intent intent = new Intent(CenterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    }
                });
        mChangePassword.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(CenterActivity.this, ChangePwdActivity.class);
                        startActivity(intent);
                    }
                });
        mCancelOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CenterActivity.this, CancelOrderActivity.class);
                startActivity(intent);
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
