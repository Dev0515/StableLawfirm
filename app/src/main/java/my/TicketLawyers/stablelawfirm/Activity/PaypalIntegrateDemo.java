package my.TicketLawyers.stablelawfirm.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.model.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PaypalIntegrateDemo extends AppCompatActivity {

    EditText edt1;
    Button btn;
    public static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config =new PayPalConfiguration().
            environment( PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.Paypal_client_id);
    String amt="10";
    String item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_integrate_demo);
        btn=(Button)findViewById(R.id.order);
       // edt1=(EditText)findViewById(R.id.textView1);
        Intent intent1 = getIntent();
        item = intent1.getStringExtra("item");

        Intent intent=new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processpayment();
            }
        });

    }
    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }

    private void processpayment() {

        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(String.valueOf(amt)),"USD",item, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent aa=new Intent(this, PaymentActivity.class);
        aa.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        aa.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(aa,PAYPAL_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PAYPAL_REQUEST_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation!=null)
                {
                    try
                    {
                        String paymentetails=confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this,PaymenDetails.class)
                                .putExtra("Paymentdetails",paymentetails)
                                .putExtra("paymentamt",amt));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
