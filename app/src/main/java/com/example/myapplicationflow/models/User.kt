package com.example.myapplicationflow.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null,

    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = null,

    @ColumnInfo(name = "online_status")
    var onlineStatus: Boolean = false,

    @ColumnInfo(name = "age")
    var age: Int = 0

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}