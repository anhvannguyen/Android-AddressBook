package me.anhvannguyen.android.addressbook;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private RecyclerView mAddressRecyclerView;
    private AddressRecyclerAdapter mRecycleAdapter;
    private FloatingActionButton mAddButton;
    private CoordinatorLayout mCoordinatorLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCoordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinator_layout);

        mRecycleAdapter = new AddressRecyclerAdapter(getActivity());

        mAddButton = (FloatingActionButton) rootView.findViewById(R.id.add_fab);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mCoordinatorLayout, "Add Works!", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAddressRecyclerView = (RecyclerView) rootView.findViewById(R.id.main_address_recyclerview);
        // improve performance if the content of the layout
        // does not change the size of the RecyclerView
        mAddressRecyclerView.setHasFixedSize(true);

        mAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAddressRecyclerView.setAdapter(mRecycleAdapter);
        
        return rootView;
    }
}
