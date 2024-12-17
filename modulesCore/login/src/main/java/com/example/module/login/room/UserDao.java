package com.example.module.login.room;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    int findUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    User validateAccount(String email, String password);

    @Delete()
    void deleteUser(User user);

    @Update
    void updateUser(User user);

}
