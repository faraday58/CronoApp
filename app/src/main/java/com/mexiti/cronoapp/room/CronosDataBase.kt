package com.mexiti.cronoapp.room

import androidx.room.Database
import com.mexiti.cronoapp.model.Cronos


@Database(entities = [Cronos::class], version = 1, exportSchema = false)
abstract class CronosDataBase {
    abstract fun cronosDao():CronosDatabaseDao
}