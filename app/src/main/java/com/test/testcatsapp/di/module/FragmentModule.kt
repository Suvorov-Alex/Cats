package com.test.testcatsapp.di.module

import com.test.testcatsapp.ui.cats.all.CatsFragment
import com.test.testcatsapp.ui.cats.favorite.FavoriteCatsFragment
import com.test.testcatsapp.ui.cats.photo.CatPhotoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeCatsFragment(): CatsFragment

    @ContributesAndroidInjector
    abstract fun contributeFavoriteCatsFragment(): FavoriteCatsFragment

    @ContributesAndroidInjector
    abstract fun contributeCatPhotoFragment(): CatPhotoFragment
}