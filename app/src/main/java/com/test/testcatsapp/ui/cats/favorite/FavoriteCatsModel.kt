package com.test.testcatsapp.ui.cats.favorite

import com.test.testcatsapp.data.entity.Cat
import com.test.testcatsapp.repository.CatsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavoriteCatsModel
@Inject
constructor(
    private val catsRepository: CatsRepository
) : FavoriteCats.Model {

    override fun getCatsFromDb(): Observable<List<Cat>> =
        catsRepository
            .getCatsFromDb()

    override fun deleteCatFromFavorite(cat: Cat): Completable =
        Completable.fromAction { catsRepository.deleteFavoriteCat(cat) }

    override fun saveFavoriteCat(cat: Cat): Completable =
        Completable.fromAction { catsRepository.saveFavoriteCat(cat) }
}