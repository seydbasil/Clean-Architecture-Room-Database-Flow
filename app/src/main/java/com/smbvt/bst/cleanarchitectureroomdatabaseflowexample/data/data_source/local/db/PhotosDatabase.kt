package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

const val PHOTOS_DB_VERSION = 1
const val DATABASE_NAME_PHOTOS = "db1_photos"
const val TABLE_NAME_PHOTOS = "tb1_photos"

@Database(entities = [PhotosTable::class], version = PHOTOS_DB_VERSION, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun getPhotoDao() : PhotoDao

}