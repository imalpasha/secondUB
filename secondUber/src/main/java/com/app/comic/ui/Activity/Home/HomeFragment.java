package com.app.comic.ui.Activity.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.DestinationBooking.DestinationBookingActivity;
import com.app.comic.ui.Activity.Login.LoginActivity;
import com.app.comic.ui.Activity.Profile.UpdateDriverActivity;
import com.app.comic.ui.Activity.Profile.UpdatePassengerActivity;
import com.app.comic.ui.Activity.SignUp.SignUpAsActivity;
import com.app.comic.ui.Model.Receive.DriverInfoReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Receive.PassengerInfoReceive;
import com.app.comic.ui.Model.Request.DriverInfoRequest;
import com.app.comic.ui.Model.Request.PassengerInfoRequest;
import com.app.comic.ui.Model.Request.UpdateDriverRequest;
import com.app.comic.ui.Module.HomeModule;
import com.app.comic.ui.Module.LoginModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HomeFragment extends BaseFragment implements HomePresenter.HomeView {

    @Inject
    HomePresenter presenter;

    SharedPrefManager pref;
    Activity act;

    @InjectView(R.id.btnEnterDestination)
    Button btnEnterDestination;

    @InjectView(R.id.btnUpdatePassenger)
    Button btnUpdatePassenger;

    @InjectView(R.id.btnUpdateDriver)
    Button btnUpdateDriver;

    @InjectView(R.id.btnLogout)
    Button btnLogout;

    @InjectView(R.id.btnRate)
    Button btnRate;

    String username;

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
        MainApplication.get(getActivity()).createScopedGraph(new HomeModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_ride_homepage, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        //check user type
        HashMap<String, String> init = pref.getUserType();
        String userType = init.get(SharedPrefManager.USER_TYPE);
        //String userType = "P";

        //check user type
        HashMap<String, String> init2 = pref.getUsername();
        username = init2.get(SharedPrefManager.USER_NAME);
        Log.e("Username", username);

        if (userType.equalsIgnoreCase("P")) {
            btnEnterDestination.setVisibility(View.VISIBLE);
            btnUpdatePassenger.setVisibility(View.VISIBLE);
            btnUpdateDriver.setVisibility(View.GONE);
            btnRate.setVisibility(View.VISIBLE);
        } else {
            btnEnterDestination.setVisibility(View.VISIBLE);
            btnUpdatePassenger.setVisibility(View.GONE);
            btnUpdateDriver.setVisibility(View.VISIBLE);
            btnRate.setVisibility(View.GONE);
        }

        btnEnterDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DestinationBookingActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnUpdatePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                PassengerInfoRequest passengerInfoRequest = new PassengerInfoRequest();
                passengerInfoRequest.setUsername(username);
                presenter.onPassengerInfoRequest(passengerInfoRequest);

            }
        });

        btnUpdateDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
                driverInfoRequest.setUsername(username);
                presenter.onDriverInfoRequest(driverInfoRequest);

            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();

                pref.setUserLogin("N");
            }
        });

        return view;
    }


    @Override
    public void onPassengerInfoReceive(PassengerInfoReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            String passengerInfo = new Gson().toJson(obj);
            Intent intent = new Intent(getActivity(), UpdatePassengerActivity.class);
            intent.putExtra("PASSENGER_INFO", passengerInfo);
            getActivity().startActivity(intent);

        }
    }

    @Override
    public void onDriverInfoReceive(DriverInfoReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            String driverInfo = new Gson().toJson(obj);
            Intent intent = new Intent(getActivity(), UpdateDriverActivity.class);
            intent.putExtra("DRIVER_INFO", driverInfo);
            getActivity().startActivity(intent);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
