package com.example.myapplicationflow.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці `roles`
        database.execSQL("CREATE TABLE roles2 (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, userId INTEGER NOT NULL, roleName TEXT NOT NULL, accessLevel INTEGER NOT NULL, description TEXT NOT NULL)")
    }
}
