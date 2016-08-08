
package tripcar.yadu.com.tripcar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")

public class Datum {

    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("departure_date")
    @Expose
    private String departureDate;
    @SerializedName("departure_time")
    @Expose
    private String departureTime;
    @SerializedName("departure_datetime")
    @Expose
    private String departureDatetime;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("seats")
    @Expose
    private Integer seats;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("luggage")
    @Expose
    private String luggage;
    @SerializedName("departure_address")
    @Expose
    private String departureAddress;
    @SerializedName("departure_city")
    @Expose
    private String departureCity;
    @SerializedName("departure_state")
    @Expose
    private String departureState;
    @SerializedName("arrival_address")
    @Expose
    private String arrivalAddress;
    @SerializedName("departure_country")
    @Expose
    private String departureCountry;
    @SerializedName("arrival_city")
    @Expose
    private String arrivalCity;
    @SerializedName("arrival_state")
    @Expose
    private String arrivalState;
    @SerializedName("arrival_country")
    @Expose
    private String arrivalCountry;
    @SerializedName("car")
    @Expose
    private Car car;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("end_point")
    @Expose
    private List<String> endPoint = new ArrayList<String>();
    @SerializedName("start_point")
    @Expose
    private List<String> startPoint = new ArrayList<String>();
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
     *     The departureDate
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * 
     * @param departureDate
     *     The departure_date
     */
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * 
     * @return
     *     The departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 
     * @param departureTime
     *     The departure_time
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 
     * @return
     *     The departureDatetime
     */
    public String getDepartureDatetime() {
        return departureDatetime;
    }

    /**
     * 
     * @param departureDatetime
     *     The departure_datetime
     */
    public void setDepartureDatetime(String departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     *     The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The luggage
     */
    public String getLuggage() {
        return luggage;
    }

    /**
     * 
     * @param luggage
     *     The luggage
     */
    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    /**
     * 
     * @return
     *     The departureAddress
     */
    public String getDepartureAddress() {
        return departureAddress;
    }

    /**
     * 
     * @param departureAddress
     *     The departure_address
     */
    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    /**
     * 
     * @return
     *     The departureCity
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * 
     * @param departureCity
     *     The departure_city
     */
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    /**
     * 
     * @return
     *     The departureState
     */
    public String getDepartureState() {
        return departureState;
    }

    /**
     * 
     * @param departureState
     *     The departure_state
     */
    public void setDepartureState(String departureState) {
        this.departureState = departureState;
    }

    /**
     * 
     * @return
     *     The arrivalAddress
     */
    public String getArrivalAddress() {
        return arrivalAddress;
    }

    /**
     * 
     * @param arrivalAddress
     *     The arrival_address
     */
    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    /**
     * 
     * @return
     *     The departureCountry
     */
    public String getDepartureCountry() {
        return departureCountry;
    }

    /**
     * 
     * @param departureCountry
     *     The departure_country
     */
    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    /**
     * 
     * @return
     *     The arrivalCity
     */
    public String getArrivalCity() {
        return arrivalCity;
    }

    /**
     * 
     * @param arrivalCity
     *     The arrival_city
     */
    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    /**
     * 
     * @return
     *     The arrivalState
     */
    public String getArrivalState() {
        return arrivalState;
    }

    /**
     * 
     * @param arrivalState
     *     The arrival_state
     */
    public void setArrivalState(String arrivalState) {
        this.arrivalState = arrivalState;
    }

    /**
     * 
     * @return
     *     The arrivalCountry
     */
    public String getArrivalCountry() {
        return arrivalCountry;
    }

    /**
     * 
     * @param arrivalCountry
     *     The arrival_country
     */
    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    /**
     * 
     * @return
     *     The car
     */
    public Car getCar() {
        return car;
    }

    /**
     * 
     * @param car
     *     The car
     */
    public void setCar(Car car) {
        this.car = car;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
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
     *     The endPoint
     */
    public List<String> getEndPoint() {
        return endPoint;
    }

    /**
     * 
     * @param endPoint
     *     The end_point
     */
    public void setEndPoint(List<String> endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * 
     * @return
     *     The startPoint
     */
    public List<String> getStartPoint() {
        return startPoint;
    }

    /**
     * 
     * @param startPoint
     *     The start_point
     */
    public void setStartPoint(List<String> startPoint) {
        this.startPoint = startPoint;
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
