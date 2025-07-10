package id.derysudrajat.recepify.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import java.util.UUID

object LocalProviderUtils {
    @Throws(IOException::class)
    suspend fun copyToInternalStorage(application: Context, uri: Uri): File =
        withContext(Dispatchers.IO) {
            val uuid = UUID.randomUUID()
            val file = File(application.cacheDir, "temp_file_$uuid")
            application.contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return@withContext file
        }

    fun getBitmap(file: File): Bitmap {
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}