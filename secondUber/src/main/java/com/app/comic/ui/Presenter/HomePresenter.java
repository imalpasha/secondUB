package com.app.comic.ui.Presenter;

import com.app.comic.ui.Model.Receive.DestinationReceive;
import com.app.comic.ui.Model.Receive.DriverInfoReceive;
import com.app.comic.ui.Model.Receive.ListRidesReceive;
import com.app.comic.ui.Model.Receive.PassengerInfoReceive;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Receive.SignDriverReceive;
import com.app.comic.ui.Model.Receive.SignPassengerReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Receive.UpdateDriverReceive;
import com.app.comic.ui.Model.Receive.UpdatePassengerReceive;
import com.app.comic.ui.Model.Request.DestinationRequest;
import com.app.comic.ui.Model.Request.DriverInfoRequest;
import com.app.comic.ui.Model.Request.ListRidesRequest;
import com.app.comic.ui.Model.Request.LoginRequest;
import com.app.comic.ui.Model.Request.PassengerInfoRequest;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Model.Request.SignDriverRequest;
import com.app.comic.ui.Model.Request.SignPassengerRequest;
import com.app.comic.ui.Model.Request.UpdateDriverRequest;
import com.app.comic.ui.Model.Request.UpdatePassengerRequest;
import com.app.comic.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HomePresenter {

    private SharedPrefManager pref;

    public interface LoginView {
        void onLoginReceive(LoginReceive event);
    }

    public interface SignPassengerView {
        void onSignPassengerReceive(SignPassengerReceive event);
    }

    public interface UpdatePassengerView {
        void onUpdatePassengerReceive(UpdatePassengerReceive event);
    }

    public interface DestinationView {
        void onDestinationReceive(DestinationReceive event);
    }

    public interface HomeView {
        void onPassengerInfoReceive(PassengerInfoReceive event);

        void onDriverInfoReceive(DriverInfoReceive event);

    }

    public interface UpdateDriverView {
        void onUpdateDriverReceive(UpdateDriverReceive event);
    }

    public interface SignDriverView {
        void onSignDriverReceive(SignDriverReceive event);
    }

    public interface ListRidesView {
        void onListRidesReceive(ListRidesReceive event);

        void onSelectReceive(SelectReceive event);
    }

    public interface SelectionView {
        void onSelectView(SelectReceive event);
    }

    public interface SplashScreen {
        void onConnectionFailed();
    }


    private UpdatePassengerView updatePassengerView;
    private HomeView homeView;
    private SplashScreen view2;
    private LoginView loginView;
    private DestinationView destinationView;
    private SignPassengerView signPassengerView;
    private SignDriverView signDriverView;
    private SelectionView selectionView;
    private ListRidesView listRidesView;
    private UpdateDriverView updateDriverView;
    private final Bus bus;

    public HomePresenter(UpdatePassengerView view, Bus bus) {
        this.updatePassengerView = view;
        this.bus = bus;
    }

    public HomePresenter(LoginView view, Bus bus) {
        this.loginView = view;
        this.bus = bus;
    }

    public HomePresenter(ListRidesView view, Bus bus) {
        this.listRidesView = view;
        this.bus = bus;
    }

    public HomePresenter(UpdateDriverView view, Bus bus) {
        this.updateDriverView = view;
        this.bus = bus;
    }

    public HomePresenter(HomeView view, Bus bus) {
        this.homeView = view;
        this.bus = bus;
    }

    public HomePresenter(SelectionView view, Bus bus) {
        this.selectionView = view;
        this.bus = bus;
    }

    public HomePresenter(SignPassengerView view, Bus bus) {
        this.signPassengerView = view;
        this.bus = bus;
    }

    public HomePresenter(SignDriverView view, Bus bus) {
        this.signDriverView = view;
        this.bus = bus;
    }

    public HomePresenter(DestinationView view, Bus bus) {
        this.destinationView = view;
        this.bus = bus;
    }


    public void onUpdatePassengerRequest(UpdatePassengerRequest data) {
        bus.post(new UpdatePassengerRequest(data));
    }


    public void onPassengerInfoRequest(PassengerInfoRequest data) {
        bus.post(new PassengerInfoRequest(data));
    }

    public void onDriverInfoRequest(DriverInfoRequest data) {
        bus.post(new DriverInfoRequest(data));
    }

    public void onListRequest(ListRidesRequest data) {
        bus.post(new ListRidesRequest(data));
    }

    public void onUpdateDriverRequest(UpdateDriverRequest data) {
        bus.post(new UpdateDriverRequest(data));
    }

    public void onDestinationRequest(DestinationRequest data) {
        bus.post(new DestinationRequest(data));
    }

    public void onSelectRequest(SelectRequest data) {
        bus.post(new SelectRequest(data));
    }

    public void onRegisterRequest(SignPassengerRequest data) {
        bus.post(new SignPassengerRequest(data));
    }

    public void onLoginRequest(LoginRequest data) {
        bus.post(new LoginRequest(data));
    }

    public void onSignDriverRequest(SignDriverRequest data) {
        bus.post(new SignDriverRequest(data));
    }


    @Subscribe
    public void onUpdatePassengerReceive(UpdatePassengerReceive event) {
        updatePassengerView.onUpdatePassengerReceive(event);
    }

    @Subscribe
    public void onPassengerInfoReceive(PassengerInfoReceive event) {
        homeView.onPassengerInfoReceive(event);
    }

    @Subscribe
    public void onDriverInfoReceive(DriverInfoReceive event) {
        homeView.onDriverInfoReceive(event);
    }

    @Subscribe
    public void onUpdateDriverReceive(UpdateDriverReceive event) {
        updateDriverView.onUpdateDriverReceive(event);
    }


    @Subscribe
    public void onLoginReceive(LoginReceive event) {
        loginView.onLoginReceive(event);
    }

    @Subscribe
    public void onSelectReceive(SelectReceive event) {

        if (selectionView != null) {
            selectionView.onSelectView(event);
        }

        if (listRidesView != null) {
            listRidesView.onSelectReceive(event);
        }

    }

    @Subscribe
    public void onListRidesReceive(ListRidesReceive event) {
        listRidesView.onListRidesReceive(event);
    }

    @Subscribe
    public void onDestinationReceive(DestinationReceive event) {
        destinationView.onDestinationReceive(event);
    }


    @Subscribe
    public void onSignDriverReceive(SignDriverReceive event) {
        signDriverView.onSignDriverReceive(event);
    }

    @Subscribe
    public void onSignPassengerReceive(SignPassengerReceive event) {
        signPassengerView.onSignPassengerReceive(event);
    }


    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }


}
