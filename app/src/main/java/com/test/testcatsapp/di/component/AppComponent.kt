package com.test.testcatsapp.di.component

import com.test.testcatsapp.CatsApp
import com.test.testcatsapp.di.module.ActivityModule
import com.test.testcatsapp.di.module.AppModule
import com.test.testcatsapp.di.module.FragmentModule
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
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<CatsApp> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<CatsApp>
}