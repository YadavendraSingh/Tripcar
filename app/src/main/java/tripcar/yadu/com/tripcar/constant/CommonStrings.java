package tripcar.yadu.com.tripcar.constant;

/**
 * Created by yadu on 13/1/16.
 */
public class CommonStrings {

    public static String GCM_TOCKEN = "Gcm_Tocken";

    public static String NO_INTERNET_CONNECTION = "No Internet Connection Found";

    public static String MYPREFERENCE = "MyPref";

    public static String IS_LOGGED_IN = "IsLoggedIn";

    public static String PROFILE_URL = "Profile_Url";

    public static String PROFILE_NAME = "Profile_Name";

    public static String URL = "Enter your link here";

    public static String FB_LOGIN_BASE = "fb_login";

    public static String ADD_CAR_BASE = "add_car";

    public static String GET_ALL_CAR = "get_my_cars";

    public static String ACCESS_TOCKEN_BASE = "access_token_login";

    public static String MOBILE_REGISTER_BASE = "mobile_register";

    public static String CHECK_OTP_BASE = "check_otp";

    public static String PHONE_NUMBER = "Phone_Number";

    public static String USER_ACCESS_TOCKEN = "User_Access_Tocken";

    public static String SMS_ORIGIN = "tripcar";
    
    public static String OTP_DELIMITER = ":";

    public static String PHONE_NUMBER_VERIFIED = "Phone_number_verified";

    public static String carBrands[] = {"Audi", "Bentley", "BMW", "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai",
            "Jaguar",
            "Land Rover",
            "Mahindra",
            "Maruti Suzuki",
            "Mercedes Benz",
            "Mitsubishi",
            "Nissan",
            "Porche",
            "Range Rover",
            "Renault",
            "Rolls-Royce",
            "Skoda",
            "Tata Motors",
            "Toyota",
            "Volkswagen",
            "Volvo"

    };

    public static String carModel[] = {"Audi", "Bentley", "BMW", "Chevrolet", "Fiat", "Ford", "Honda", "Hyundai",
            "Jaguar",
            "Land Rover",
            "Mahindra",
            "Maruti Suzuki",
            "Mercedes Benz",
            "Mitsubishi",
            "Nissan",
            "Porche",
            "Range Rover",
            "Renault",
            "Rolls-Royce",
            "Skoda",
            "Tata Motors",
            "Toyota",
            "Volkswagen",
            "Volvo"

    };

    public static String carType[] = {"Hatchbak", "Sedan", "SUV", "MUV", "Mini Van"};

    public static String comfortLevel[] = {"Basic", "Normal", "Comfortable", "Luxury"};

    public static String carColor[] = {"white", "black", "silver", "blue", "green", "dark green", "dark blue", "dark grey", "purple", "brown", "light green", "light blue", "light grey", "red", "pink", "orange", "yellow", "gold"};

    public static String seatsAvailable[] = {"1", "2", "3", "4", "5"};

    public static String KEY_FIND_CAR = "FIND_CAR";

    public static String KEY_DEPARTURE_CITY = "DEPARTURE_CITY";
    public static String KEY_ARRIVAL_CITY = "ARRIVAL_CITY";


    public static final String TABLE_ALL_MY_CARS = "ALL_MY_CARS";

    public static final String KEY_ID = "id";

    public static final String CAR_MAKE = "CAR_MAKE";

    public static final String CAR_MODEL = "CAR_MODEL";

    public static final String CAR_COMFORT = "CAR_COMFORT";

    public static final String SEAT_COUNT = "SEAT_COUNT";

    public static final String CAR_COLOR = "CAR_COLOR";

    public static final String CAR_TYPE = "CAR_TYPE";

    public static final String CREATE_TABLE_ALL_MY_CARS = "CREATE TABLE "
            + TABLE_ALL_MY_CARS
            + " ("
            + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + CAR_MAKE
            + " VARCHAR,"
            + CAR_MODEL
            + " VARCHAR,"
            + CAR_COMFORT
            + " VARCHAR,"
            + SEAT_COUNT
            + " VARCHAR,"
            + CAR_COLOR
            + " VARCHAR,"
            + CAR_TYPE
            + " VARCHAR)";

}
