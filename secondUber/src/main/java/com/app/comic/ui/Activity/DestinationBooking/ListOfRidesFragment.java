package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Model.JSON.Driver;
import com.app.comic.ui.Model.Receive.DestinationReceive;
import com.app.comic.ui.Model.Receive.ListRidesReceive;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Request.ListRidesRequest;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Module.ListRidesModule;
import com.app.comic.ui.Module.LoginModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.ExpandAbleGridView;
import com.app.comic.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

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

        //check user type
        HashMap<String, String> init2 = pref.getUsername();
        String username = init2.get(SharedPrefManager.USER_NAME);

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
            if(obj.getPassenger().size() == 0){
                //display na
            }else{
                PassengerListAdapter adapter = new PassengerListAdapter(getActivity(),this, obj.getPassenger());
                passengerList.setAdapter(adapter);
            }

            if(obj.getDriver().size() == 0){
                //display na
            }else{
                DriverListAdapter adapter = new DriverListAdapter(getActivity(),this, obj.getDriver());
                driverList.setAdapter(adapter);
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

    public void viewDriver(Driver obj){

        String driverInfo = new Gson().toJson(obj);

        Intent intent = new Intent(getActivity(), DriverProfileActivity.class);
        intent.putExtra("DRIVER_INFO", driverInfo);
        getActivity().startActivity(intent);
        getActivity().finish();

    }

    public void selectDriver(String id){

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
