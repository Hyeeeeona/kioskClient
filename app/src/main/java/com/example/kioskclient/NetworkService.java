package com.example.kioskclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {
    /*
    @POST("/api/versions/")
    Call<Version> port_version(@Body Version version);

    @PATCH("/api/versions/{pk}/")
    Call<Version> patch_version(@Path("pk") int pk, @Body Version version);
    @DELETE("/api/versions/{pk}/")
    Call<Version> delete_version(@Path("pk") int pk);
    @GET("/api/versions/")
    Call<List<Version>> get_version();
    @GET("/api/versions/{pk}/")
    Call<Version> get_pk_version(@Path("pk") int pk);
*/
    @POST("/mobilekiosk/shopinfo/")
    Call<ShopInfo> post_shopinfo(@Body ShopInfo shopinfo);
    @PATCH("/mobilekiosk/shopinfo/{pk}/")
    Call<ShopInfo> patch_shopinfo(@Path("pk") int pk, @Body ShopInfo shopinfo);
    @DELETE("/api/restaurants/{pk}/")
    Call<ShopInfo> delete_shopinfo(@Path("pk") int pk);
    @GET("/mobilekiosk/shopinfo/")
    Call<List<ShopInfo>> get_shopinfo();
    @GET("/mobilekiosk/shopinfo/{pk}/")
    Call<ShopInfo> get_pk_shopinfo(@Path("pk") int pk);
    //@GET("/api/weathers/{pk}/shopinfo_list/")
    //Call<List<ShopInfo>> get_weather_pk_shopinfo(@Path("pk") int pk);

}

//참조 : https://duzi077.tistory.com/129?category=703147