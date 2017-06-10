package val.com.valparked.retrofit;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import val.com.valparked.model.BaseResponseModel;
import val.com.valparked.model.CallMyCar;
import val.com.valparked.model.Login;
import val.com.valparked.model.Parking;
import val.com.valparked.model.ValidCardInfo;


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
    public static Call<ValidCardInfo> getIssueCard(HashMap<String, String> params) {
        return getRetrofitApi().getIssueCard(params);
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
