package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.copy2File


fun ensureDbExist(ctx: Context, name: String) {
    val skipInfoDb = ctx.getDatabasePath(name)
    if (!skipInfoDb.exists()) {
        skipInfoDb.parentFile.mkdirs()
        skipInfoDb.createNewFile()
        ctx.assets.copy2File(name, skipInfoDb)
    }
}

