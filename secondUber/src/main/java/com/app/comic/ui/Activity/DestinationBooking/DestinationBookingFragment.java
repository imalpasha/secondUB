package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.Home.HomeActivity;
import com.app.comic.ui.Model.Receive.DestinationReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Request.DestinationRequest;
import com.app.comic.ui.Model.Request.LoginRequest;
import com.app.comic.ui.Module.DestinationModule;
import com.app.comic.ui.Module.LoginModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.DropDownItem;
import com.app.comic.utils.SharedPrefManager;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class DestinationBookingFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, HomePresenter.DestinationView, Validator.ValidationListener {

    @Inject
    HomePresenter presenter;

    @NotEmpty(sequence = 1, messageResId = R.string.student_id_empty)
    @InjectView(R.id.txtRideAddress)
    EditText txtRideAddress;

    @InjectView(R.id.txtRideDate)
    TextView txtRideDate;

    @InjectView(R.id.txtRideState)
    TextView txtRideState;

    @InjectView(R.id.txtRideTime)
    TextView txtRideTime;

    @NotEmpty(sequence = 1, messageResId = R.string.student_id_empty)
    @InjectView(R.id.txtRideDestination)
    EditText txtRideDestination;

    @InjectView(R.id.txtRideStateDestination)
    TextView txtRideStateDestination;

    @InjectView(R.id.btnContinue)
    Button btnContinue;

    // Validator Attributes
    SharedPrefManager pref;
    Activity act;
    Validator mValidator;

    final Calendar calendar = Calendar.getInstance();
    String fullDate;
    static final String DATEPICKER_TAG = "datepicker";
    ArrayList<DropDownItem> originList = new ArrayList<DropDownItem>();
    ArrayList<DropDownItem> destinationList = new ArrayList<DropDownItem>();
    View view;

    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    public static DestinationBookingFragment newInstance() {

        DestinationBookingFragment fragment = new DestinationBookingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new DestinationModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.share_ride_destination, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        final int year = calendar.get(Calendar.YEAR);

        originList = getState(act);
        destinationList = getState(act);

        /*txtRideState.setText(originList.get(0).getText());
        txtRideState.setTag(originList.get(0).getCode());*/

        /*txtRideStateDestination.setText(destinationList.get(0).getText());
        txtRideStateDestination.setTag(destinationList.get(0).getCode());*/

        txtRideState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e(purposeList.get(0).getCode().toString(),purposeList.get(1).getCode().toString());
                txtRideState.setHint("State");
                popupSelection(originList, getActivity(), txtRideState, true, view);
            }
        });

        txtRideStateDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e(purposeList.get(0).getCode().toString(),purposeList.get(1).getCode().toString());
                txtRideStateDestination.setHint("State");
                popupSelection(destinationList, getActivity(), txtRideStateDestination, true, view);
            }
        });


        txtRideDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(year - 80, year);
                if (checkFragmentAdded()) {
                    datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard();
                mValidator.validate();
                /*Intent intent = new Intent(getActivity(), HomeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();*/
            }
        });
        return view;
    }

    @Override
    public void onDestinationReceive(DestinationReceive obj) {

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

    @Override
    public void onValidationSucceeded() {

        //check user type
        HashMap<String, String> init2 = pref.getUsername();
        String username = init2.get(SharedPrefManager.USER_NAME);

        //check user type
        HashMap<String, String> init3 = pref.getPhone();
        String phone = init3.get(SharedPrefManager.USER_PHONE);

        Log.e("Phone",phone);

        initiateLoading(getActivity());
        DestinationRequest destinationRequest = new DestinationRequest();
        destinationRequest.setRideAddress(txtRideAddress.getText().toString());
        destinationRequest.setRideState(txtRideState.getText().toString());
        destinationRequest.setRideDate(fullDate);
        destinationRequest.setRideTime(txtRideTime.getText().toString());
        destinationRequest.setRideDestinationAddress(txtRideDestination.getText().toString());
        destinationRequest.setRideDestinationState(txtRideStateDestination.getText().toString());
        destinationRequest.setUsername(username);
        destinationRequest.setPhone(phone);
        presenter.onDestinationRequest(destinationRequest);
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        /* Validation Failed - Toast Error */

        for (ValidationError error : errors) {
            View view = error.getView();
            setShake(view);
            view.setFocusable(true);
            view.requestFocus();

            /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            }
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

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        String monthInAlphabet = getMonthAlphabet(month);
        txtRideDate.setText(day + " " + monthInAlphabet + " " + year);

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if (month < 10) {
            varMonth = "0";
        }
        if (day < 10) {
            varDay = "0";
        }

        fullDate = year + "-" + varMonth + "" + (month + 1) + "-" + varDay + "" + day;
        /*int currentYear = calendar.get(Calendar.YEAR);
        age = currentYear - year;
        limitAge = age >= 18;*/
    }
}
