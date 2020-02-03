package com.test.testcatsapp.ui.cats.favorite

import com.test.testcatsapp.data.entity.Cat
import com.test.testcatsapp.repository.CatsRepository
import javax.inject.Inject

class FavoriteCatsModel @Inject constructor(
    private val catsRepository: CatsRepository
) : FavoriteCats.Model {

    override fun catsFromDb() =
        catsRepository.fromDb()

    override fun deleteCatFromFavorite(cat: Cat) =
        catsRepository.deleteFavorite(cat)

    override fun saveFavoriteCat(cat: Cat) =
        catsRepository.saveFavorite(cat)
}