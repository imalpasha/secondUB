package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.comic.MainController;
import com.app.comic.R;
import com.app.comic.api.ApiEndpoint;
import com.app.comic.application.MainApplication;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Model.JSON.Driver;
import com.app.comic.ui.Model.JSON.Rate;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Module.DriverProfileModule;
import com.app.comic.ui.Presenter.HomePresenter;
import com.app.comic.ui.Realm.RealmObjectController;
import com.app.comic.utils.SharedPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class DriverProfileFragment extends BaseFragment implements HomePresenter.SelectionView {

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.btnSignUp)
    Button btnSignUp;

    @NotEmpty(sequence = 4, messageResId = R.string.phone_empty)
    @InjectView(R.id.txtPlateNumber)
    TextView txtPlateNumber;

    @InjectView(R.id.txtTypeOfCar)
    TextView txtTypeOfCar;

    @InjectView(R.id.txtUsername)
    TextView txtUsername;

    @InjectView(R.id.txtPhoneNumber)
    TextView txtPhoneNumber;

    @InjectView(R.id.driver_addImage)
    ImageView driver_addImage;

    @InjectView(R.id.call)
    Button call;

    @InjectView(R.id.star1)
    ImageView star1;

    @InjectView(R.id.star2)
    ImageView star2;

    @InjectView(R.id.star3)
    ImageView star3;

    @InjectView(R.id.star4)
    ImageView star4;

    @InjectView(R.id.star5)
    ImageView star5;

    // Validator Attributes
    SharedPrefManager pref;
    Activity act;
    Driver driver;
    Rate rate;

    public static DriverProfileFragment newInstance(Bundle bundle) {

        DriverProfileFragment fragment = new DriverProfileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act = getActivity();
        MainApplication.get(getActivity()).createScopedGraph(new DriverProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_ride_view_driver, container, false);
        ButterKnife.inject(this, view);

        Bundle bundle = getArguments();
        String driverInfo = bundle.getString("DRIVER_INFO");

        Bundle bundle2 = getArguments();
        String rateInfo = bundle2.getString("RATE_INFO");

        Gson book = new Gson();
        driver = book.fromJson(driverInfo, Driver.class);

        Gson book2 = new Gson();
        rate = book2.fromJson(rateInfo, Rate.class);

        setData(driver, rate);

        btnSignUp.setText("Select");
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initiateLoading(getActivity());
                SelectRequest selectRequest = new SelectRequest();
                selectRequest.setUsername("username");
                selectRequest.setDriverID(driver.getId());

                presenter.onSelectRequest(selectRequest);
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPassenger(driver.getPhone());
            }
        });
        return view;
    }

    public void setData(Driver obj, Rate obj2) {

        if (driver.getDriver_image() != null) {
            Glide.with(getActivity()).load(ApiEndpoint.imagePath() + "" + driver.getDriver_image())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    //.placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.promo_home))
                    .into(driver_addImage);
        } else {
            driver_addImage.setImageResource(R.drawable.no_profile);
        }

        txtUsername.setText(obj.getUsername());
        txtPhoneNumber.setText(obj.getPhone());
        txtPlateNumber.setText(obj.getPlat_number());
        txtTypeOfCar.setText(obj.getCar_type());

        String rate = obj2.getRate();

        if (rate.equals("1")){
            star1.setImageResource(R.drawable.star_g);
        }else if (rate.equals("2")){
            star2.setImageResource(R.drawable.star_g);
        }else if (rate.equals("3")){
            star3.setImageResource(R.drawable.star_g);
        }else if (rate.equals("4")){
            star4.setImageResource(R.drawable.star_g);
        }else if (rate.equals("5")){
            star5.setImageResource(R.drawable.star_g);
        }else{
            star1.setImageResource(R.drawable.star_n);
            star2.setImageResource(R.drawable.star_n);
            star3.setImageResource(R.drawable.star_n);
            star4.setImageResource(R.drawable.star_n);
            star5.setImageResource(R.drawable.star_n);
        }

    }

    public void callPassenger(String phoneNo) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNo));
        startActivity(intent);
    }

    @Override
    public void onSelectView(SelectReceive obj) {

        dismissLoading();

        Boolean status = MainController.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText(obj.getMessage())
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();

                            Intent intent = new Intent(getActivity(), ListOfRidesActivity.class);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

}
