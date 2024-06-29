package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME_PHOTOS)
data class PhotosTable(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val photoUrl: String
)