package tripcar.yadu.com.tripcar.gettersetter;

/**
 * Created by yadu on 16/1/16.
 */
public class UserGetterSetter {

    String phone_number,first_name,last_name,user_access_tocken;
    int update_popup,new_user;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getUpdate_popup() {
        return update_popup;
    }

    public void setUpdate_popup(int update_popup) {
        this.update_popup = update_popup;
    }

    public int getNew_user() {
        return new_user;
    }

    public void setNew_user(int new_user) {
        this.new_user = new_user;
    }

    public String getUser_access_tocken() {
        return user_access_tocken;
    }

    public void setUser_access_tocken(String user_access_tocken) {
        this.user_access_tocken = user_access_tocken;
    }
}
