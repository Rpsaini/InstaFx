package com.web.instafx.fileupload;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AddEventInterface  {

    @Multipart
    @POST("submit-kyc-images")
    Observable<Response<ServerResponse>> uploadKyc
            (
            @Part("token") RequestBody token,
            @Part("DeviceToken") RequestBody DeviceToken,
            @Part("Version") RequestBody Version,
            @Part("PlatForm") RequestBody PlatForm,
            @Part("Timestamp") RequestBody Timestamp,
            @Header("X-API-KEY") String apikey,

            @Part MultipartBody.Part image,
            @Part("image") RequestBody name
            );


//    @Multipart
//    @POST("update-profile")
//    Observable<Response<ServerResponse>> saveProfileWithoutImage(
//            @Part("user_id") RequestBody user_id,
//            @Part("first_name") RequestBody description,
//            @Part("last_name") RequestBody event_date,
//            @Part("mobile") RequestBody event_time,
//            @Header("token") String authHeader
//    );
//
//
//
//
//    @Multipart
//    @POST("send-reply")
//    Observable<Response<ServerResponse>> ticketReply(
//            @Part("user_id") RequestBody user_id,
//            @Part("query_id") RequestBody query_id,
//            @Part("message") RequestBody message,
//            @Header("token") String authHeader,
//            @Part MultipartBody.Part image,
//            @Part("image") RequestBody name
//
//    );
//
//
//    @Multipart
//    @POST("send-reply")
//    Observable<Response<ServerResponse>> ticketReplywithoutimage(
//            @Part("user_id") RequestBody user_id,
//            @Part("query_id") RequestBody query_id,
//            @Part("message") RequestBody message,
//            @Header("token") String authHeader
//
//
//
//
//    );








}
