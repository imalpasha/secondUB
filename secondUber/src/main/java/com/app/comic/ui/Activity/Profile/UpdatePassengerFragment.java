package com.app.comic.ui.Activity.Profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.app.comic.R;
import com.app.comic.base.BaseFragment;
import com.app.comic.utils.SharedPrefManager;
import butterknife.ButterKnife;


public class UpdatePassengerFragment extends BaseFragment {

    // Validator Attributes
    private SharedPrefManager pref;
    Activity act;

    public static UpdatePassengerFragment newInstance() {

        UpdatePassengerFragment fragment = new UpdatePassengerFragment();
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

        View view = inflater.inflate(R.layout.share_ride_sign_as_passenger, container, false);
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
