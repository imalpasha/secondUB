package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Model.JSON.Driver;
import com.app.comic.ui.Model.JSON.Rate;
import com.app.comic.ui.Model.Receive.ListRidesReceive;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Request.ListRidesRequest;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Module.ListRidesModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.ExpandAbleGridView;
import com.app.comic.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ListOfRidesFragment extends BaseFragment implements HomePresenter.ListRidesView {

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.driverList)
    ExpandAbleGridView driverList;

    @InjectView(R.id.passengerList)
    ExpandAbleGridView passengerList;

    @InjectView(R.id.layout_driver)
    LinearLayout layout_driver;

    @InjectView(R.id.layout_passenger)
    LinearLayout layout_passenger;

    @InjectView(R.id.driver_match)
    ScrollView driver_match;

    @InjectView(R.id.passenger_match)
    ScrollView passenger_match;

    @InjectView(R.id.driver_no_match)
    TextView driver_no_match;

    @InjectView(R.id.passenger_no_match)
    TextView passenger_no_match;

    // Validator Attributes
    SharedPrefManager pref;
    Activity act;

    public static ListOfRidesFragment newInstance() {

        ListOfRidesFragment fragment = new ListOfRidesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new ListRidesModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_ride_list, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        //check username
        HashMap<String, String> init2 = pref.getUsername();
        String username = init2.get(SharedPrefManager.USER_NAME);

        //check user type
        HashMap<String, String> init = pref.getUserType();
        String userType = init.get(SharedPrefManager.USER_TYPE);

        if (userType.equalsIgnoreCase("P")) {
            //layout_driver.setVisibility(View.VISIBLE);
            layout_passenger.setVisibility(View.GONE);

        }else {
            layout_driver.setVisibility(View.GONE);
            //layout_driver.setVisibility(View.VISIBLE);
        }

        //LOAD LIST
        initiateLoading(getActivity());
        ListRidesRequest listRidesRequest = new ListRidesRequest();
        listRidesRequest.setUsername(username);
        presenter.onListRequest(listRidesRequest);
        return view;
    }

    @Override
    public void onListRidesReceive(ListRidesReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            //set list
            if (obj.getPassenger().size() == 0) {
                passenger_no_match.setVisibility(View.VISIBLE);
                passenger_match.setVisibility(View.GONE);
            } else {
                PassengerListAdapter adapter = new PassengerListAdapter(getActivity(), this, obj.getPassenger());
                passengerList.setAdapter(adapter);
                passenger_no_match.setVisibility(View.GONE);
                passenger_match.setVisibility(View.VISIBLE);
            }

            if (obj.getDriver().size() == 0) {
                driver_no_match.setVisibility(View.VISIBLE);
                driver_match.setVisibility(View.GONE);
            } else {
                DriverListAdapter adapter = new DriverListAdapter(getActivity(), this, obj.getDriver(), obj.getRate());
                driverList.setAdapter(adapter);
                driver_no_match.setVisibility(View.GONE);
                driver_match.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onSelectReceive(SelectReceive obj) {

        dismissLoading();
        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText(obj.getMessage())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            Intent intent = new Intent(getActivity(), ListOfRidesActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();

                        }
                    })
                    .show();

        }
    }

    public void callPassenger(String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
        startActivity(intent);
    }

    public void viewDriver(Driver obj) {

        String driverInfo = new Gson().toJson(obj);
        //String rateInfo = new Gson().toJson(rate);

        Intent intent = new Intent(getActivity(), DriverProfileActivity.class);
        intent.putExtra("DRIVER_INFO", driverInfo);
        //intent.putExtra("RATE_INFO", rateInfo);
        getActivity().startActivity(intent);

    }

    public void selectDriver(String id) {

        initiateLoading(getActivity());
        SelectRequest selectRequest = new SelectRequest();
        selectRequest.setDriverID(id);
        selectRequest.setUsername("username");
        presenter.onSelectRequest(selectRequest);
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
