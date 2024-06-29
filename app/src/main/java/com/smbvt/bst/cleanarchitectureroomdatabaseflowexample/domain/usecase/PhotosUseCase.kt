package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.usecase

import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotosTable
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.repository.PhotosRepository
import javax.inject.Inject


class PhotosUseCase @Inject constructor(private val photosRepository: PhotosRepository) {
    suspend fun insertPhotoUrl(photoUrl: String) {
        photosRepository.insertPhotoUrl(photosTable = PhotosTable(photoUrl = photoUrl))
    }
}