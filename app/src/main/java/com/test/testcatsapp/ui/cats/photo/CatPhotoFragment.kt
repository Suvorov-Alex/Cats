package com.test.testcatsapp.ui.cats.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.testcatsapp.R
import kotlinx.android.synthetic.main.fragment_cat_photo.*

class CatPhotoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cat_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        catPhotoTv.text = arguments?.getString("photoUrl")
    }
}