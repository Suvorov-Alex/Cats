package com.test.testcatsapp.repository

import com.test.testcatsapp.api.CatsApi
import com.test.testcatsapp.data.CatsDao
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.Completable
import javax.inject.Inject

class DefaultCatsRepository @Inject constructor(
    private val catsDao: CatsDao,
    private val catsApi: CatsApi
) : CatsRepository {

    override fun fromDb() =
        catsDao.all()

    override fun fromApi() =
        catsApi.search(
            header = DefaultQueryParams.xApiKeyHeader,
            limit = DefaultQueryParams.limit,
            page = DefaultQueryParams.page,
            order = DefaultQueryParams.order,
            type = DefaultQueryParams.type
        )

    override fun saveFavorite(cat: Cat) =
        Completable.fromAction { catsDao.insert(cat) }

    override fun deleteFavorite(cat: Cat) =
        Completable.fromAction { catsDao.deleteById(cat.id) }

    private open class QueryParams(
        val xApiKeyHeader: String,
        val limit: Int,
        val page: Int,
        val order: String,
        val type: String
    )

    private object DefaultQueryParams : QueryParams(
        xApiKeyHeader = X_API_KEY_HEADER,
        limit = 50,
        page = 1,
        order = DESC_ORDER,
        type = GIF_TYPE
    )

    companion object {
        private const val X_API_KEY_HEADER = "04db451c-a335-421b-8943-c2a80526f352"
        private const val DESC_ORDER = "DESC"
        private const val GIF_TYPE = "gif"
    }
}