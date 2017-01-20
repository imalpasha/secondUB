package com.app.comic.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.app.comic.MainFragmentActivity;
import com.app.comic.base.BaseFragment;
import com.app.comic.ui.Model.Receive.DestinationReceive;
import com.app.comic.ui.Model.Receive.ListRidesReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Receive.SignDriverReceive;
import com.app.comic.ui.Model.Receive.SignPassengerReceive;
import com.app.comic.ui.Model.Request.DestinationRequest;
import com.app.comic.ui.Model.Request.ListRidesRequest;
import com.app.comic.ui.Model.Request.LoginRequest;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Model.Request.SignDriverRequest;
import com.app.comic.ui.Model.Request.SignPassengerRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHandler {

    private final Bus bus;
    ApiService apiService2;
    Context context;
    ProgressDialog mProgressDialog;
    private int inc;
    private boolean retry;


    public ApiRequestHandler(Bus bus, ApiService apiService) {
        this.bus = bus;
        this.apiService2 = apiService;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiEndpoint.getUrl())
                .client(defaultHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService2 = retrofit.create(ApiService.class);
    }

    /*OkHttpClient okClient = new OkHttpClient();
    okClient.interceptors().add(new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            // if we needed to refresh oAuth tokens, this is where it would happen

            Request original = chain.request();
            // Customize the request
            Request request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "auth-token")
                    .method(original.method(), original.body())
                    .build();

            Response response = chain.proceed(request); // currently does nothing
            return response;
        }
    });*/


    // ------------------------------------------------------------------------------ //


        /*OkHttpClient okClient = new OkHttpClient();
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                // if we needed to refresh oAuth tokens, this is where it would happen
                Response response = chain.proceed(chain.request()); // currently does nothing
                return response;
            }
        });*/

    @Subscribe
    public void onListRidesRequest(final ListRidesRequest event) {
        Log.e("Request Handle", "Handler");

        Call<ListRidesReceive> call = apiService2.listRides(event);
        call.enqueue(new Callback<ListRidesReceive>() {
            @Override
            public void onResponse(Call<ListRidesReceive> call, Response<ListRidesReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    ListRidesReceive user = response.body();
                    bus.post(new ListRidesReceive(user));
                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

                }
            }

            @Override
            public void onFailure(Call<ListRidesReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }

    @Subscribe
    public void onListRidesRequest(final SelectRequest event) {
        Log.e("Request Handle", "Handler");

        Call<SelectReceive> call = apiService2.selectRequest(event);
        call.enqueue(new Callback<SelectReceive>() {
            @Override
            public void onResponse(Call<SelectReceive> call, Response<SelectReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    SelectReceive user = response.body();
                    bus.post(new SelectReceive(user));

                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");
                }
            }

            @Override
            public void onFailure(Call<SelectReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }

    @Subscribe
    public void onLoginRequest(final LoginRequest event) {
        Log.e("Request Handle", "Handler");

        Call<LoginReceive> call = apiService2.login(event);
        call.enqueue(new Callback<LoginReceive>() {
            @Override
            public void onResponse(Call<LoginReceive> call, Response<LoginReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    LoginReceive user = response.body();
                    bus.post(new LoginReceive(user));
                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");
                }
            }

            @Override
            public void onFailure(Call<LoginReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }

    @Subscribe
    public void onDestinationRequest(final DestinationRequest event) {
        Log.e("Request Handle", "Handler");

        Call<DestinationReceive> call = apiService2.destinationRequest(event);
        call.enqueue(new Callback<DestinationReceive>() {
            @Override
            public void onResponse(Call<DestinationReceive> call, Response<DestinationReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    DestinationReceive user = response.body();
                    bus.post(new DestinationReceive(user));
                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");
                }
            }

            @Override
            public void onFailure(Call<DestinationReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }


    @Subscribe
    public void onSignPassengerRequest(final SignPassengerRequest event) {
        Log.e("Request Handle", "Handler");

        Call<SignPassengerReceive> call = apiService2.signPassenger(event);
        call.enqueue(new Callback<SignPassengerReceive>() {
            @Override
            public void onResponse(Call<SignPassengerReceive> call, Response<SignPassengerReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    SignPassengerReceive user = response.body();
                    bus.post(new SignPassengerReceive(user));
                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");
                }
            }

            @Override
            public void onFailure(Call<SignPassengerReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }

    @Subscribe
    public void onSignDriverRequest(final SignDriverRequest event) {
        Log.e("Request Handle", "Handler");

        Call<SignDriverReceive> call = apiService2.signDriver(event);
        call.enqueue(new Callback<SignDriverReceive>() {
            @Override
            public void onResponse(Call<SignDriverReceive> call, Response<SignDriverReceive> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    SignDriverReceive user = response.body();
                    bus.post(new SignDriverReceive(user));
                    Log.e("Failed", Integer.toString(statusCode));
                } else {
                    int statusCode = response.code();
                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");
                }
            }

            @Override
            public void onFailure(Call<SignDriverReceive> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext(), "Unable to connect to server");

            }
        });
    }

   /* @Subscribe
    public void onAuthRequest(final AuthRequest event) {

        apiService.onAuthRequest(event, new Callback<AuthReceive>() {

            @Override
            public void success(AuthReceive retroResponse, Response response) {
                Log.e("www", response.getReason().toString());
                if (retroResponse != null) {
                    bus.post(new AuthReceive(retroResponse));
                    // RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("error", error.getMessage());
                Log.e("error", error.getResponse().getReason());
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }

    @Subscribe
    public void onComicRequest(final ComicRequest event) {

        apiService.onComicRequest(event.getCharacter(),event.getLevel(),event.getOption(),event.getToken(), new Callback<ComicReceive>() {

            @Override
            public void success(ComicReceive retroResponse, Response response) {
              //  Log.e("www", response.getReason().toString());
                if (retroResponse != null) {
                    bus.post(new ComicReceive(retroResponse));
                    // RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(retroResponse));
                } else {
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }

            @Override
            public void failure(RetrofitError error) {

               // Log.e("error", error.getMessage());
               // Log.e("error", error.getResponse().getReason());
                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());

            }

        });
    }*/


}
