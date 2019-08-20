package team.app.base.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException


/**
 * Delete file regardless of file is dictionary (folder).

 * @param file
 */
fun delete(file: File?) {
    if (file == null)
        return
    if (file.isFile) {
        file.delete()
        return
    }
    val childs = file.listFiles() ?: return

    for (f in childs) {
        delete(f)
    }
    file.delete()
}

/**
 * Create temp file. If has external storage create in external else create
 * in internal.

 * @param context
 * *
 * @return
 * *
 * @throws IOException
 */
@Throws(IOException::class)
fun createTempFile(context: Context): File? {
    return if (!hasExternalStorage()) {
        createTempFile(context, context.cacheDir)
    } else {
        createTempFile(context, context.getExternalFilesDir("caches"))
    }
}

/**
 * Create temp file in folder

 * @param context
 * *
 * @param folder  where place temp file
 * *
 * @return
 * *
 * @throws IOException
 */
@Throws(IOException::class)
fun createTempFile(context: Context, folder: File?): File {
    val prefix = System.currentTimeMillis().toString()
    return File.createTempFile(prefix, null, folder)
}

/**
 * Check external exist or not.

 * @return
 */
fun hasExternalStorage(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}
