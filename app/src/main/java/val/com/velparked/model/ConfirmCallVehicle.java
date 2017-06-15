
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfirmCallVehicle extends BaseResponseModel implements Parcelable
{

    private VehicalInfo vehicalInfo;
    public final static Creator<ConfirmCallVehicle> CREATOR = new Creator<ConfirmCallVehicle>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ConfirmCallVehicle createFromParcel(Parcel in) {
            ConfirmCallVehicle instance = new ConfirmCallVehicle();
            instance.vehicalInfo = ((VehicalInfo) in.readValue((VehicalInfo.class.getClassLoader())));
            return instance;
        }

        public ConfirmCallVehicle[] newArray(int size) {
            return (new ConfirmCallVehicle[size]);
        }

    };

    public VehicalInfo getVehicalInfo() {
        return vehicalInfo;
    }

    public void setVehicalInfo(VehicalInfo vehicalInfo) {
        this.vehicalInfo = vehicalInfo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalInfo);
    }

    public int describeContents() {
        return  0;
    }

}
