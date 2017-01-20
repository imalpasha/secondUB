package com.app.comic.api;

import com.app.comic.ui.Model.Receive.DestinationReceive;
import com.app.comic.ui.Model.Receive.DriverInfoReceive;
import com.app.comic.ui.Model.Receive.ListRidesReceive;
import com.app.comic.ui.Model.Receive.LoginReceive;
import com.app.comic.ui.Model.Receive.PassengerInfoReceive;
import com.app.comic.ui.Model.Receive.SelectReceive;
import com.app.comic.ui.Model.Receive.SignDriverReceive;
import com.app.comic.ui.Model.Receive.SignPassengerReceive;
import com.app.comic.ui.Model.Receive.UpdateDriverReceive;
import com.app.comic.ui.Model.Receive.UpdatePassengerReceive;
import com.app.comic.ui.Model.Request.DestinationRequest;
import com.app.comic.ui.Model.Request.DriverInfoRequest;
import com.app.comic.ui.Model.Request.ListRidesRequest;
import com.app.comic.ui.Model.Request.LoginRequest;
import com.app.comic.ui.Model.Request.PassengerInfoRequest;
import com.app.comic.ui.Model.Request.SelectRequest;
import com.app.comic.ui.Model.Request.SignDriverRequest;
import com.app.comic.ui.Model.Request.SignPassengerRequest;
import com.app.comic.ui.Model.Request.UpdateDriverRequest;
import com.app.comic.ui.Model.Request.UpdatePassengerRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    //share_ride
    @POST("login")
    Call<LoginReceive> login(@Body LoginRequest obj);

    @POST("register")
    Call<SignPassengerReceive> signPassenger(@Body SignPassengerRequest obj);

    @POST("registerDriver")
    Call<SignDriverReceive> signDriver(@Body SignDriverRequest obj);

    @POST("postDestination")
    Call<DestinationReceive> destinationRequest(@Body DestinationRequest obj);

    @POST("listRides")
    Call<ListRidesReceive> listRides(@Body ListRidesRequest obj);

    @POST("selectDriver")
    Call<SelectReceive> selectRequest(@Body SelectRequest obj);

    @POST("updatePassenger")
    Call<UpdatePassengerReceive> updatePassenger(@Body UpdatePassengerRequest obj);

    @POST("updateDriver")
    Call<UpdateDriverReceive> updateDriver(@Body UpdateDriverRequest obj);

    @POST("getPassenger")
    Call<PassengerInfoReceive> getPassenger(@Body PassengerInfoRequest obj);

    @POST("getDriver")
    Call<DriverInfoReceive> getDriver(@Body DriverInfoRequest obj);

    //void onComicRequest(@QueryMap Map<String, String> params, Callback<ComicReceive> callback);

    //comic
/* Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiEndpoint.getUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService service = retrofit.create(ApiService.class);

    //@POST("/auth")
    //void onAuthRequest(@Body AuthRequest obj, Callback<AuthReceive> callback);




    //onRetrieveFlightSummary*/

    /*@POST("/auth")
    void onAuthRequest(@Body AuthRequest obj, Callback<AuthReceive> callback);

    //@POST("/comic")
   // void onComicRequest(@Body ComicRequest obj, Callback<ComicReceive> callback);

    @GET("/comic?character={character}&level={level}&option={option}&token={token}")
    void onComicRequest(@Query("character") String character, @Query("level") String level, @Query("option") String option, @Query("token") String token, Callback<ComicReceive> callback);
*/
    //onRetrieveFlightSummary

}


