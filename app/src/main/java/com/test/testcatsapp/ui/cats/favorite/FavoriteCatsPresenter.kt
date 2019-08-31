package com.test.testcatsapp.ui.cats.favorite

import android.annotation.SuppressLint
import android.util.Log
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class FavoriteCatsPresenter(
    private val model: FavoriteCats.Model
) : FavoriteCats.Presenter {
    private val disposable = CompositeDisposable()

    private var view: FavoriteCats.View? = null

    override fun bind(view: FavoriteCats.View) {
        this.view = view

        model
            .getCatsFromDb()
            .doOnSubscribe { view.showProgress() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cats -> onDataLoaded(cats) },
                { error -> onErrorReceived(error) }
            )
            .addTo(disposable)
    }

    override fun unbind() {
        disposable.clear()
    }

    @SuppressLint("CheckResult")
    override fun onCatClicked(cat: Cat) {
        view?.showFullImage(cat.imageUrl)
    }

    override fun onCatLongClicked(cat: Cat) {
        model
            .deleteCatFromFavorite(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view?.showUndoDeleteCatSnackbar(cat) },
                { error -> Log.d(TAG, error.message) }
            )
    }

    @SuppressLint("CheckResult")
    override fun onUndoDeleteCatClicked(cat: Cat) {
        model
            .saveFavoriteCat(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d(TAG, "Cat $cat successfully deleted and added") },
                { error -> Log.d(TAG, error.message) }
            )
    }

    private fun onDataLoaded(cats: List<Cat>) {
        view?.hideProgress()
        if (cats.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showContent(cats)
        }
    }

    private fun onErrorReceived(error: Throwable) {
        Log.d(TAG, error.message)
        view?.hideProgress()
        view?.showError()
    }

    companion object {
        private const val TAG = "FavoriteCatsModel"
    }
}