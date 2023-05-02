package com.example.payparking.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userDetails")
data class UserEntity(
    @PrimaryKey val e_email: String,
    @ColumnInfo("e_username") val e_username: String,
    @ColumnInfo("e_password") val password: String,
    @ColumnInfo("e_mobile") val mobile: Int
)