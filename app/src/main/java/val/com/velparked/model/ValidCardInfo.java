package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 6/4/2017.
 */

public class ValidCardInfo extends BaseResponseModel implements Parcelable
{

    public CardInfo cardInfo;
    public final static Parcelable.Creator<ValidCardInfo> CREATOR = new Creator<ValidCardInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ValidCardInfo createFromParcel(Parcel in) {
            ValidCardInfo instance = new ValidCardInfo();
            instance.cardInfo = ((CardInfo) in.readValue((CardInfo.class.getClassLoader())));
            return instance;
        }

        public ValidCardInfo[] newArray(int size) {
            return (new ValidCardInfo[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cardInfo);
    }

    public int describeContents() {
        return 0;
    }

}