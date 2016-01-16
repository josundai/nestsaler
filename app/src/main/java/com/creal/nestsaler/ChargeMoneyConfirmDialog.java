package com.creal.nestsaler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.creal.nestsaler.actions.AbstractAction;
import com.creal.nestsaler.actions.JSONObjectAction;
import com.creal.nestsaler.util.PreferenceUtil;

import org.json.JSONException;
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
            mMoneyView.setText( this.getString(R.string.charge_money_number) + money);
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
        action.execute(new JSONObjectAction.UICallBack() {
            public void onSuccess(Object result) {
                if( result instanceof JSONObject){
                    JSONObject jObj = (JSONObject) result;
                    try {
                        String orderNumber = jObj.getString("order_id");
                        String orderMoney = jObj.getString("order_time");
                        String money = jObj.getString("money");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //TODO: 提示生成订单。然后等候付款方付款。此时是什么界面？？？
                    /**
                    Intent intent = new Intent(ChargeMoneyConfirmDialog.this, ChargeMoneyConfirmDialog.class);
                    intent.putExtra(ChargeMoneyConfirmDialog.ORDER_ID, orderNumber);
                    intent.putExtra(ChargeMoneyConfirmDialog.ORDER_MONEY, orderMoney);
//					startActivityForResult(intent, CancelOrderSuccDialog.ACTIVITY_ID);
                    startActivity(intent);
                     **/

                }
            }
            public void onFailure(AbstractAction.ActionError error) {
//				dialog.dismiss();
                Toast.makeText(ChargeMoneyConfirmDialog.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }
}
