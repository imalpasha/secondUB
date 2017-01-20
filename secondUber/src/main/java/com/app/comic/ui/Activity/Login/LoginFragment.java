package com.app.comic.ui.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.Home.HomeActivity;
import com.app.comic.ui.Activity.SignUp.SignUpAsActivity;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Request.LoginRequest;
import com.app.comic.ui.Module.LoginModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.SharedPrefManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginFragment extends BaseFragment implements HomePresenter.LoginView, Validator.ValidationListener {

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.btnSignUp)
    Button btnSignUp;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @NotEmpty(sequence = 1, messageResId = R.string.email_empty)
    @Email
    @InjectView(R.id.txtEmail)
    EditText txtEmail;

    @NotEmpty(sequence = 2, messageResId = R.string.password_empty)
    @InjectView(R.id.txtPassword)
    EditText txtPassword;

    SharedPrefManager pref;
    Validator mValidator;

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_ride_login, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUpAsActivity.class);
                getActivity().startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
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
    public void onLoginReceive(LoginReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            pref.setUserType(obj.getType());

            Intent intent = new Intent(getActivity(), HomeActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();

        }
    }

    @Override
    public void onValidationSucceeded() {

        initiateLoading(getActivity());
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(txtEmail.getText().toString());
        loginRequest.setPassword(txtPassword.getText().toString());

        presenter.onLoginRequest(loginRequest);
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
