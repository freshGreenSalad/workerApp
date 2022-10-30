package com.tamaki.workerapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.tamaki.workerapp.data.SslSettings
import com.tamaki.workerapp.data.apiCallsToServer.AWSInterface
import com.tamaki.workerapp.data.datastore.DataStoreImplementaion
import com.tamaki.workerapp.data.datastore.DatastoreInterface
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.data.room.RoomDatabase
import com.tamaki.workerapp.data.room.YourRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import kotlinx.serialization.json.*


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val USER_PREFERENCES = "user_preferences"

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) = Room.databaseBuilder(
        app,
        RoomDatabase::class.java,
        "roomDatabase"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: RoomDatabase) = db.getArticleDao()

    @Singleton
    @Provides
    fun provideRepository(repository: YourRepository): RepositoryInterface = repository

    @Singleton
    @Provides
    fun provideDataStore(dataStore: DataStoreImplementaion): DatastoreInterface = dataStore

    @Singleton
    @Provides
    fun AWSRequest(client: HttpClient, dataStore: DataStore<Preferences>): AWSInterface =
        AWSRequest(client, dataStore)

    @Singleton
    @Provides
    fun AWSclient(@ApplicationContext appContext: Context): HttpClient = HttpClient(Android) {
        /*engine {
            sslManager = { httpsURLConnection ->
                httpsURLConnection.sslSocketFactory = SslSettings.getSslContext(appContext)?.socketFactory
            }
        }*/
        install(Logging) {
            level = LogLevel.ALL
        }

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            }
            )
        }
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}
