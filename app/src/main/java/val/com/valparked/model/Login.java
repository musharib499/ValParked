
package val.com.valparked.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Login extends BaseResponseModel implements Parcelable
{

    private UserDetails userDetails;
    public final static Creator<Login> CREATOR = new Creator<Login>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Login createFromParcel(Parcel in) {
            Login instance = new Login();
            instance.userDetails = ((UserDetails) in.readValue((UserDetails.class.getClassLoader())));
            return instance;
        }

        public Login[] newArray(int size) {
            return (new Login[size]);
        }

    }
    ;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userDetails);
    }

    public int describeContents() {
        return  0;
    }

}
