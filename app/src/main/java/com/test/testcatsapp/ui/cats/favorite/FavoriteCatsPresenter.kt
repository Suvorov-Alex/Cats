package com.test.testcatsapp.ui.cats.favorite

import android.util.Log
import com.test.testcatsapp.common.extensions.addTo
import com.test.testcatsapp.common.extensions.applyUiSchedulers
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.disposables.CompositeDisposable

class FavoriteCatsPresenter(
    private val model: FavoriteCats.Model
) : FavoriteCats.Presenter {
    private val disposable = CompositeDisposable()

    private var view: FavoriteCats.View? = null

    override fun bind(view: FavoriteCats.View) {
        this.view = view

        model.catsFromDb()
            .doOnSubscribe { view.showProgress() }
            .applyUiSchedulers()
            .subscribe(::onCatsLoaded, ::onCatsLoadingError)
            .addTo(disposable)
    }

    override fun unbind() {
        disposable.clear()
    }

    override fun onCatClicked(cat: Cat) {
        view?.showFullImage(cat.imageUrl)
    }

    override fun onCatLongClicked(cat: Cat) {
        model.deleteCatFromFavorite(cat)
            .applyUiSchedulers()
            .subscribe({ view?.showUndoDeleteCatSnackbar(cat) }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    override fun onUndoDeleteCatClicked(cat: Cat) {
        model.saveFavoriteCat(cat)
            .applyUiSchedulers()
            .subscribe({ Log.d(TAG, "Cat $cat successfully deleted and added") }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    private fun onCatsLoaded(cats: List<Cat>) {
        view?.hideProgress()
        if (cats.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent(cats)
        }
    }

    private fun onCatsLoadingError(error: Throwable) {
        error.printStackTrace()
        view?.hideProgress()
        view?.showError()
    }

    companion object {
        private const val TAG = "FavoriteCatsPresenter"
    }
}