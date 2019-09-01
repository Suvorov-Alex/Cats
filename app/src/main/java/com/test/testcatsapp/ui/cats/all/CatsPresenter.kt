package com.test.testcatsapp.ui.cats.all

import android.annotation.SuppressLint
import android.util.Log
import com.test.testcatsapp.R
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

class CatsPresenter(
    private val model: Cats.Model
) : Cats.Presenter {
    private val disposable = CompositeDisposable()

    private var view: Cats.View? = null

    override fun bind(view: Cats.View) {
        this.view = view

        model
            .cats()
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

    override fun onCatClicked(cat: Cat) {
        view?.showFullImage(cat.imageUrl)
    }

    @SuppressLint("CheckResult")
    override fun onCatLongClicked(cat: Cat) {
        model
            .setCatFavorite(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view?.showToast(R.string.cats_view_save_to_favorite_toast) },
                { error -> Log.d(TAG, error.message) }
            )
    }

    override fun onRefresh() {
        model
            .cats()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { cats ->
                    view?.stopRefreshing()
                    onDataLoaded(cats)
                },
                { error ->
                    view?.stopRefreshing()
                    onErrorReceived(error)
                }
            )
            .addTo(disposable)
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
        private const val TAG = "CatsPresenter"
    }
}