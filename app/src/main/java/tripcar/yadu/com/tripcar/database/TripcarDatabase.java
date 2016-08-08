package tripcar.yadu.com.tripcar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import tripcar.yadu.com.tripcar.constant.CommonStrings;
import tripcar.yadu.com.tripcar.gettersetter.UserCarGetterSetter;

/**
 * Created by yadu on 18/4/16.
 */
public class TripcarDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TRIPCAR_DATABASE";
    public static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;


    public TripcarDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e) {
        }
    }

    public void close() {
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CommonStrings.CREATE_TABLE_ALL_MY_CARS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Store data

    public void insertCarData(UserCarGetterSetter data) {

        //db.delete("ALL_MY_CARS", null, null);
        ContentValues values = new ContentValues();

        try {


       //     for (int i = 0; i < data.getSku_cd().size(); i++) {

                values.put("CAR_MAKE", data.getMake());
                values.put("CAR_MODEL", data.getModel());
                values.put("CAR_COMFORT", data.getComfort());
                values.put("SEAT_COUNT", data.getSeats());

                values.put("CAR_COLOR", data.getColourc());
                values.put("CAR_TYPE", data.getCar_type());

                db.insert("ALL_MY_CARS", null, values);


          //  }

        } catch (Exception ex) {
            Log.d("Insert Car Data ",
                    ex.toString());
        }

    }


//Get car Data

    public ArrayList<UserCarGetterSetter> getAllCars() {
        Log.d("Fetch car data->Start<-",
                "----");
        ArrayList<UserCarGetterSetter> list = new ArrayList<UserCarGetterSetter>();
        Cursor dbcursor = null;

        try {

            dbcursor = db
                    .rawQuery(
                            "SELECT * FROM ALL_MY_CARS", null);

            if (dbcursor != null) {
                dbcursor.moveToFirst();
                while (!dbcursor.isAfterLast()) {
                    UserCarGetterSetter sb = new UserCarGetterSetter();

                    sb.setMake(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CAR_MAKE")));

                    sb.setModel(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CAR_MODEL")));

                    sb.setCar_type(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CAR_TYPE")));

                    sb.setComfort(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CAR_COMFORT")));

                    sb.setSeats(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("SEAT_COUNT")));
                    sb.setColourc(dbcursor.getString(dbcursor
                            .getColumnIndexOrThrow("CAR_COLOR")));


							/*sb.setClos_stock_cold_room(dbcursor.getString(dbcursor
									.getColumnIndexOrThrow("COLD_ROOM")));*/
							/*sb.setClos_stock_meccaindf(dbcursor.getString(dbcursor
									.getColumnIndexOrThrow("MCCAIN_DF")));*/
							/*sb.setClos_stock_storedf(dbcursor.getString(dbcursor
									.getColumnIndexOrThrow("STORE_DF")));*/
							/*sb.setMidday_stock(dbcursor.getString(dbcursor
									.getColumnIndexOrThrow("MIDDAY_STOCK")));*/


                    list.add(sb);
                    dbcursor.moveToNext();
                }
                dbcursor.close();
                return list;
            }

        } catch (Exception e) {
            Log.d("Exception",
                    e.toString());
            return list;
        }

        Log.d("Fetching-->Stop<-",
                "-------------------");
        return list;
    }
}
