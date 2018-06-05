package mm.dd.tools.accessibility.ext

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import mm.dd.tools.accessibility.db.SkipSQLiteHelper
import java.io.File

fun AssetManager.copy2File(name: String, file: File) {
    file.writeBytes(open(name).readBytes())
}

val Context.database: SkipSQLiteHelper
    get() = SkipSQLiteHelper.getInstance(this.applicationContext)

fun Context.loge(s: String) {
    Log.e(this::class.java.simpleName, s)
}