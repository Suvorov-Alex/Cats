package com.test.testcatsapp.ui.cats.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.RequestManager
import com.test.testcatsapp.R
import com.test.testcatsapp.ui.cats.all.CatsFragment.Companion.PHOTO_URL_BUNDLE_KEY
import com.test.testcatsapp.ui.cats.photo.CatPhoto.View.DownloadPhotoResult
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_cat_photo.*
import javax.inject.Inject

class CatPhotoFragment : DaggerFragment(), CatPhoto.View {
    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var presenter: CatPhoto.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_cat_photo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)

        val photo = arguments?.getString(PHOTO_URL_BUNDLE_KEY)
        glide.load(photo).into(catPhotoView)

        catPhotoDownloadView.setOnClickListener { presenter.onDownloadViewClicked(catPhotoView.drawable) }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showToast(result: DownloadPhotoResult) {
        val text = when (result) {
            DownloadPhotoResult.PERMISSION_ERROR -> R.string.cat_photo_permission_error
            DownloadPhotoResult.ERROR -> R.string.cat_photo_download_error
            DownloadPhotoResult.SUCCESS -> R.string.cat_photo_download_success
        }
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun requestPermissionsForResult(permissions: Array<String>, requestCode: Int) =
        ActivityCompat.requestPermissions(requireActivity(), permissions, requestCode)
}