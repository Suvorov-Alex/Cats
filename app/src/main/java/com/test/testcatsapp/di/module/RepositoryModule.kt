package com.test.testcatsapp.di.module

import com.test.testcatsapp.api.CatsApi
import com.test.testcatsapp.data.CatsDao
import com.test.testcatsapp.repository.CatsRepository
import com.test.testcatsapp.repository.DefaultCatsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideCatsRepository(catsDao: CatsDao, catsApi: CatsApi): CatsRepository =
        DefaultCatsRepository(catsDao, catsApi)
}