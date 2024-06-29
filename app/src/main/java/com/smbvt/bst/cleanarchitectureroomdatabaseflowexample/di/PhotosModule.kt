package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.di

import android.content.Context
import androidx.room.Room
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.DATABASE_NAME_PHOTOS
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotoDao
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotosDatabase
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.repository.PhotosRepositoryImpl
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.repository.PhotosRepository
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.domain.usecase.PhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PhotosModule {
    @Provides
    fun providePhotosDatabase(@ApplicationContext context : Context): PhotosDatabase {
        return Room.databaseBuilder(context = context, PhotosDatabase::class.java, DATABASE_NAME_PHOTOS)
            .build()
    }

    @Provides
    fun providePhotoDao(database: PhotosDatabase): PhotoDao {
        return database.getPhotoDao()
    }

    @Provides
    fun providePhotoRepository(photoDao: PhotoDao): PhotosRepository {
        return PhotosRepositoryImpl(photoDao)
    }

    @Provides
    fun providePhotosUseCase(photosRepository: PhotosRepository): PhotosUseCase {
        return PhotosUseCase(photosRepository)
    }

}