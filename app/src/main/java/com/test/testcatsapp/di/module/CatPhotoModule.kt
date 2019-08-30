package com.test.testcatsapp.di.module

import android.content.Context
import com.test.testcatsapp.ui.cats.photo.CatPhoto
import com.test.testcatsapp.ui.cats.photo.CatPhotoModel
import com.test.testcatsapp.ui.cats.photo.CatPhotoPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CatPhotoModule {
    @Singleton
    @Provides
    fun provideCatPhotoPresenter(context: Context, model: CatPhoto.Model): CatPhoto.Presenter =
        CatPhotoPresenter(context, model)

    @Singleton
    @Provides
    fun provideCatPhotoModel(): CatPhoto.Model =
        CatPhotoModel()
}