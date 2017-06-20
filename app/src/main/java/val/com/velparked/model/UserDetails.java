
package val.com.velparked.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDetails implements Parcelable
{

    private String userid;
    private Integer deviceID;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String hotelid;
    private String phoneid;


    private String userType;
    public final static Parcelable.Creator<UserDetails> CREATOR = new Creator<UserDetails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserDetails createFromParcel(Parcel in) {
            UserDetails instance = new UserDetails();
            instance.userid = ((String) in.readValue((String.class.getClassLoader())));
            instance.deviceID = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.email = ((String) in.readValue((String.class.getClassLoader())));
            instance.phone = ((String) in.readValue((String.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.hotelid = ((String) in.readValue((String.class.getClassLoader())));
            instance.phoneid = ((String) in.readValue((String.class.getClassLoader())));
            instance.userType = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public UserDetails[] newArray(int size) {
            return (new UserDetails[size]);
        }

    };


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    public String getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(String phoneid) {
        this.phoneid = phoneid;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userid);
        dest.writeValue(deviceID);
        dest.writeValue(name);
        dest.writeValue(email);
        dest.writeValue(phone);
        dest.writeValue(address);
        dest.writeValue(userType);
        dest.writeValue(hotelid);
        dest.writeValue(phoneid);

    }

    public int describeContents() {
        return 0;
    }

}