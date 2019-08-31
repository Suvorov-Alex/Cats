package com.test.testcatsapp.ui.cats.photo

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.test.testcatsapp.R
import java.io.File

class CatPhotoPresenter(
    private val context: Context,
    private val model: CatPhoto.Model
) : CatPhoto.Presenter {

    private var view: CatPhoto.View? = null

    private var fullPhoto: Drawable? = null

    override fun bind(view: CatPhoto.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun onDownloadViewClicked(drawable: Drawable?) {
        fullPhoto = drawable
        if (drawable != null) {
            if (ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
                model.savePhoto(drawable).onPhotoSavingStatusReceived()
            } else {
                onPermissionNotGranted()
            }
        } else {
            view?.showToast(R.string.cat_photo_download_error)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                fullPhoto?.let { model.savePhoto(it).onPhotoSavingStatusReceived() }
            } else {
                view?.showToast(R.string.cat_photo_permission_error)
            }
        }
    }

    private fun CatPhoto.SavingStatus.onPhotoSavingStatusReceived() =
        when (this) {
            is CatPhoto.SavingStatus.Success -> {
                Log.d(TAG, "Cat photo saved successfully")
                photo.showInGallery()
                view?.showToast(R.string.cat_photo_download_success)
            }
            is CatPhoto.SavingStatus.Error -> {
                Log.d(TAG, error.message)
                view?.showToast(R.string.cat_photo_download_error)
            }
        }

    private fun File.showInGallery() =
        context.sendBroadcast(
            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
                data = Uri.fromFile(File(this@showInGallery.absolutePath))
            }
        )

    private fun onPermissionNotGranted() =
        view?.requestPermissionsForResult(arrayOf(WRITE_EXTERNAL_STORAGE), PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE)

    companion object {
        private const val TAG = "CatPhotoPresenter"
        private const val PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE = 1
    }
}