package team.app.base.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AppSharePreference(private val context: Context, private val gson: Gson) {

    private val mSharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("SharedPreferencesCore", Context.MODE_PRIVATE)
    }

    operator fun <T> get(key: String, anonymousClass: Class<T>): T? {
        return when (anonymousClass) {
            String::class.java -> mSharedPreferences.getString(key, "") as T
            Boolean::class.java -> java.lang.Boolean.valueOf(mSharedPreferences.getBoolean(key, false)) as T
            Float::class.java -> java.lang.Float.valueOf(mSharedPreferences.getFloat(key, -1f)) as T
            Int::class.java -> Integer.valueOf(mSharedPreferences.getInt(key, -1)) as T
            Long::class.java -> java.lang.Long.valueOf(mSharedPreferences.getLong(key, -1)) as T
            else -> gson.fromJson(mSharedPreferences.getString(key, ""), anonymousClass)
        }
    }

    fun <T> put(key: String, data: T) {
        val editor = mSharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data as String)
            is Boolean -> editor.putBoolean(key, data as Boolean)
            is Float -> editor.putFloat(key, data as Float)
            is Int -> editor.putInt(key, data as Int)
            is Long -> editor.putLong(key, data as Long)
            else -> editor.putString(key, gson.toJson(data))
        }
        editor.apply()
    }

    fun clear() {
        mSharedPreferences.edit().clear().apply()
    }
}