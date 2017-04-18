package ru.set66.operatorchat.api;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Alex on 16.04.2017.
 */

public interface InterfaceApi {
    @GET("{file}")
    Call<ResponseBody> getData(@Path("file") String fileName);

    @POST()
    Call<String> clearORdisable(@Field("cmd") String cmd, @Field("hash") String udid);

    @POST()
    Call<String> sendMessage(@Field("cmd") String cmd, @Field("person") String person, @Field("chatmessage") String body, @Field("hash") String udid, @Field("phone") String phone);
}
