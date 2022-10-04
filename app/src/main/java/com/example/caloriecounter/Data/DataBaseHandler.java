package com.example.caloriecounter.Data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.caloriecounter.model.Food;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

//Class used for database management

public class DataBaseHandler extends SQLiteOpenHelper {
    private final ArrayList<Food> foodList = new ArrayList<>();

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }



    public DataBaseHandler(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.FOOD_NAME + " TEXT, "
                + Constants.CALORIES + " INT, "
                + Constants.DATE_ADDED + " LONG, "
                + Constants.PHOTO + " BLOB );";

        db.execSQL(CREATE_TABLE);

    }

//    public void something()
//    {
//        SQLiteDatabase dba = this.getWritableDatabase();
//        dba.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
//        onCreate(dba);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }


    //add food method
    public void addFood(Food food) {
        SQLiteDatabase dba = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.FOOD_NAME, food.getFoodName());
        values.put(Constants.CALORIES, food.getCalories());
        values.put(Constants.DATE_ADDED, System.currentTimeMillis());
        //Stores the bitmap in bytes
        values.put(Constants.PHOTO, getBytes(food.getPhoto()));

        dba.insert(Constants.TABLE_NAME, null, values);
        Log.d("Added Food Item", "YES");
        dba.close();
    }

    public Integer deleteFood(String id)
    {
        SQLiteDatabase dba = this.getWritableDatabase();
        return dba.delete(Constants.TABLE_NAME, "KEY_ID", new String[]{id});
    }

    @SuppressLint("Range")
    public ArrayList<Food> getAllFood() {
        foodList.clear();
        SQLiteDatabase dba = this.getReadableDatabase();
        Cursor cursor = dba.query(Constants.TABLE_NAME,
                new String[]{Constants.KEY_ID, Constants.FOOD_NAME, Constants.CALORIES, Constants.DATE_ADDED, Constants.PHOTO},
                null, null, null, null, Constants.DATE_ADDED + " DESC ");

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Constants.FOOD_NAME)));
                food.setCalories(cursor.getInt(cursor.getColumnIndex(Constants.CALORIES)));
                food.setFoodId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                byte[] image = cursor.getBlob(cursor.getColumnIndex(Constants.PHOTO));
                Bitmap photo = getImage(image);
                food.setPhoto(photo);

                DateFormat dateFormat = DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_ADDED))).getTime());
                food.setRecordDate(date);

                foodList.add(food);

            } while (cursor.moveToNext());
        }
        cursor.close();
        dba.close();
        return foodList;
    }

}
