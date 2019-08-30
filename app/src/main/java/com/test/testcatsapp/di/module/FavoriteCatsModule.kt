package com.test.testcatsapp.di.module

import com.test.testcatsapp.repository.CatsRepository
import com.test.testcatsapp.ui.cats.favorite.FavoriteCats
import com.test.testcatsapp.ui.cats.favorite.FavoriteCatsModel
import com.test.testcatsapp.ui.cats.favorite.FavoriteCatsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FavoriteCatsModule {
    @Singleton
    @Provides
    fun provideFavoriteCatsModel(catsRepository: CatsRepository): FavoriteCats.Model =
        FavoriteCatsModel(catsRepository)

    @Singleton
    @Provides
    fun provideFavoriteCatsPresenter(model: FavoriteCats.Model): FavoriteCats.Presenter =
        FavoriteCatsPresenter(model)
}