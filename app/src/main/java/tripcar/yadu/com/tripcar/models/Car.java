
package tripcar.yadu.com.tripcar.models;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("org.jsonschema2pojo")
public class Car {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("comfort")
    @Expose
    private String comfort;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("car_type")
    @Expose
    private String carType;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("__v")
    @Expose
    private Integer V;

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
     *     The make
     */
    public String getMake() {
        return make;
    }

    /**
     * 
     * @param make
     *     The make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * 
     * @return
     *     The model
     */
    public String getModel() {
        return model;
    }

    /**
     * 
     * @param model
     *     The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 
     * @return
     *     The comfort
     */
    public String getComfort() {
        return comfort;
    }

    /**
     * 
     * @param comfort
     *     The comfort
     */
    public void setComfort(String comfort) {
        this.comfort = comfort;
    }

    /**
     * 
     * @return
     *     The seats
     */
    public Integer getSeats() {
        return seats;
    }

    /**
     * 
     * @param seats
     *     The seats
     */
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    /**
     * 
     * @return
     *     The colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * 
     * @param colour
     *     The colour
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * 
     * @return
     *     The carType
     */
    public String getCarType() {
        return carType;
    }

    /**
     * 
     * @param carType
     *     The car_type
     */
    public void setCarType(String carType) {
        this.carType = carType;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The user
     */
    public String getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(String user) {
        this.user = user;
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

}
