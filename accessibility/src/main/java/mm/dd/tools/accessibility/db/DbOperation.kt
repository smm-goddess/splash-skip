package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.copy2File
import mm.dd.tools.accessibility.ext.database
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.select


fun ensureDbExist(ctx: Context, name: String) {
    val skipInfoDb = ctx.getDatabasePath(name)
    if (!skipInfoDb.exists()) {
        skipInfoDb.parentFile.mkdirs()
        skipInfoDb.createNewFile()
        ctx.assets.copy2File(name, skipInfoDb)
    }
}

fun Context.enabledPackages(): List<SkipEntity> {
    return skipPackages().filter { it.enable!! }
}

fun Context.skipById(): List<SkipEntity> {
    return enableByType("1")
}

fun Context.skipByText(): List<SkipEntity> {
    return enableByType("2")
}

fun Context.enableByType(type: String): List<SkipEntity> {
    return database.use {
        return@use select("package_info")
                .whereArgs("(enable={enable}) and (type={type})",
                        "enable" to "1",
                        "type" to type)
                .parseList(ColumnEntityParser())
    }
}

fun Context.skipPackages(): List<SkipEntity> {
    return database.use {
        return@use select("package_info")
                .parseList(ColumnEntityParser())
    }
}

fun Context.unablePackages(): List<SkipEntity> {
    return skipPackages().filter { !it.enable!! }
}

class ColumnEntityParser : MapRowParser<SkipEntity> {
    override fun parseRow(columns: Map<String, Any?>): SkipEntity {
        return SkipEntity(
                id = columns["id"] as Long,
                packageName = columns["packageName"] as String,
                appName = columns["appName"] as String,
                target = columns["target"] as String,
                type = columns["type"] as Long,
                enable = columns["enable"] == 1L
        )
    }
}

