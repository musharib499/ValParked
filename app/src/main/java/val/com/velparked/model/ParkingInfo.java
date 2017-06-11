
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkingInfo implements Parcelable
{

    private String vehicalNumber;
    private String parkTime;
    private String requestTime;
    private String pickedTime;
    private String status;
    public final static Creator<ParkingInfo> CREATOR = new Creator<ParkingInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ParkingInfo createFromParcel(Parcel in) {
            ParkingInfo instance = new ParkingInfo();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.parkTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.requestTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.pickedTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ParkingInfo[] newArray(int size) {
            return (new ParkingInfo[size]);
        }

    }
    ;

    public String getVehicalNumber() {
        return vehicalNumber;
    }

    public void setVehicalNumber(String vehicalNumber) {
        this.vehicalNumber = vehicalNumber;
    }

    public String getParkTime() {
        return parkTime;
    }

    public void setParkTime(String parkTime) {
        this.parkTime = parkTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getPickedTime() {
        return pickedTime;
    }

    public void setPickedTime(String pickedTime) {
        this.pickedTime = pickedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalNumber);
        dest.writeValue(parkTime);
        dest.writeValue(requestTime);
        dest.writeValue(pickedTime);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
