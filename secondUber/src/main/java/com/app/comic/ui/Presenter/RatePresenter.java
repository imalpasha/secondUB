package com.app.comic.ui.Presenter;

import com.app.comic.ui.Model.Receive.RateReceive;
import com.app.comic.ui.Model.Request.RateRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class RatePresenter {

    public interface RateView {
        void onRateReceive(RateReceive event);
    }

    private RateView rateView;
    private final Bus bus;

    public RatePresenter(RateView view, Bus bus) {
        this.rateView = view;
        this.bus = bus;
    }


    public void onRateRequest(RateRequest data) {
        bus.post(new RateRequest(data));
    }


    @Subscribe
    public void onRateReceive(RateReceive event) {
        rateView.onRateReceive(event);
    }


    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }


}
