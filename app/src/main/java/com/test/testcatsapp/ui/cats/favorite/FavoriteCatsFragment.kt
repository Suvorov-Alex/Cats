package com.test.testcatsapp.ui.cats.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.testcatsapp.R
import kotlinx.android.synthetic.main.fragment_favorite_cats.*

class FavoriteCatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favCatTv.setOnClickListener {
            findNavController().navigate(
                R.id.actionFavoriteCatPhoto,
                Bundle().apply { putString("photoUrl", "URL1") }
            )
        }
    }

}