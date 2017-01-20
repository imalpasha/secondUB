package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.DestinationBooking.ListOfRidesFragment;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ListOfRidesFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ListRidesModule {

    private final HomePresenter.ListRidesView listRidesView;

    public ListRidesModule(HomePresenter.ListRidesView loginView) {
        this.listRidesView = loginView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(listRidesView, bus);
    }
}
