package com.example.myapplicationflow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplicationflow.models.Role
import com.example.myapplicationflow.models.User

@Database(entities = [User::class, Role::class], version = 1, exportSchema = true)
abstract class ItemDatabase : RoomDatabase() {

    abstract val itemDatabaseDao: ItemDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getInstance(context: Context): ItemDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "users_information"
                    ).allowMainThreadQueries()
                        //.addMigrations(MIGRATION_1_2) // Додаємо міграцію
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}