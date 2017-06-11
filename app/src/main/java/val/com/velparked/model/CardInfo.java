
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CardInfo implements Parcelable
{

    private String vehicalNumber;
    public final static Creator<CardInfo> CREATOR = new Creator<CardInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CardInfo createFromParcel(Parcel in) {
            CardInfo instance = new CardInfo();
            instance.vehicalNumber = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public CardInfo[] newArray(int size) {
            return (new CardInfo[size]);
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
