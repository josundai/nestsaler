package com.creal.nestsaler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.views.HeaderView;

public class CancelOrderActivity extends Activity {
    private EditText mOrderId;
    private EditText mAmount;
    private EditText mCardNum;
    private EditText mAppNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_order);

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.center_cancel_order);
        headerView.setTitleLeft();
        headerView.hideLeftBtn();
        headerView.setLeftBtnListener(null);

        mOrderId = (EditText)findViewById(R.id.id_txt_order_id);
        mAmount = (EditText)findViewById(R.id.id_txt_amount);
        mCardNum = (EditText)findViewById(R.id.id_txt_card);
        mAppNum = (EditText)findViewById(R.id.id_txt_app_num);
        String appNum = PreferenceUtil.getString(this, Constants.APP_USER_APP_NUM, null);
        if(!TextUtils.isEmpty(appNum)){
            mAppNum.setText(appNum);
        }
    }

    public void onSubmitClick(View view){
        CharSequence ordeId = mOrderId.getText();
        if(TextUtils.isEmpty(ordeId)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mOrderId.startAnimation(shake);
            return;
        }
        CharSequence amount = mAmount.getText();
        if(TextUtils.isEmpty(amount)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mAmount.startAnimation(shake);
            return;
        }

        CharSequence cardNum = mCardNum.getText();
        if(TextUtils.isEmpty(cardNum)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mCardNum.startAnimation(shake);
            return;
        }

        CharSequence appNum = mAppNum.getText();
        if(TextUtils.isEmpty(appNum)){
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
            mAppNum.startAnimation(shake);
            return;
        }
        Intent intent = new Intent(this, CancelOrderConfirmActivity.class);
        intent.putExtra(CancelOrderConfirmActivity.INTENT_EXTRA_ORDER_AMOUNT, amount.toString());
        intent.putExtra(CancelOrderConfirmActivity.INTENT_EXTRA_ORDER_ID, ordeId.toString());
        intent.putExtra(CancelOrderConfirmActivity.INTENT_EXTRA_ORDER_CARD_NUM, cardNum.toString());
        intent.putExtra(CancelOrderConfirmActivity.INTENT_EXTRA_ORDER_APP_NUM, appNum.toString());
        startActivityForResult(intent, CancelOrderConfirmActivity.ACTIVITY_ID);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CancelOrderConfirmActivity.ACTIVITY_ID){
            if(resultCode != 0){
                finish();
            }
        }
    }
}
