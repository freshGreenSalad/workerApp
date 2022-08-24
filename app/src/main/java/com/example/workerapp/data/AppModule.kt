package com.example.workerapp.data

import android.content.Context
import androidx.room.Room
import com.example.workerapp.data.ktor.AWSInterface
import com.example.workerapp.data.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RoomDatabase::class.java,
        "roomDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: RoomDatabase) = db.getArticleDao()

    @Singleton
    @Provides
    fun AWSConnection() = AWSInterface.create()
}