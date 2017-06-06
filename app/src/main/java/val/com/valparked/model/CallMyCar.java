package val.com.valparked.model;

/**
 * Created by User on 6/5/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class CallMyCar extends BaseResponseModel implements Parcelable
{

    public VehicleInfo vehicalInfo;
    public final static Parcelable.Creator<CallMyCar> CREATOR = new Creator<CallMyCar>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CallMyCar createFromParcel(Parcel in) {
            CallMyCar instance = new CallMyCar();
            instance.vehicalInfo = ((VehicleInfo) in.readValue((VehicleInfo.class.getClassLoader())));
            return instance;
        }

        public CallMyCar[] newArray(int size) {
            return (new CallMyCar[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalInfo);
    }

    public int describeContents() {
        return 0;
    }

}
