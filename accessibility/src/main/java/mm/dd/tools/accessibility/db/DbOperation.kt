package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.copy2File


fun copyDbFromAssetsToDatabaseIfNecessary(ctx: Context, name: String) {
    val dbPath = ctx.getDatabasePath(name)
    if (!dbPath.exists()) {
        dbPath.parentFile.mkdirs()
        dbPath.createNewFile()
        ctx.assets.copy2File(name, dbPath)
    }
}

