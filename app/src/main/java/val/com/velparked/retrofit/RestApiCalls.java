package val.com.velparked.retrofit;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import val.com.velparked.model.BaseResponseModel;
import val.com.velparked.model.CallMyCar;
import val.com.velparked.model.ConfirmCallVehicle;
import val.com.velparked.model.ConfirmValidate;
import val.com.velparked.model.Login;
import val.com.velparked.model.Parking;
import val.com.velparked.model.ValidCardInfo;


/**
 * Created by vinodtakhar on 25/5/16.
 */
public class RestApiCalls {
    //public static final String REST_HOST = BuildConfig.REST_HOST+"v2/";
    public static final String REST_HOST = "http://smartplaykids.com/parking/application/";//BuildConfig.;

    private static Retrofit retrofit = null;
    private static RetrofitInterface retrofitInterface = null;


    public static RetrofitInterface getRetrofitApi() {
        if (retrofitInterface == null) {
            HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
            logger.setLevel(HttpLoggingInterceptor.Level.BODY);

          OkHttpClient  mHttpClient = new OkHttpClient.Builder().addInterceptor(logger).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(REST_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mHttpClient)
                    .build();
            retrofitInterface = retrofit.create(RetrofitInterface.class);
        }
        return retrofitInterface;
    }


    public static Call<Login> getLogin(HashMap<String, String> params) {
        return getRetrofitApi().getLogin(params);
    }
    public static Call<BaseResponseModel> getLogOut(HashMap<String, String> params) {
        return getRetrofitApi().getLogOut(params);
    }
    public static Call<ConfirmValidate> getConfirmValidate(HashMap<String, String> params) {
        return getRetrofitApi().getConfirmValidate(params);
    }
    public static Call<ConfirmCallVehicle> getConfirmCallMyCar(HashMap<String, String> params) {
        return getRetrofitApi().getConfirmCallMyCar(params);
    }
    public static Call<ValidCardInfo> getIssueCard(HashMap<String, String> params) {
        return getRetrofitApi().getIssueCard(params);
    }
    public static Call<ValidCardInfo> getIssueCardNumber(HashMap<String, String> params) {
        return getRetrofitApi().getIssueCardNumber(params);
    }
    public static Call<CallMyCar> getCallVehicle(HashMap<String, String> params) {
        return getRetrofitApi().getCallVehicle(params);
    }
    public static Call<ValidCardInfo> getValidCard(HashMap<String, String> params) {
        return getRetrofitApi().getValidCard(params);
    }
    public static Call<Parking> getParking(HashMap<String, String> params) {
        return getRetrofitApi().getParking(params);
    }


}
