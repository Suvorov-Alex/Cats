package com.test.testcatsapp.di.component

import com.test.testcatsapp.CatsApp
import com.test.testcatsapp.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        RepositoryModule::class,
        CatsModule::class,
        FavoriteCatsModule::class,
        CatPhotoModule::class
    ]
)
interface AppComponent : AndroidInjector<CatsApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<CatsApp>
}