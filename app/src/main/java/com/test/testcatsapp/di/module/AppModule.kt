package com.test.testcatsapp.di.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.test.testcatsapp.CatsApp
import com.test.testcatsapp.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@GlideModule
class AppModule : AppGlideModule() {
    @Provides
    @Singleton
    fun provideContext(app: CatsApp): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideRequestOptions(): RequestOptions =
        RequestOptions().placeholder(R.drawable.image_placeholder_150)

    @Singleton
    @Provides
    fun provideGlide(context: Context, requestOptions: RequestOptions): RequestManager =
        Glide.with(context).setDefaultRequestOptions(requestOptions)
}