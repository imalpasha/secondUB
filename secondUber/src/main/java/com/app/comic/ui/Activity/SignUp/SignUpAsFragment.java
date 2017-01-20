package com.app.comic.ui.Activity.SignUp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.app.comic.R;
import com.app.comic.base.BaseFragment;
import com.app.comic.utils.SharedPrefManager;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class SignUpAsFragment extends BaseFragment {


    @InjectView(R.id.btnRegisterAsPassenger)
    Button btnRegisterAsPassenger;

    @InjectView(R.id.btnRegisterAsDriver)
    Button btnRegisterAsDriver;

    // Validator Attributes
    private SharedPrefManager pref;
    Activity act;

    public static SignUpAsFragment newInstance() {

        SignUpAsFragment fragment = new SignUpAsFragment();
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

        View view = inflater.inflate(R.layout.share_ride_sign_as, container, false);
        ButterKnife.inject(this, view);

        btnRegisterAsPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignAsPassengerActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnRegisterAsDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignAsDriverActivity.class);
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
