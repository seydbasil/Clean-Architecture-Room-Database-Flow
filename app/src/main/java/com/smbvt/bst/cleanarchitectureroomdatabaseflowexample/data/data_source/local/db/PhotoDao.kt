package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotoUrl(photosTable: PhotosTable)

    @Query("SELECT * FROM $TABLE_NAME_PHOTOS")
    suspend fun getPhotoUrls() : List<PhotosTable>
}