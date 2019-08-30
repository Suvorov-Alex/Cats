package com.test.testcatsapp.ui.cats.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.test.testcatsapp.R
import com.test.testcatsapp.data.entity.Cat
import com.test.testcatsapp.ui.cats.CatsAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_favorite_cats.*
import javax.inject.Inject

class FavoriteCatsFragment : DaggerFragment(), FavoriteCats.View {
    @Inject
    lateinit var presenter: FavoriteCats.Presenter

    @Inject
    lateinit var glide: RequestManager

    private val dataSource = mutableListOf<Cat>()
    private var catsAdapter: CatsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_favorite_cats, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)

        catsAdapter = CatsAdapter(glide, dataSource, presenter::onCatClicked, presenter::onCatLongClicked)
        favoriteCatsView.adapter = catsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.unbind()
    }

    override fun showProgress() {
        favoriteCatsView.isVisible = false
        favoriteCatsProgressView.isVisible = true
        favoriteCatsPlaceholderView.isVisible = false
    }

    override fun hideProgress() {
        favoriteCatsProgressView.isVisible = false
    }

    override fun showContent(cats: List<Cat>) {
        dataSource.clear()
        dataSource.addAll(cats)
        catsAdapter?.notifyDataSetChanged()

        favoriteCatsView.isVisible = true
        favoriteCatsProgressView.isVisible = false
        favoriteCatsPlaceholderView.isVisible = false
    }

    override fun showEmptyView() {
        favoriteCatsView.isVisible = false
        favoriteCatsProgressView.isVisible = false
        favoriteCatsPlaceholderView.isVisible = true
        favoriteCatsPlaceholderView.setTitle(R.string.favorite_cats_view_placeholder_empty)
    }

    override fun showError() {
        favoriteCatsView.isVisible = false
        favoriteCatsProgressView.isVisible = false
        favoriteCatsPlaceholderView.isVisible = true
        favoriteCatsPlaceholderView.setTitle(R.string.cats_view_placeholder_error)
    }

    override fun showFullImage(photoUrl: String) {
        if (findNavController().currentDestination?.id == R.id.favoriteCatsFragment) {
            val photoBundle = Bundle().apply { putString("photoUrl", photoUrl) }
            findNavController().navigate(R.id.action_from_favorite_cats_to_favorite_cat_photo, photoBundle)
        }
    }

    override fun showUndoDeleteCatSnackbar(cat: Cat) =
        Snackbar
            .make(requireView(), R.string.favorite_cats_delete_cat, Snackbar.LENGTH_LONG)
            .setAction(R.string.favorite_cats_delete_cat_undo) { presenter.onUndoDeleteCatClicked(cat) }
            .show()
}