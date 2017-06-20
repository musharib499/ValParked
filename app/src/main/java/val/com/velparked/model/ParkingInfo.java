
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ParkingInfo implements Parcelable
{

    private String vehicalNumber;
    private String parkTime;
    private String parkDate;
    private String requestTime;
    private String requestDate;
    private String pickedTime;
    private String pickedDate;
    private String status;
    public final static Creator<ParkingInfo> CREATOR = new Creator<ParkingInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ParkingInfo createFromParcel(Parcel in) {
            ParkingInfo instance = new ParkingInfo();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.parkTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.parkDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.requestTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.requestDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.pickedTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.pickedDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public ParkingInfo[] newArray(int size) {
            return (new ParkingInfo[size]);
        }

    }
    ;



    public String getParkDate() {
        return parkDate;
    }

    public void setParkDate(String parkDate) {
        this.parkDate = parkDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getPickedDate() {
        return pickedDate;
    }

    public void setPickedDate(String pickedDate) {
        this.pickedDate = pickedDate;
    }

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
        dest.writeValue(parkDate);
        dest.writeValue(requestTime);
        dest.writeValue(requestDate);
        dest.writeValue(pickedTime);
        dest.writeValue(pickedDate);
        dest.writeValue(status);
    }

    public int describeContents() {
        return  0;
    }

}
