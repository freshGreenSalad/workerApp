package com.example.workerapp.Data.Room

import android.content.Context
import androidx.room.Room
import com.example.workerapp.Data.Room.ktor.AWSInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModuel {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        roomDatabase::class.java,
        "roomDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: roomDatabase) = db.getArticleDao()

    @Singleton
    @Provides
    fun AWSConnection() = AWSInterface.create()
}