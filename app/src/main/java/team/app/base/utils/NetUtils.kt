package team.app.base.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * @return true if network conntected, false otherwise.
 */
fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    if (networkInfo != null && networkInfo.isConnected) {
        return true
    }
    return false
}