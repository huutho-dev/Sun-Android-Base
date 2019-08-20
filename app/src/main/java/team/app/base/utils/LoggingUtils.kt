package team.app.base.utils

import android.util.Log

fun Any.verbose(message : () -> Any?) {
    val tag = getTag(javaClass)
    Log.v(tag, message.toString())
}

fun Any.info(message : () -> Any?) {
    val tag = getTag(javaClass)
    Log.i(tag, message.toString())
}

fun Any.error(message : () -> Any?) {
    val tag = getTag(javaClass)
    Log.e(tag, message.toString() )
}

private fun getTag(clazz: Class<*>): String {
    val className = clazz.simpleName
    return if (className.length <= 23) {
        className
    } else {
        className.substring(0, 23)
    }
}