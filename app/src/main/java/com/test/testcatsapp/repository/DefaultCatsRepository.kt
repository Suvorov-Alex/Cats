package com.test.testcatsapp.repository

import com.test.testcatsapp.api.CatsApi
import com.test.testcatsapp.data.CatsDao
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.Observable
import javax.inject.Inject

class DefaultCatsRepository
@Inject
constructor(
    private val catsDao: CatsDao,
    private val catsApi: CatsApi
) : CatsRepository {

    override fun getCatsFromDb(): Observable<List<Cat>> =
        catsDao
            .all()

    override fun getCatsFromApi() =
        catsApi
            .getCats(
                header = DefaultQueryParams.xApiKeyHeader,
                limit = DefaultQueryParams.limit,
                page = DefaultQueryParams.page,
                order = DefaultQueryParams.order
            )

    override fun saveFavoriteCat(cat: Cat) =
        catsDao
            .insert(cat)

    override fun deleteFavoriteCat(cat: Cat) =
        catsDao
            .deleteById(cat.id)

    private open class QueryParams(
        val xApiKeyHeader: String,
        val limit: Int,
        val page: Int,
        val order: String
    )

    private object DefaultQueryParams : QueryParams(
        xApiKeyHeader = X_API_KEY_HEADER,
        limit = 50,
        page = 3,
        order = DESC_ORDER
    )

    companion object {
        private const val X_API_KEY_HEADER = "04db451c-a335-421b-8943-c2a80526f352"
        private const val DESC_ORDER = "DESC"
    }
}