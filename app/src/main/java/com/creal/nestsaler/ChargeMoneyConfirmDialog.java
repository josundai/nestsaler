package com.creal.nestsaler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.JSONObjectAction;
import com.creal.nestsaler.util.JSONUtil;
import com.creal.nestsaler.util.PreferenceUtil;
import com.creal.nestsaler.util.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChargeMoneyConfirmDialog extends Activity {
    public static final String ORDER_ID = "ORDER_ID";
    public static final String ORDER_MONEY = "ORDER_MONEY";

    private TextView mOrderNumView;
    private TextView mMoneyView;
    private Button button;

    private String money;
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_charge_money_confirm);
        button = ((Button)findViewById(R.id.id_charge_money_submit));
        mMoneyView = (TextView)findViewById(R.id.id_charge_money_number);
        mOrderNumView = (TextView)findViewById(R.id.id_charge_money_id);
        orderId = getIntent().getStringExtra(ORDER_ID);
        money = getIntent().getStringExtra(ORDER_MONEY);

        if(orderId != null) {
            mOrderNumView.setText(this.getString(R.string.charge_money_orderNumber)+ orderId );
            mMoneyView.setText( this.getString(R.string.charge_money_number) + Utils.formatMoney(money));
        }
    }


    public void onConfirmChargeClick(View view) {
        //send the real request.
        Map parameters = new HashMap();
        String appNum = PreferenceUtil.getString(this, Constants.APP_USER_APP_NUM, null);
        parameters.put("money", money );
        parameters.put("app_number", appNum);
        parameters.put("prepaid_id", orderId);

        JSONObjectAction action = new JSONObjectAction(this, Constants.URL_QUERY_CHARGE_RESULT, parameters);
        action.execute(new JSONObjectAction.UICallBack<JSONObject>() {
            public void onSuccess(JSONObject jObj) {
                String orderMoney = JSONUtil.getString(jObj, "money", "");
                Intent intent = new Intent(ChargeMoneyConfirmDialog.this, SuccDialog.class);
                intent.putExtra(SuccDialog.INTENT_EXTRA_DATA, orderMoney);
                startActivity(intent);
                finish();
            }

            public void onFailure(AbstractAction.ActionError error) {
//				dialog.dismiss();
                Toast.makeText(ChargeMoneyConfirmDialog.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
