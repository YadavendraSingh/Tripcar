
package tripcar.yadu.com.tripcar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("fb_email")
    @Expose
    private String fbEmail;
    @SerializedName("fb_id")
    @Expose
    private String fbId;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("fb_friends")
    @Expose
    private Integer fbFriends;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("app_version")
    @Expose
    private Integer appVersion;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private Boolean deviceType;
    @SerializedName("last_login")
    @Expose
    private String lastLogin;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("location")
    @Expose
    private List<String> location = new ArrayList<String>();
    @SerializedName("__v")
    @Expose
    private Integer V;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("pin")
    @Expose
    private String pin;

    /**
     * 
     * @return
     *     The Id
     */
    public String getId() {
        return Id;
    }

    /**
     * 
     * @param Id
     *     The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The fbEmail
     */
    public String getFbEmail() {
        return fbEmail;
    }

    /**
     * 
     * @param fbEmail
     *     The fb_email
     */
    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    /**
     * 
     * @return
     *     The fbId
     */
    public String getFbId() {
        return fbId;
    }

    /**
     * 
     * @param fbId
     *     The fb_id
     */
    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    /**
     * 
     * @return
     *     The age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 
     * @param age
     *     The age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 
     * @return
     *     The fbFriends
     */
    public Integer getFbFriends() {
        return fbFriends;
    }

    /**
     * 
     * @param fbFriends
     *     The fb_friends
     */
    public void setFbFriends(Integer fbFriends) {
        this.fbFriends = fbFriends;
    }

    /**
     * 
     * @return
     *     The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 
     * @param accessToken
     *     The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 
     * @return
     *     The appVersion
     */
    public Integer getAppVersion() {
        return appVersion;
    }

    /**
     * 
     * @param appVersion
     *     The app_version
     */
    public void setAppVersion(Integer appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 
     * @return
     *     The deviceToken
     */
    public String getDeviceToken() {
        return deviceToken;
    }

    /**
     * 
     * @param deviceToken
     *     The device_token
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    /**
     * 
     * @return
     *     The deviceType
     */
    public Boolean getDeviceType() {
        return deviceType;
    }

    /**
     * 
     * @param deviceType
     *     The device_type
     */
    public void setDeviceType(Boolean deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * 
     * @return
     *     The lastLogin
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * 
     * @param lastLogin
     *     The last_login
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     * @param isDeleted
     *     The is_deleted
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 
     * @return
     *     The location
     */
    public List<String> getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(List<String> location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The V
     */
    public Integer getV() {
        return V;
    }

    /**
     * 
     * @param V
     *     The __v
     */
    public void setV(Integer V) {
        this.V = V;
    }

    /**
     * 
     * @return
     *     The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 
     * @param phone
     *     The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 
     * @return
     *     The pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * 
     * @param pin
     *     The pin
     */
    public void setPin(String pin) {
        this.pin = pin;
    }

}
