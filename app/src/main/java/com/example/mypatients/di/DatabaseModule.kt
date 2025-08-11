package com.example.mypatients.di


import android.content.Context
import androidx.room.Room
import com.example.mypatients.data.DefaultPatientRepository
import com.example.mypatients.data.PatientRepository
import com.example.mypatients.data.local.AppDatabase
import com.example.mypatients.data.local.PatientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "mypatients.db").build()

    @Provides
    fun providePatientDao(db: AppDatabase): PatientDao = db.patientDao()

    @Provides @Singleton
    fun provideRepo(
        dao: PatientDao,
        api: com.example.mypatients.data.remote.ApiService
    ): PatientRepository = DefaultPatientRepository(dao, api)

}
