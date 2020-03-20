package my.TicketLawyers.stablelawfirm.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.adapter.AllCasesAdapter;
import my.TicketLawyers.stablelawfirm.model.DataModel1;
import my.TicketLawyers.stablelawfirm.model.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyCases extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    String FinalJSonObject;
    String strtext;
    ProgressDialog pd;

    String HTTP_URL1="http://www.stabilelawfirm.com/MobileApi/RetrieveRecord.php  ";
    ArrayList<DataModel1> modelRecyclerArrayList = new ArrayList<>();
    public MyCases() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_my_cases, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         strtext = getArguments().getString("edttext");
         getActivity().setTitle("My Cases");
        recyclerView = (RecyclerView)view. findViewById(R.id.allmusics);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        fetchJSON();
    }

    private void fetchJSON()
    {   pd = new ProgressDialog(getContext(), R.style.MyTheme);
        pd.setCancelable(false);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HTTP_URL1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    FinalJSonObject = response ;
                    try {

                        JSONArray jsonArrays = new JSONArray(FinalJSonObject);
                        for (int i = 0; i < jsonArrays.length(); i++) {
                            JSONObject json_data = jsonArrays.getJSONObject(i);
                            DataModel1 fish=new DataModel1();
                            fish.image=json_data.getString("Ticket");
                            if(fish.image.equalsIgnoreCase("0"))
                             {
                            Toast.makeText(getContext(), "No data Found", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            break;
                             }
                        else{

                            fish.Desc=json_data.getString("Description");
                            modelRecyclerArrayList.add(fish);
                            adapter = new AllCasesAdapter(modelRecyclerArrayList,getContext());
                            recyclerView.setAdapter(adapter);
                            pd.dismiss(); } }

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check Your Internet Connection...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
               // Log.e("2", "onErrorResponse: "+error );
                pd.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("emailId",   strtext);

                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestque(stringRequest);
    }

}
