package com.example.myapplicationflow.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
data class Role(
    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "user_id")
    var userId: Int = 0,

    @ColumnInfo(name = "access_level")
    var accessLevel: Int = 0,

    @ColumnInfo(name = "description")
    var description: String? = null

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}