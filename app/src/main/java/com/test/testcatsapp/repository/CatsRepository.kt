package com.test.testcatsapp.repository

import io.reactivex.Observable
import io.reactivex.Single
import com.test.testcatsapp.api.entity.Cat as ApiCat
import com.test.testcatsapp.data.entity.Cat as DbCat

interface CatsRepository {
    fun getCatsFromDb(): Observable<List<DbCat>>

    fun getCatsFromApi(): Single<List<ApiCat>>

    fun saveFavoriteCat(cat: DbCat)
    fun deleteFavoriteCat(cat: DbCat)
}