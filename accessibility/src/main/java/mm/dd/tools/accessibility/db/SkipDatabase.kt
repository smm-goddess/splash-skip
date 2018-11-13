package mm.dd.tools.accessibility.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Room
import android.content.Context


@Database(entities = [SkipEntity::class], version = 1, exportSchema = false)
abstract class SkipDatabase : RoomDatabase() {
    abstract fun skipDao(): SkipDao
}

object SkipDatabaseObject {

    lateinit var skipDatabase: SkipDatabase

    fun initDB(ctx: Context) {
        skipDatabase = Room.databaseBuilder(
                ctx.applicationContext,
                SkipDatabase::class.java,
                "skip_info.db"
        ).build()
    }
}

var skipDao = SkipDatabaseObject.skipDatabase.skipDao()