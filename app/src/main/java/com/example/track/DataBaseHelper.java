package com.example.track;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String USER = "USER";
    public static final String TRACK_NAME = "TRACK_NAME";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String LATITUDE = "LATITUDE";
    public static final String ALTITUDE = "ALTITUDE";
    public static final String DATE = "DATE";
    public static final String IMAGE = "IMAGE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "track.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," + TRACK_NAME + " TEXT," + LONGITUDE + " REAl," + LATITUDE + " REAl," + ALTITUDE + " REAl," + DATE + " TEXT," + IMAGE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Track track) {
        if(track!=null){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TRACK_NAME, track.getName());
            cv.put(LATITUDE, track.getLatitude());
            cv.put(LONGITUDE, track.getLongitude());
            cv.put(ALTITUDE, track.getAltitude());
            cv.put(DATE, track.getDate());
            cv.put(IMAGE,track.getImageUri());
            long insert = db.insert(USER, null, cv);
            return insert == -1 ? false : true;
        }
        return false;
}
   public ArrayList<Track> getAll(){
       ArrayList<Track> tracks=new ArrayList<>();
       String querySelector="SELECT * FROM "+USER;
       SQLiteDatabase db =getReadableDatabase();
       Cursor cursor=db.rawQuery(querySelector,null);
     if(cursor.moveToNext()){
         do {
             int trackId=cursor.getInt(0);
             String trackName=cursor.getString(1);
             double trackLongitude=cursor.getDouble(2);
             double trackLatitude=cursor.getDouble(3);
             double trackAltitude=cursor.getDouble(4);
             String trackDate=cursor.getString(5);
             String trackImage=cursor.getString(6);
         Track track =new Track(trackId,trackLatitude,trackAltitude,trackLongitude,trackImage,trackName,trackDate);
         tracks.add(track);
         }while (cursor.moveToNext());
     }else{
// TODO: 5/16/2021 add failed case 
     }
     cursor.close();
     db.close();
       return  tracks;
   }
   public boolean deleteOne (Track track){
       String querySelector="DELETE FROM "+USER+" WHERE ID="+track.getId();
       SQLiteDatabase db =getWritableDatabase();
      Cursor cursor= db.rawQuery(querySelector,null);
      File file =new File(track.getImageUri());

        if(cursor.moveToFirst())
        {
            db.close();
            cursor.close();
            return true;
        }else {
            db.close();
            cursor.close();
            return false;
        }
   }
    public boolean deleteOneId (int id){
        String querySelector="DELETE FROM "+USER+" WHERE ID="+id;
        SQLiteDatabase db =getWritableDatabase();
        Cursor cursor= db.rawQuery(querySelector,null);
        if(cursor.moveToFirst())
        {
            db.close();
            cursor.close();
            return true;
        }else {
            db.close();
            cursor.close();
            return false;
        }
    }

    }