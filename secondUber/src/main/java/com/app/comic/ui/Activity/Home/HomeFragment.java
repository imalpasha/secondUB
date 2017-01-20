package com.app.comic.ui.Activity.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.comic.R;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.DestinationBooking.DestinationBookingActivity;
import com.app.comic.ui.Activity.Profile.UpdatePassengerActivity;
import com.app.comic.ui.Activity.SignUp.SignUpAsActivity;
import com.app.comic.utils.SharedPrefManager;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends BaseFragment {

    // Validator Attributes
    private SharedPrefManager pref;
    Activity act;

    @InjectView(R.id.btnEnterDestination)
    Button btnEnterDestination;

    @InjectView(R.id.btnUpdate)
    Button btnUpdate;


    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();

        //MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        //RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_ride_homepage, container, false);
        ButterKnife.inject(this, view);

        btnEnterDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DestinationBookingActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdatePassengerActivity.class);
                getActivity().startActivity(intent);
            }
        });



        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        //presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        //presenter.onPause();
    }

}
