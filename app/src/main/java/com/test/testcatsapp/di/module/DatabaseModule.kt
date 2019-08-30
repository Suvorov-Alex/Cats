package com.test.testcatsapp.di.module

import android.content.Context
import com.test.testcatsapp.data.CatsDao
import com.test.testcatsapp.data.CatsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideCatsDatabase(context: Context): CatsDatabase =
        CatsDatabase.create(context)

    @Singleton
    @Provides
    fun provideCatsDao(db: CatsDatabase): CatsDao =
        db.catsDao()
}