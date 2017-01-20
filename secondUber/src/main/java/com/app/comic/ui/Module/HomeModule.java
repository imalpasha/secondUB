package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Home.HomeFragment;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = HomeFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class HomeModule {

    private final HomePresenter.HomeView homeView;

    public HomeModule(HomePresenter.HomeView loginView) {
        this.homeView = loginView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(homeView, bus);
    }
}
