package team.app.base.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import team.app.base.Constant

/**
 * Ref: http://thetechnocafe.com/how-to-use-room-in-android-all-you-need-to-know-to-get-started/
 * Ref: https://medium.com/mindorks/room-kotlin-android-architecture-components-71cad5a1bb35
 */

/*
@Database(entities = [], version = Constant.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                Constant.DATABASE_NAME
            )
                .build()
        }
    }
}*/
