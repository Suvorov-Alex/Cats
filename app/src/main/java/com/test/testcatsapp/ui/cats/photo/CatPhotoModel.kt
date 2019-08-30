package com.test.testcatsapp.ui.cats.photo

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream

class CatPhotoModel : CatPhoto.Model {
    override fun savePhoto(photo: Drawable): CatPhoto.SavingStatus {
        val imageFileName = "Cat" + System.currentTimeMillis() + ".jpg"
        val storageFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())
        var isStorageFileExists = true
        if (!storageFile.exists()) {
            isStorageFileExists = storageFile.mkdirs()
        }
        if (isStorageFileExists) {
            val imageFile = File(storageFile, imageFileName)
            try {
                FileOutputStream(imageFile).use { photo.toBitmap().compress(Bitmap.CompressFormat.JPEG, 100, it) }
            } catch (e: Throwable) {
                return CatPhoto.SavingStatus.Error(e)
            }
            return CatPhoto.SavingStatus.Success(imageFile)
        }
        return CatPhoto.SavingStatus.Error(Exception("Storage file doesn't exists"))
    }
}