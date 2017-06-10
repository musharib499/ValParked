
package val.com.valparked.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Parking extends BaseResponseModel implements Parcelable
{

    private List<ParkingInfo> parkingInfo = null;
    public final static Creator<Parking> CREATOR = new Creator<Parking>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Parking createFromParcel(Parcel in) {
            Parking instance = new Parking();
            in.readList(instance.parkingInfo, (ParkingInfo.class.getClassLoader()));
            return instance;
        }

        public Parking[] newArray(int size) {
            return (new Parking[size]);
        }

    }
    ;

    public List<ParkingInfo> getParkingInfo() {
        return parkingInfo;
    }

    public void setParkingInfo(List<ParkingInfo> parkingInfo) {
        this.parkingInfo = parkingInfo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(parkingInfo);
    }

    public int describeContents() {
        return  0;
    }

}
