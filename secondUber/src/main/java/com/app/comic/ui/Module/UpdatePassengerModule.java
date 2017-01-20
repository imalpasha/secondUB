package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Activity.Profile.UpdatePassengerFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = UpdatePassengerFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class UpdatePassengerModule {

    private final HomePresenter.UpdatePassengerView updatePassengerView;

    public UpdatePassengerModule(HomePresenter.UpdatePassengerView loginView) {
        this.updatePassengerView = loginView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(updatePassengerView, bus);
    }
}
