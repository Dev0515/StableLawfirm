package my.TicketLawyers.stablelawfirm.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import my.TicketLawyers.stablelawfirm.Activity.PaymenDetails;

import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.model.Config;
import my.TicketLawyers.stablelawfirm.model.MySingleton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class HireUsFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String item,FinalJSonObject, amt="10";
    LinearLayout cardlayout;
    public static final int PAYPAL_REQUEST_CODE=7171;
    private static PayPalConfiguration config =new PayPalConfiguration().
            environment( PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.Paypal_client_id);

    final String get_token = "http://bytecodetechnologies.co.in/main.php";
    final String send_payment_details = "http://bytecodetechnologies.co.in/checkout.php";
    Button submitpayment;
    HashMap<String, String> paramHash;
    Stripe stripe;
    Card card;
    Token tok;
    int selectedId;
    ProgressDialog pd;
    String HTTP_URL="http://bytecodetechnologies.co.in/StableLawfirm/gaurav.php";
    ImageView paypal1;
    String[] objects = { "select a category","Moving against traffic", "Improper passing", "Unlawful use of median strip", "Operating constructor vehicle in excess of 45 mph", "\tOperating motorized bicycle on a restricted highway",
            "More than one person on a motorized bicycle", "Failure to yield to pedestrian in crosswalk", "Failure to yield to pedestrian in crosswalk; passing a vehicle yielding to pedestrian in crosswalk", "Driving through safety zone",
            "Racing on highway","Improper action or omission on grades and curves","Failure to observe direction of officer",
            "Failure to stop vehicle before crossing sidewalk","Failure to yield to pedestrians or vehicles while entering or leaving highway",
            "Driving on public or private property to avoid a traffic sign or signal","Operating a motor vehicle on a sidewalk",
            "Failure to obey direction of officer","Failure to observe traffic signals","Failure to keep right","Improper operating of vehicle on divided highway or divider"
            ,"Failure to keep right at intersection","Failure to pass to right of vehicle proceeding in opposite direction"
            ,"Improper passing on right or off roadway","Wrong way on a one-way street","Improper passing in no passing zone"
            ,"Failure to yield to overtaking vehicle","Failure to observe traffic lanes","Tailgating","Failure to yield at intersection","Failure to use proper entrances to limited access highways","Failure to yield to emergency vehicles"
            ,"Reckless driving","Careless driving","Destruction of agricultural or recreational property","Slow speed blocking traffic","Driving in an unsafe manner (points only assessed for the third or subsequent violation(s) within a five-year period.)","Exceeding maximum speed 1-14 mph over limit","Exceeding maximum speed 15-29 mph over limit Exceeding maximum speed 30 mph or more over limit"
            ,"Failure to stop for traffic light","Improper turn at traffic light","Failure to stop at flashing red signal","Failure to stop for police whistle","Improper right or left turn","Improper turn from approved turning course","Improper U-turn","Failure to give proper signal","Improper backing or turning in street","Improper crossing of railroad grade crossing","mproper crossing of bridge","Improper crossing of railroad grade crossing by certain vehicles",
            "Improper passing of school bus","Improper passing of frozen dessert truck","Leaving the scene of an accident - No personal injury Personal injury","Failure to observe stop or yield signs","Racing on highway",
            "Moving violation out of state"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.hireusfrag, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Hire us Now");
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        paypal1=(ImageView)view.findViewById(R.id.paypal);
        cardlayout=(LinearLayout)view.findViewById(R.id.cardlayout);
        submitpayment=(Button)view.findViewById(R.id.submitButton);
        final EditText cardNumberField = (EditText)view. findViewById(R.id.cardNumber);
        final EditText yearField = (EditText) view.findViewById(R.id.year);
        final EditText cvcField = (EditText)view. findViewById(R.id.cvc);
        final EditText cardholdername=(EditText)view.findViewById(R.id.cardholdername);
        final EditText zipcode=(EditText)view.findViewById(R.id.zipppcode);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter <String> dataAdapter = new ArrayAdapter <String>(getContext(), android.R.layout.simple_spinner_item, objects);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(dataAdapter);
        radioSexGroup = (RadioGroup)view.findViewById(R.id.radioSex);
         selectedId = radioSexGroup.getCheckedRadioButtonId();
        radioSexButton = (RadioButton)view. findViewById(selectedId);
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.radioMale:
                        paypal1.setVisibility(View.VISIBLE);
                        cardlayout.setVisibility(View.GONE);
                        paypal1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(item.equalsIgnoreCase("select a category"))
                                {
                                    Toast.makeText(getContext(), "Please choose category ", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "item is"+item, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getContext(), PayPalService.class);
                                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                                   getActivity().startService(intent);
                                    processpayment();

//                                    Intent a=new Intent(getContext(), PaypalIntegrateDemo.class);
//                                    a.putExtra("item",item);
//                                    startActivity(a);
                                } }});
                        break;
                    case R.id.radioFemale:

                        paypal1.setVisibility(View.GONE);

                        if(item.equalsIgnoreCase("select a category"))
                        {
                            Toast.makeText(getContext(), "Please choose category ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            cardlayout.setVisibility(View.VISIBLE);
                        }
                        break;
                } }
        });


        if(radioSexButton.getText().toString().equalsIgnoreCase("paypal"))
        {
            paypal1.setVisibility(View.VISIBLE);
            cardlayout.setVisibility(View.GONE);
            paypal1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.equalsIgnoreCase("select a category"))
                    {
                        Toast.makeText(getContext(), "Please choose category ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "item is "+item, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getContext(), PayPalService.class);
                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                        getActivity().startService(intent);
                        processpayment();
//                        Intent a=new Intent(getContext(), PaypalIntegrateDemo.class);
//                        a.putExtra("item",item);
//                        startActivity(a);
                    }
                }
            }); }
        else if(radioSexButton.getText().toString().equalsIgnoreCase("card"))
        {
            paypal1.setVisibility(View.GONE);
            if(item.equalsIgnoreCase("select a category"))
            {
                Toast.makeText(getContext(), "Please choose category ", Toast.LENGTH_SHORT).show();
            }
            else
            {
                cardlayout.setVisibility(View.VISIBLE);
            }
        }
        try {
            stripe = new Stripe("pk_test_hl3owcTBgOPOadPckz5fUDxO00PAZMfTS0");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        submitpayment.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pd = new ProgressDialog(getContext());
            pd.setCancelable(false);
            pd.setMessage("Loading");
            pd.setTitle("Payment processing ");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setProgress(0);
            pd.show();

            if(cardNumberField.getText().toString().length()==0)
            {
                Toast.makeText(getContext(), "Please input card number", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            else if(!validateCardExpiryDate(yearField.getText().toString())){
                Toast.makeText(getContext(), "Please enter  correct date", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
            else if(cardholdername.getText().toString().length()==0)
            {
                Toast.makeText(getContext(), "Please enter name", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
            else if(zipcode.getText().toString().length()==0)
            {
                Toast.makeText(getContext(), "Please enter zipcode", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
            else if(cvcField.getText().toString().length()==0)
            {
                Toast.makeText(getContext(), "Please enter  CCV number", Toast.LENGTH_SHORT).show();
                pd.dismiss();

            }
            else
            {
                String s = yearField.getText().toString();
                String[] data = s.split("/", 2);

                card = new Card(
                        cardNumberField.getText().toString(),
                        Integer.valueOf(data[0]),
                        Integer.valueOf(data[1]),
                        cvcField.getText().toString()
                );

                card.setCurrency("usd");
                card.setName(cardholdername.getText().toString());
                card.setAddressZip(zipcode.getText().toString());

                stripe.createToken(card, "pk_test_hl3owcTBgOPOadPckz5fUDxO00PAZMfTS0", new TokenCallback() {
                    public void onSuccess(Token token) {
                        Toast.makeText(getContext(), "Token created: " + token.getId(), Toast.LENGTH_LONG).show();
                        tok = token;
                        hii("Stable@gmail.com",token.getId());
                         //   pd.dismiss();

                    }

                    public void onError(Exception error) {
                        Log.e("Stripe", error.getLocalizedMessage());

                        Toast.makeText(getContext(), ""+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    }
                });
            } }
    });

    }
    public void hii(final String email, final String tokenid)
    {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("88", "onResponse: "+response );
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    FinalJSonObject = response ;
                    Log.e("88", "onResponse: "+response );
                    try {

                        JSONArray jsonArrays = new JSONArray(FinalJSonObject);
                        Log.e("8", "doInBackground: " );

                        for (int i = 0; i < jsonArrays.length(); i++) {
                            JSONObject json_data = jsonArrays.getJSONObject(i);
                            String res=json_data.getString("response");
                            String msg=json_data.getString("message");
                            Log.e("222", "onResponse: "+res );
                            pd.dismiss();
                            Toast.makeText(getContext(), "Successfully paid ", Toast.LENGTH_SHORT).show();


                        }
                    }
                    catch (JSONException e) {

                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", email);
                params.put("source",tokenid);


                return params;
            }
        };


        MySingleton.getInstance(getContext()).addToRequestque(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

      item = (String) parent.getItemAtPosition(position);
        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
        //String item = parent.getItemAtPosition(position).toString();
        Log.e("88", "onItemSelected: "+item );
        if(!item.equalsIgnoreCase("select a category"))
        {

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.e("88", "notttt: "+item );

    }


    boolean validateCardExpiryDate(String expiryDate) {
        return expiryDate.matches("(?:[0-1][1-2]|[0-9][0-2])/[0-9]{2}");
    }
    private void processpayment() {

        PayPalPayment payPalPayment=new PayPalPayment(new BigDecimal(String.valueOf(amt)),"USD",item, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent aa=new Intent(getContext(), PaymentActivity.class);
        aa.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        aa.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(aa,PAYPAL_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        startActivity(new Intent(getContext(), PaymenDetails.class)
                                .putExtra("Paymentdetails",paymentetails)
                                .putExtra("paymentamt",amt));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode== Activity.RESULT_CANCELED)
            {
                Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
            }
        }
        else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(getContext(), "cancel", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroy() {
        getContext().stopService(new Intent( getContext(),PayPalService.class));
        super.onDestroy();
    }

}