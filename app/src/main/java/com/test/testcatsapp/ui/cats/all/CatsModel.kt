package com.test.testcatsapp.ui.cats.all

import com.test.testcatsapp.common.mappers.toDbEntities
import com.test.testcatsapp.data.entity.Cat
import com.test.testcatsapp.repository.CatsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CatsModel
@Inject
constructor(
    private val catsRepository: CatsRepository
) : Cats.Model {

    override fun cats(): Single<List<Cat>> =
        catsRepository
            .getCatsFromApi()
            .map { apiCats -> apiCats.toDbEntities() }

    override fun setCatFavorite(cat: Cat): Completable =
        Completable.fromAction { catsRepository.saveFavoriteCat(cat) }
}
