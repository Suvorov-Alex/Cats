package com.test.testcatsapp.di.module

import com.test.testcatsapp.repository.CatsRepository
import com.test.testcatsapp.ui.cats.all.Cats
import com.test.testcatsapp.ui.cats.all.CatsModel
import com.test.testcatsapp.ui.cats.all.CatsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CatsModule {
    @Singleton
    @Provides
    fun provideCatsModel(catsRepository: CatsRepository): Cats.Model =
        CatsModel(catsRepository)

    @Singleton
    @Provides
    fun provideCatsPresenter(model: Cats.Model): Cats.Presenter =
        CatsPresenter(model)
}