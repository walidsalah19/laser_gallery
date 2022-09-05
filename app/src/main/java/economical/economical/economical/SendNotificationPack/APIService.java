package economical.economical.economical.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                     "Content-Type:application/json",
                    "Authorization:key=AAAAvCvNL-U:APA91bGbItpBkiH6P6YCResr8FR0-1w6B9r5mfAQtx3FB7vZNx0FwJQOSGNpfMoKdQaPvTIUgjLBeFejuiMSxw3LLpzOBOIzisHwq2vDnNrsgtUW5caaw3xa1QEeVhJdC3G0KbrVEhG0" // Your server key refer to video for finding your server key
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

