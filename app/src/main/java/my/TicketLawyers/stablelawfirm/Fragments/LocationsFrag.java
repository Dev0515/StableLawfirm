package my.TicketLawyers.stablelawfirm.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.adapter.LocationAdapter;
import my.TicketLawyers.stablelawfirm.model.DataModel;
import my.TicketLawyers.stablelawfirm.model.MyData;

import java.util.ArrayList;


public class LocationsFrag extends Fragment {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;

    private static ArrayList<Integer> removedItems;
    public LocationsFrag() { }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_locations, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Locations");
        recyclerView = (RecyclerView)view. findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
                    ,MyData.Phoneno[i]
                    ,MyData.Fax[i]
            ));
        }
        removedItems = new ArrayList<Integer>();
        adapter = new LocationAdapter(data);
        recyclerView.setAdapter(adapter);
    }

}
