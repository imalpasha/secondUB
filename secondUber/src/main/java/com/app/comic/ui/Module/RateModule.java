package com.app.comic.ui.Module;

import com.app.comic.AppModule;
import com.app.comic.ui.Activity.Rate.RateFragment;
import com.app.comic.ui.Presenter.RatePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = RateFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class RateModule {
    private final RatePresenter.RateView rateView;

    public RateModule(RatePresenter.RateView rateView) {
        this.rateView = rateView;
    }

    @Provides
    @Singleton
    RatePresenter provideRatePresenter(Bus bus) {
        return new RatePresenter(rateView, bus);
    }
}
