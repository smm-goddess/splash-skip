package mm.dd.tools.accessibility.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class SkipSQLiteHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "skip_info.db") {

    companion object {
        private var instance: SkipSQLiteHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SkipSQLiteHelper {
            if (instance == null) {
                instance = SkipSQLiteHelper(ctx)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}