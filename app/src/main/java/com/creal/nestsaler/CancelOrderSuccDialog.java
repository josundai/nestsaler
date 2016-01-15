package com.creal.nestsaler;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

public class CancelOrderSuccDialog extends Activity {
    public static final String INTENT_EXTRA_ORDER_ID = "ORDER_ID";

    public static final int ACTIVITY_ID = R.layout.dialog_cancel_order_succ;
    private TextView mMsgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_order_succ);
        mMsgView = (TextView)findViewById(R.id.id_txt_desc);
        String orderId = getIntent().getStringExtra(INTENT_EXTRA_ORDER_ID);
        if(orderId != null) {
            String desc = String.format(getString(R.string.cancel_order_result_desc), orderId);
            Spannable span = new SpannableString(desc);
            span.setSpan(new ForegroundColorSpan(Color.RED), 4, 4 + orderId.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            mMsgView.setText(span);
        }
    }

    public void onBackToHomeClick(View view){
        setResult(1, null);
        finish();
    }
}
