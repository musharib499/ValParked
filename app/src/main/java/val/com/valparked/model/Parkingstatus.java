
package val.com.valparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Parkingstatus extends BaseResponseModel implements Parcelable
{

    private String vehicalNumber;
    private String parkTime;
    private String requestTime;
    private String pickedTime;
    private String parkedStatus;
    public final static Creator<Parkingstatus> CREATOR = new Creator<Parkingstatus>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Parkingstatus createFromParcel(Parcel in) {
            Parkingstatus instance = new Parkingstatus();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            instance.parkTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.requestTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.pickedTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.parkedStatus = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Parkingstatus[] newArray(int size) {
            return (new Parkingstatus[size]);
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

   /* public String getStatus() {return parkedStatus;}

    public void setParkedStatus(String parkedStatus) {
        this.parkedStatus = parkedStatus;
    }*/

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalNumber);
        dest.writeValue(parkTime);
        dest.writeValue(requestTime);
        dest.writeValue(pickedTime);
        dest.writeValue(parkedStatus);
    }

    public int describeContents() {
        return  0;
    }

}
