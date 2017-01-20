package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.DestinationBooking.DriverProfileFragment;
import com.app.comic.ui.Activity.Login.LoginFragment;
import com.app.comic.ui.Presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = DriverProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class DriverProfileModule {

    private final HomePresenter.SelectionView selectionView;

    public DriverProfileModule(HomePresenter.SelectionView selectionView) {
        this.selectionView = selectionView;
    }

    @Provides
    @Singleton
    HomePresenter provideLoginPresenter(Bus bus) {
        return new HomePresenter(selectionView, bus);
    }
}
