package com.test.testcatsapp.ui.cats.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.RequestManager
import com.test.testcatsapp.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_cat_photo.*
import javax.inject.Inject

class CatPhotoFragment : DaggerFragment(), CatPhoto.View {
    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var presenter: CatPhoto.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_cat_photo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)

        val photo = arguments?.getString("photoUrl")
        glide.load(photo).into(catPhotoView)

        catPhotoDownloadView.setOnClickListener { presenter.onDownloadViewClicked(catPhotoView.drawable) }
    }

    override fun showToast(message: Int) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    override fun requestPermissionsForResult(permissions: Array<String>, requestCode: Int) =
        ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
}