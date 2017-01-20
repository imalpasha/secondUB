package com.app.comic.ui.Activity.Login;

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


public class SignUpFragment extends BaseFragment {

    // Validator Attributes
    private SharedPrefManager pref;
    Activity act;

    public static SignUpFragment newInstance() {

        SignUpFragment fragment = new SignUpFragment();
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

        View view = inflater.inflate(R.layout.secondhand_signuppage, container, false);
        ButterKnife.inject(this, view);

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
