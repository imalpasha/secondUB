package com.app.comic.ui.Activity.Rate;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.comic.R;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Model.Receive.RateReceive;
import com.app.comic.ui.Module.RateModule;
import com.app.comic.ui.Presenter.RatePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.SharedPrefManager;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class RateFragment extends BaseFragment implements RatePresenter.RateView {

    @Inject
    RatePresenter presenter;

    SharedPrefManager pref;
    Activity act;

    public static RateFragment newInstance() {

        RateFragment fragment = new RateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new RateModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rate, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        return view;
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
    public void onRateReceive(RateReceive event) {

    }
}
