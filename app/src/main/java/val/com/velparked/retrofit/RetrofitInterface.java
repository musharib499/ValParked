package val.com.velparked.retrofit;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import val.com.velparked.model.BaseResponseModel;
import val.com.velparked.model.CallMyCar;
import val.com.velparked.model.ConfirmCallVehicle;
import val.com.velparked.model.ConfirmValidate;
import val.com.velparked.model.Login;
import val.com.velparked.model.Parking;
import val.com.velparked.model.ValidCardInfo;

/**
 * Created by vinodtakhar on 2/6/16.
 */
public interface RetrofitInterface {

    @GET("login.php")
    Call<Login> getLogin(@QueryMap Map<String, String> params);

    @GET("logout.php")
    Call<BaseResponseModel> getLogOut(@QueryMap Map<String, String> params);

    @GET("confirm_call_vehical.php")
    Call<ConfirmCallVehicle> getConfirmCallMyCar(@QueryMap Map<String, String> params);

    @GET("confirm_validate_card.php")
    Call<ConfirmValidate> getConfirmValidate(@QueryMap Map<String, String> params);

    @GET("issue_card.php")
    Call<ValidCardInfo> getIssueCard(@QueryMap Map<String, String> params);

    @GET("issue_number.php")
    Call<ValidCardInfo> getIssueCardNumber(@QueryMap Map<String, String> params);

    @GET("call_vehical.php")
    Call<CallMyCar> getCallVehicle(@QueryMap Map<String, String> params);

    @GET("validate_card.php")
    Call<ValidCardInfo> getValidCard(@QueryMap Map<String, String> params);

    @GET("parking_status.php")
    Call<Parking> getParking(@QueryMap Map<String, String> params);

   /* @GET("startup/{dealerId}")
    Call<StartupApiModel> getStartup(@Path("dealerId") String dealerId);



    @FormUrlEncoded
    @POST("testimonial/{dealerId}")
    Call<BaseResponseModel> postTestimonial(@Path("dealerId") String dealerId,
                                            @FieldMap Map<String, String> params);
    @Multipart
    @POST("testimonial/{dealerId}")
    Call<BaseResponseModel> postTestimonial(@Path("dealerId") String dealerId,
                                            @Part("file\"; filename=\"imagefile.jpg\" ") RequestBody file,
                                            @PartMap Map<String, RequestBody> params);*/
}
