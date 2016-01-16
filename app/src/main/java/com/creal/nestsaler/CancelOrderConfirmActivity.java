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
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.MD5PwdAction;
import com.creal.nestsaler.util.UIUtil;
import com.creal.nestsaler.util.Utils;
import com.creal.nestsaler.views.HeaderView;

import java.util.HashMap;
import java.util.Map;

public class CancelOrderConfirmActivity extends Activity {
    public static final int ACTIVITY_ID = R.layout.activity_cancel_order_confirm;
    public static final String INTENT_EXTRA_ORDER_ID = "ORDER_ID";
    public static final String INTENT_EXTRA_ORDER_AMOUNT = "ORDER_AMOUNT";
    public static final String INTENT_EXTRA_ORDER_CARD_NUM = "CARD_NUM";
    public static final String INTENT_EXTRA_ORDER_APP_NUM = "APP_NUM";

    private TextView mOrderId;
    private TextView mAmount;
    private TextView mCardNum;
    private TextView mAppNum;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order_confirm);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.center_cancel_order);
        headerView.setTitleLeft();
        headerView.hideLeftBtn();
        headerView.setLeftBtnListener(null);

        mOrderId = (TextView)findViewById(R.id.id_txt_order_id);
        mAmount = (TextView)findViewById(R.id.id_txt_order_amount);
        mCardNum = (TextView)findViewById(R.id.id_txt_order_card_id);
        mAppNum = (TextView)findViewById(R.id.id_txt_order_app_num);
        mPassword = (EditText)findViewById(R.id.id_txt_login_pwd);

        mOrderId.setText(getIntent().getStringExtra(INTENT_EXTRA_ORDER_ID));
        mAmount.setText("￥" + Utils.formatMoneyInYuan(getIntent().getStringExtra(INTENT_EXTRA_ORDER_AMOUNT)));
        mCardNum.setText(getIntent().getStringExtra(INTENT_EXTRA_ORDER_CARD_NUM));
        mAppNum.setText(getIntent().getStringExtra(INTENT_EXTRA_ORDER_APP_NUM));
    }

    public void onCancelClick(View view){
        setResult(0, null);
        finish();
    }

    public void onOkClick(View view){
        CharSequence password = mPassword.getText();
        if(TextUtils.isEmpty(password)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mPassword.startAnimation(shake);
            return;
        }

        Map parameters = new HashMap();
        parameters.put("app_number", mAppNum.getText().toString());
        parameters.put("card_num", mCardNum.getText().toString());
        parameters.put("money", String.valueOf( (int)(Double.parseDouble(getIntent().getStringExtra(INTENT_EXTRA_ORDER_AMOUNT)) * 100)) );
        parameters.put("order_id", mOrderId.getText().toString());
        parameters.put("password", mPassword.getText().toString());
        parameters.put("original_time", "");
        parameters.put("serial_number", "");
        final MD5PwdAction cancelOrderAction = new MD5PwdAction(this, Constants.URL_CANCEL_ORDER, parameters, "password");
        final Dialog progressDialog = UIUtil.showLoadingDialog(this, getString(R.string.signing), false);
        cancelOrderAction.execute(new AbstractAction.UICallBack() {
            public void onSuccess(Object result) {
                progressDialog.dismiss();
                Intent intent = new Intent(CancelOrderConfirmActivity.this, CancelOrderSuccDialog.class);
                intent.putExtra(CancelOrderSuccDialog.INTENT_EXTRA_ORDER_ID, mOrderId.getText().toString());
                startActivityForResult(intent, CancelOrderSuccDialog.ACTIVITY_ID);
            }

            public void onFailure(AbstractAction.ActionError error) {
                progressDialog.dismiss();
                Toast.makeText(CancelOrderConfirmActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                //Test code below
//                Intent intent = new Intent(CancelOrderConfirmActivity.this, CancelOrderSuccDialog.class);
//                intent.putExtra(CancelOrderSuccDialog.INTENT_EXTRA_ORDER_ID, mOrderId.getText().toString());
//                startActivityForResult(intent, CancelOrderSuccDialog.ACTIVITY_ID);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CancelOrderSuccDialog.ACTIVITY_ID){
            if(resultCode != 0){
                setResult(1, null);
                finish();
            }
        }
    }
}
