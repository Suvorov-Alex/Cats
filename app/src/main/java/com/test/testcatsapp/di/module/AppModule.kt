package com.test.testcatsapp.di.module

import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.test.testcatsapp.CatsApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@GlideModule
class AppModule : AppGlideModule() {
    @Provides
    @Singleton
    fun provideContext(app: CatsApp): Context = app.applicationContext
}