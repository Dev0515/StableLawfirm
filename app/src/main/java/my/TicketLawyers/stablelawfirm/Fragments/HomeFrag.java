package my.TicketLawyers.stablelawfirm.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.TicketLawyers.stablelawfirm.R;


public class HomeFrag extends Fragment {
    Button location,contactus,callus, mycases;
    private static FragmentManager fragmentManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getFragmentManager();//Get Fragment Manager
        getActivity().setTitle("Dashboard");
                location=(Button)view.findViewById(R.id.location);
                contactus=(Button)view.findViewById(R.id.contactus);
                callus=(Button)view.findViewById(R.id.instaa);
                 mycases=(Button)view.findViewById(R.id.mycases) ;
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationsFrag fragment2 = new LocationsFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.commit();
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactusFrag fragment2 = new ContactusFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.commit();

            }
        });
        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallusFrag fragment2 = new CallusFrag();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment2);
                fragmentTransaction.commit();
            }
        });
        mycases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.customdialog);
                dialog.setTitle("Title...");
                final EditText editText=(EditText)dialog.findViewById(R.id.edittext);


                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String aa=editText.getText().toString();

                        if(aa.isEmpty())
                        {
                            Toast.makeText(getContext(), "Enter mail id", Toast.LENGTH_SHORT).show();

                        }else{
                            done(aa);
                            dialog.dismiss();

                        } }
                });

                dialog.show();
//                MyCases fragment2 = new MyCases();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.content_frame, fragment2);
//                fragmentTransaction.commit();


            }
        });
    }

        private void done(String aa) {

        Fragment argumentFragment = new MyCases();//Get Fragment Instance
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("edttext", aa);//put string, int, etc in bundle with a key value
        argumentFragment.setArguments(data);//Finally set argument bundle to fragment

        fragmentManager.beginTransaction().replace(R.id.content_frame, argumentFragment).commit();//now replace the argument fragment


    }

}



