package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Activity.Profile.UpdateDriverFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = UpdateDriverFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class UpdateDriverModule {

    private final HomePresenter.UpdateDriverView loginView;

    public UpdateDriverModule(HomePresenter.UpdateDriverView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(loginView, bus);
    }
}
