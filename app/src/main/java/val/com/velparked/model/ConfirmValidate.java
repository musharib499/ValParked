
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ConfirmValidate extends BaseResponseModel implements Parcelable
{

    private CardInfo cardInfo;
    public final static Creator<ConfirmValidate> CREATOR = new Creator<ConfirmValidate>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ConfirmValidate createFromParcel(Parcel in) {
            ConfirmValidate instance = new ConfirmValidate();
            instance.cardInfo = ((CardInfo) in.readValue((CardInfo.class.getClassLoader())));
            return instance;
        }

        public ConfirmValidate[] newArray(int size) {
            return (new ConfirmValidate[size]);
        }

    }
    ;


    public CardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cardInfo);
    }

    public int describeContents() {
        return  0;
    }

}
