package com.bigknowledge.Notifications;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAxQVqTPA:APA91bF9L-D2crHCBhtIwoncKbkP0Js5n9IL-e3eoc9Udn4Q5HKgpUePU8Vdr7NVqdtnLSDIz0OQUjgsW5u2mtVbwKE0JB6DA0YxE3A6IZ-82txYHTxFRnQ-UyOTEdzaTymWoRldCsdQ"
    })
    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);
}
