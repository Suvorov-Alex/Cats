package com.test.testcatsapp.ui.cats.photo

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import com.test.testcatsapp.ui.cats.photo.CatPhoto.View.DownloadPhotoResult

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
                handleSavingStatus(model.savePhoto(drawable))
            } else {
                onPermissionNotGranted()
            }
        } else {
            view?.showToast(DownloadPhotoResult.ERROR)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                fullPhoto?.let { handleSavingStatus(model.savePhoto(it)) }
            } else {
                view?.showToast(DownloadPhotoResult.PERMISSION_ERROR)
            }
        }
    }

    private fun handleSavingStatus(status: CatPhoto.SavingStatus) =
        when (status) {
            is CatPhoto.SavingStatus.Success -> {
                showInGallery(status.photo)
                view?.showToast(DownloadPhotoResult.SUCCESS)
            }
            is CatPhoto.SavingStatus.Error -> {
                status.error.printStackTrace()
                view?.showToast(DownloadPhotoResult.ERROR)
            }
        }

    private fun showInGallery(photo: File) =
        context.sendBroadcast(
            Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
                data = Uri.fromFile(File(photo.absolutePath))
            }
        )

    private fun onPermissionNotGranted() =
        view?.requestPermissionsForResult(arrayOf(WRITE_EXTERNAL_STORAGE), PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE)

    companion object {
        private const val PERMISSIONS_WRITE_EXTERNAL_STORAGE_CODE = 1
    }
}