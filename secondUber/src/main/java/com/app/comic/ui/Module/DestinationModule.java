package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.DestinationBooking.DestinationBookingFragment;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = DestinationBookingFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class DestinationModule {

    private final HomePresenter.DestinationView loginView;

    public DestinationModule(HomePresenter.DestinationView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(loginView, bus);
    }
}
