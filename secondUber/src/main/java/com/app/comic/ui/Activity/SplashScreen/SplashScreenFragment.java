package com.app.comic.ui.Activity.SplashScreen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.comic.ui.Activity.Home.HomeActivity;
import com.app.comic.ui.Activity.Login.LoginActivity;
import com.app.comic.R;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Activity.FragmentContainerActivity;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.utils.SharedPrefManager;


import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashScreenFragment extends BaseFragment implements HomePresenter.SplashScreen {

    @Inject
    HomePresenter presenter;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private ProgressDialog progress;
    Activity act;

    public static SplashScreenFragment newInstance() {

        SplashScreenFragment fragment = new SplashScreenFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);

        Boolean login = false;
        if (login) {
            Intent intent = new Intent(act, HomeActivity.class);
            act.startActivity(intent);
            act.finish();

        } else {
            Intent intent = new Intent(act, LoginActivity.class);
            act.startActivity(intent);
            act.finish();
        }

        return view;
    }


    @Override
    public void onConnectionFailed() {
        // connectionRetry("Unable to connect to server");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
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
