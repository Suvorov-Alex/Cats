package com.test.testcatsapp.ui.cats.photo

import android.graphics.drawable.Drawable
import java.io.File

interface CatPhoto {
    interface Model {
        fun savePhoto(photo: Drawable): SavingStatus
    }

    interface View {
        enum class DownloadPhotoResult {
            PERMISSION_ERROR,
            ERROR,
            SUCCESS
        }

        fun showToast(result: DownloadPhotoResult)

        fun requestPermissionsForResult(permissions: Array<String>, requestCode: Int)
    }

    interface Presenter {
        fun bind(view: View)
        fun unbind()

        fun onDownloadViewClicked(drawable: Drawable?)

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    }

    sealed class SavingStatus {
        data class Error(val error: Throwable) : SavingStatus()
        data class Success(val photo: File) : SavingStatus()
    }
}