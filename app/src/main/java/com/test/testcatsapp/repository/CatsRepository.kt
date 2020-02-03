package com.test.testcatsapp.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import com.test.testcatsapp.api.entity.Cat as ApiCat
import com.test.testcatsapp.data.entity.Cat as DbCat

interface CatsRepository {
    fun fromDb(): Observable<List<DbCat>>

    fun fromApi(): Single<List<ApiCat>>

    fun saveFavorite(cat: DbCat) : Completable
    fun deleteFavorite(cat: DbCat) : Completable
}