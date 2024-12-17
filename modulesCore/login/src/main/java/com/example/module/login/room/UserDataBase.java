package com.example.module.login.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();

    private static volatile UserDataBase INSTANCE;

    public static UserDataBase getINSTANCE(Context context) {
        if (context == null) {
            Log.d("UserDataBaseTAG", "context is null");
            return null;
        } else {
            Log.d("UserDataBaseTAG", "context is not null");
        }
        if (INSTANCE == null) {
            synchronized (UserDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, "users").build();
                }
            }
        }
        return INSTANCE;
    }
}
