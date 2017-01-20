package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Activity.SignUp.SignAsDriverFragment;
import com.app.comic.ui.Activity.SignUp.SignAsPassengerFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = SignAsDriverFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class SignDriverModule {

    private final HomePresenter.SignDriverView signDriverView;

    public SignDriverModule(HomePresenter.SignDriverView signDriverView) {
        this.signDriverView = signDriverView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(signDriverView, bus);
    }
}
