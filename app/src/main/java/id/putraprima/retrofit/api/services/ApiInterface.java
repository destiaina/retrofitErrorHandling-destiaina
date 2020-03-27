package id.putraprima.retrofit.api.services;

import id.putraprima.retrofit.api.models.AppVersion;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.Profile;
import id.putraprima.retrofit.api.models.ProfileRequest;
import id.putraprima.retrofit.api.models.ProfileResponse;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.models.UpdatePasswordRequest;
import id.putraprima.retrofit.api.models.UpdatePasswordResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiInterface{
    @GET("/")
    Call<AppVersion> getAppVersion();
    @POST("/api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("/api/auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);
    @GET("/api/auth/me")
    Call<Data<Profile>> getProfile(@Header("Authorization") String token);

    @PATCH("/api/account/profile")
    Call<Data<Profile>> editProfile(@Header("Authorization") String token, @Body ProfileRequest Profile);

    @PATCH("/api/account/password")
    Call<Data<Profile>> editPassword(@Header("Authorization") String token, @Body UpdatePasswordRequest Profile);

}