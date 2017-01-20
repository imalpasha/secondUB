package com.app.comic.ui.Activity.Profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.Home.HomeActivity;
import com.app.comic.ui.Activity.Login.LoginActivity;
import com.app.comic.ui.Model.Receive.DriverInfoReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Receive.PassengerInfoReceive;
import com.app.comic.ui.Model.Receive.UpdateDriverReceive;
import com.app.comic.ui.Model.Request.UpdateDriverRequest;
import com.app.comic.ui.Model.Request.UpdatePassengerRequest;
import com.app.comic.ui.Module.UpdateDriverModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.DropDownItem;
import com.app.comic.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class UpdateDriverFragment extends BaseFragment implements HomePresenter.UpdateDriverView, Validator.ValidationListener {

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.btnSignUp)
    Button btnSignUp;

    @NotEmpty(sequence = 1, messageResId = R.string.student_id_empty)
    @InjectView(R.id.txtStudentID)
    EditText txtStudentID;

    @NotEmpty(sequence = 2, messageResId = R.string.email_empty)
    @InjectView(R.id.txtUsername)
    EditText txtUsername;

    @NotEmpty(sequence = 3, messageResId = R.string.password_empty)
    @InjectView(R.id.txtPassword)
    EditText txtPassword;

    @NotEmpty(sequence = 4, messageResId = R.string.phone_empty)
    @InjectView(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;

    @InjectView(R.id.txtSmoker)
    TextView txtSmoker;

    @NotEmpty(sequence = 4, messageResId = R.string.phone_empty)
    @InjectView(R.id.txtPlatNumber)
    EditText txtPlatNumber;

    @NotEmpty(sequence = 4, messageResId = R.string.phone_empty)
    @InjectView(R.id.txtLicenseNumber)
    EditText txtLicenseNumber;

    @InjectView(R.id.txtTypeOfCar)
    TextView txtTypeOfCar;

    @InjectView(R.id.txtRegister)
    TextView txtRegister;

    @InjectView(R.id.radioSex)
    RadioGroup radioSex;

    @InjectView(R.id.prefRadioSex)
    RadioGroup prefRadioSex;

    RadioButton radioSexButton;
    RadioButton prefRadioSexButton;

    private Validator mValidator;
    private ArrayList<DropDownItem> purposeList = new ArrayList<DropDownItem>();
    private ArrayList<DropDownItem> carList = new ArrayList<DropDownItem>();

    View view;
    DriverInfoReceive driverInfoReceive;

    SharedPrefManager pref;
    Activity act;

    public static UpdateDriverFragment newInstance(Bundle bundle) {

        UpdateDriverFragment fragment = new UpdateDriverFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new UpdateDriverModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.share_ride_sign_as_driver, container, false);
        ButterKnife.inject(this, view);

        purposeList = getSmoker(act);
        carList = getCarType(act);

        Bundle bundle = getArguments();
        String driverInfo = bundle.getString("DRIVER_INFO");

        Gson book = new Gson();
        driverInfoReceive = book.fromJson(driverInfo, DriverInfoReceive.class);
        txtRegister.setText("Update Profile");

        txtPassword.setVisibility(View.GONE);
        txtUsername.setEnabled(false);
        setData(driverInfoReceive);

        txtSmoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e(purposeList.get(0).getCode().toString(),purposeList.get(1).getCode().toString());
                popupSelection(purposeList, getActivity(), txtSmoker, true, view);
            }
        });

        txtTypeOfCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e(purposeList.get(0).getCode().toString(),purposeList.get(1).getCode().toString());
                popupSelection(carList, getActivity(), txtTypeOfCar, true, view);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideKeyboard();
                mValidator.validate();

            }
        });

        return view;
    }

    public void setData(DriverInfoReceive obj) {

        txtUsername.setText(obj.getInfo().getUsername());
        txtPhoneNumber.setText(obj.getInfo().getPhone());
        txtSmoker.setText(obj.getInfo().getSmoker());
        txtSmoker.setTag(obj.getInfo().getSmoker());
        txtStudentID.setText(obj.getInfo().getStudent_id());
        txtPlatNumber.setText(obj.getInfo().getPlat_number());
        txtLicenseNumber.setText(obj.getInfo().getLicense_number());
        txtTypeOfCar.setText(obj.getInfo().getCar_type());

    }


    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onUpdateDriverReceive(UpdateDriverReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText("Successfully updated!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            getActivity().finish();
                            sDialog.dismiss();
                        }
                    })
                    .show();

        }
    }

    @Override
    public void onValidationSucceeded() {

        int selectedId = radioSex.getCheckedRadioButtonId();
        int prefSelectedId = prefRadioSex.getCheckedRadioButtonId();

        radioSexButton = (RadioButton) view.findViewById(selectedId);
        prefRadioSexButton = (RadioButton) view.findViewById(prefSelectedId);

        initiateLoading(getActivity());

        /* Validation Success - Start send data to server */
        UpdateDriverRequest updateDriverRequest = new UpdateDriverRequest();
        updateDriverRequest.setUsername(txtUsername.getText().toString());
        updateDriverRequest.setGender(radioSexButton.getText().toString());
        updateDriverRequest.setPhone(txtPhoneNumber.getText().toString());
        updateDriverRequest.setPrefGender(prefRadioSexButton.getText().toString());
        updateDriverRequest.setSmoker(txtSmoker.getText().toString());
        updateDriverRequest.setPlatNumber(txtPlatNumber.getText().toString());
        updateDriverRequest.setLicenseNumber(txtLicenseNumber.getText().toString());
        updateDriverRequest.setCarType(txtTypeOfCar.getText().toString());
        updateDriverRequest.setStudentID(txtStudentID.getText().toString());
        presenter.onUpdateDriverRequest(updateDriverRequest);

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        /* Validation Failed - Toast Error */

        //boolean firstView = true;
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
