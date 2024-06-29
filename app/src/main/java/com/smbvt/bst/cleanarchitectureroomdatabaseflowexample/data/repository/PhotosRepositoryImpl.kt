package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.repository

import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotoDao
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotosTable
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.repository.PhotosRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(private val photosDao : PhotoDao) : PhotosRepository {
    override suspend fun insertPhotoUrl(photosTable: PhotosTable) {
        photosDao.insertPhotoUrl(photosTable)
    }
}