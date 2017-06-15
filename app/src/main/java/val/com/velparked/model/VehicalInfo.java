
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class VehicalInfo implements Parcelable
{

    private String vehicalNumber;
    public final static Creator<VehicalInfo> CREATOR = new Creator<VehicalInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public VehicalInfo createFromParcel(Parcel in) {
            VehicalInfo instance = new VehicalInfo();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public VehicalInfo[] newArray(int size) {
            return (new VehicalInfo[size]);
        }

    }
    ;

    public String getVehicalNumber() {
        return vehicalNumber;
    }

    public void setVehicalNumber(String vehicalNumber) {
        this.vehicalNumber = vehicalNumber;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vehicalNumber);
    }

    public int describeContents() {
        return  0;
    }

}
