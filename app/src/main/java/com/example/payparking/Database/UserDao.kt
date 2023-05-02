package com.example.payparking.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.payparking.Database.UserEntity


@Dao
interface UserDao {

    @Insert
    fun inserDetails(userEntity: UserEntity)

    @Delete
    fun deleteDetails(userEntity: UserEntity)

    @Query("SELECT * from userDetails")
    fun getAllUserDetails() : List<UserEntity>

    @Query("SELECT * from userDetails WHERE e_email = :email ")
    fun getEmailNPass(email: String) : UserEntity


}