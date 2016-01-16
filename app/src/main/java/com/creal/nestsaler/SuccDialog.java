package com.creal.nestsaler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class SuccDialog extends Activity {
    public static final String INTENT_EXTRA_DATA        = "EXTRA_DATA";
    public static final String DLG_TYPE                 = "DLG_TYPE";
    public static final String DLG_TYPE_CANCEL_ORDER    = "CANCEL_ORDER";
    public static final String DLG_TYPE_CHARGE          = "CHARGE";

    public static final int ACTIVITY_ID = R.layout.dialog_cancel_order_succ;
    private TextView mMsgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order_succ);
        mMsgView = (TextView)findViewById(R.id.id_txt_desc);


        if(DLG_TYPE_CANCEL_ORDER.equals(getIntent().getStringExtra(DLG_TYPE))) {
            String orderId = getIntent().getStringExtra(INTENT_EXTRA_DATA);
            if (orderId != null) {
                String desc = String.format(getString(R.string.cancel_order_result_desc), orderId);
                Spannable span = new SpannableString(desc);
                span.setSpan(new ForegroundColorSpan(Color.RED), 4, 4 + orderId.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mMsgView.setText(span);
            }
        }else if(DLG_TYPE_CHARGE.equals(getIntent().getStringExtra(DLG_TYPE))) {
            String orderMoney = getIntent().getStringExtra(INTENT_EXTRA_DATA);
            if (orderMoney != null) {
                String desc = String.format(getString(R.string.charge_result_msg), orderMoney);
                Spannable span = new SpannableString(desc);
                span.setSpan(new ForegroundColorSpan(Color.RED), 6, 6 + orderMoney.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                mMsgView.setText(span);
            }
        }
    }

    public void onBackPressed() {
        onBackToHomeClick(null);
    }

    public void onBackToHomeClick(View view){
        setResult(1, null);
        finish();
    }
}
