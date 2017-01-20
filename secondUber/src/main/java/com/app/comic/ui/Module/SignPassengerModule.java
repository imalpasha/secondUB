package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Activity.SignUp.SignAsPassengerFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SignAsPassengerFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SignPassengerModule {

    private final HomePresenter.SignPassengerView signPassengerView;

    public SignPassengerModule(HomePresenter.SignPassengerView signPassengerView) {
        this.signPassengerView = signPassengerView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(signPassengerView, bus);
    }
}
