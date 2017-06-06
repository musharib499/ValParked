
package val.com.valparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseResponseModel implements Parcelable
{

    private boolean status;
    private String message;
    public final static Creator<BaseResponseModel> CREATOR = new Creator<BaseResponseModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public BaseResponseModel createFromParcel(Parcel in) {
            BaseResponseModel instance = new BaseResponseModel();
            instance.status = ((boolean) in.readValue((String.class.getClassLoader())));
            instance.message = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public BaseResponseModel[] newArray(int size) {
            return (new BaseResponseModel[size]);
        }

    }
    ;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
