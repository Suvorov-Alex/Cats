package com.test.testcatsapp.ui.cats.all

import androidx.annotation.StringRes
import com.test.testcatsapp.data.entity.Cat
import io.reactivex.Completable
import io.reactivex.Single

interface Cats {
    interface Model {
        fun cats(): Single<List<Cat>>
        fun setCatFavorite(cat: Cat): Completable
    }

    interface View {
        fun showProgress()
        fun hideProgress()

        fun showCats(cats: List<Cat>)
        fun showEmptyView()
        fun showError()
        fun showFullImage(photoUrl: String)

        fun showCatSavedToast()

        fun stopRefreshing()
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()

        fun onCatClicked(cat: Cat)
        fun onCatLongClicked(cat: Cat)
        fun onRefresh()
    }
}