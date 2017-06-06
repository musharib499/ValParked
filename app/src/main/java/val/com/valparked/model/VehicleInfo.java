package val.com.valparked.model;

/**
 * Created by User on 6/5/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class VehicleInfo implements Parcelable
{

    public String vehicalNumber;
    public final static Parcelable.Creator<VehicleInfo> CREATOR = new Creator<VehicleInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VehicleInfo createFromParcel(Parcel in) {
            VehicleInfo instance = new VehicleInfo();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public VehicleInfo[] newArray(int size) {
            return (new VehicleInfo[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalNumber);
    }

    public int describeContents() {
        return 0;
    }

}
