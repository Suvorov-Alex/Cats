package com.test.testcatsapp.ui.cats.all

import com.test.testcatsapp.common.extensions.addTo
import com.test.testcatsapp.common.extensions.applyUiSchedulers
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.disposables.CompositeDisposable

class CatsPresenter(
    private val model: Cats.Model
) : Cats.Presenter {
    private val disposable = CompositeDisposable()

    private var view: Cats.View? = null

    override fun bind(view: Cats.View) {
        this.view = view

        model.cats()
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
        model.setCatFavorite(cat)
            .applyUiSchedulers()
            .subscribe({ view?.showCatSavedToast() }, Throwable::printStackTrace)
            .addTo(disposable)
    }

    override fun onRefresh() {
        model.cats()
            .applyUiSchedulers()
            .doOnTerminate { view?.stopRefreshing() }
            .subscribe(::onCatsLoaded, ::onCatsLoadingError)
            .addTo(disposable)
    }

    private fun onCatsLoaded(cats: List<Cat>) {
        view?.hideProgress()
        if (cats.isEmpty()) {
            view?.showEmptyView()
        } else {
            view?.showCats(cats)
        }
    }

    private fun onCatsLoadingError(error: Throwable) {
        error.printStackTrace()
        view?.hideProgress()
        view?.showError()
    }
}