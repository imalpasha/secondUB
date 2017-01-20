package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.comic.utils.SharedPrefManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class DestinationBookingFragment extends BaseFragment implements HomePresenter.DestinationView, Validator.ValidationListener {

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

        View view = inflater.inflate(R.layout.share_ride_destination, container, false);
        ButterKnife.inject(this, view);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        initiateLoading(getActivity());
        DestinationRequest destinationRequest = new DestinationRequest();
        destinationRequest.setRideAddress(txtRideAddress.getText().toString());
        destinationRequest.setRideState(txtRideState.getTag().toString());
        destinationRequest.setRideDate(txtRideDate.getText().toString());
        destinationRequest.setRideTime(txtRideTime.getText().toString());

        destinationRequest.setRideDestinationAddress(txtRideDestination.getText().toString());
        destinationRequest.setRideDestinationState(txtRideStateDestination.getTag().toString());
        destinationRequest.setUsername("username");

        presenter.onDestinationRequest(destinationRequest);
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

}
