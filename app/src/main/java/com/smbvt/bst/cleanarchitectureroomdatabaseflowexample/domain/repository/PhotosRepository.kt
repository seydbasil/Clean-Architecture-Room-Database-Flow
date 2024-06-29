package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.repository

import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotosTable

interface PhotosRepository {
    suspend fun insertPhotoUrl(photosTable: PhotosTable)
}