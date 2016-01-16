package com.creal.nestsaler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.creal.nestsaler.views.HeaderView;


/**
 * Created by dai on 2015/12/17.
 */
public class ScanInputMoneyActivity extends Activity {

    TextView mMoneyNumber;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_inputmoney);

        Bundle bundle = this.getIntent().getExtras();
        String money = null;
        if( bundle!=null ){
             money = bundle.getString("chargeMoney");
        }
        if( money==null ){
            money = "";
        }

        HeaderView headerView = (HeaderView) findViewById(R.id.header);
        headerView.hideRightImage();
        headerView.setTitle(R.string.receivemoney);
        headerView.setTitleLeft();
        mMoneyNumber = (TextView) findViewById(R.id.id_input_money);
        mMoneyNumber.setText( money );
        submitButton = (Button) findViewById(R.id.id_scan_submit_button);

        submitButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String errorMsg = null;

                    String moneyNumber = mMoneyNumber.getText().toString();
                    Double money = null;
                    try{
                        money = Double.parseDouble(moneyNumber);
                    }catch (Exception e){
                        errorMsg = getString(R.string.error_field_be_number);
                    }
                    if( money!=null && money <=0){
                        errorMsg = getString(R.string.error_field_be_positive);
                    }
                    if( errorMsg != null){
                        mMoneyNumber.setError( errorMsg );
                        mMoneyNumber.requestFocus();
                    }else{
//                        Toast.makeText(ScanInputMoneyActivity.this, "Trigger scan activity!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanInputMoneyActivity.this, ScanBinCodeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putDouble("chargeMoney", money);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
            }
        );
    }

}
