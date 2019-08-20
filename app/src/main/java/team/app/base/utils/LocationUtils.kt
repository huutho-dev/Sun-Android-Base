package team.app.base.utils

import android.content.Context
import android.location.LocationManager


/**
 * @param context
 * *
 * @return true if gps enabled or location network service enabled.
 */
fun Context.isLocationServiceEnabled(): Boolean {
    val gps_enabled: Boolean
    val network_enabled: Boolean
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    try {
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        network_enabled = lm
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (ex: Exception) {
        ex.printStackTrace()
        return false
    }

    return gps_enabled || network_enabled
}

/**
 * @param context
 * *
 * @return true if gps enabled.
 */
fun Context.isGPSEnabled(): Boolean {
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    try {
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return false
}

/**
 * @param context
 * *
 * @return true if location service network enabled.
 */
fun Context.isLocationServiceNetworkEnabled(): Boolean {
    val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
    try {
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return false
}