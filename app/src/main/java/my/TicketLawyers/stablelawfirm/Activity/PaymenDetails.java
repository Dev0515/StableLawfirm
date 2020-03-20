package my.TicketLawyers.stablelawfirm.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;



import org.json.JSONException;
import org.json.JSONObject;

import my.TicketLawyers.stablelawfirm.R;

public class PaymenDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymen_details);
        Intent b=getIntent();
        try{
            JSONObject jsonObject=new JSONObject(b.getStringExtra("Paymentdetails"));
            showdetails(jsonObject.getJSONObject("response"),b.getStringExtra("paymentamt"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void showdetails(JSONObject response, String paymentAmt) {
        try {
            Log.e("1", "showdetails: "+response.getString("id") );
            Log.e("2", "state: "+response.getString("state") );
            Log.e("3", "amount: "+paymentAmt );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent bb=new Intent (this,MainActivity.class);
        startActivity(bb);
    }
}
