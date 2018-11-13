package mm.dd.tools.accessibility.ext

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.res.AssetManager
import android.support.v4.app.Fragment
import android.util.Log
import mm.dd.tools.accessibility.db.SkipEntity
import java.io.File

fun AssetManager.copy2File(name: String, file: File) {
    file.writeBytes(open(name).readBytes())
}

fun Context.loge(s: String) {
    Log.e(this::class.java.simpleName, s)
}

fun Fragment.loge(s: String) {
    this.activity?.loge(s)
}

val Context.sharedPreference: SharedPreferences
    get() = getSharedPreferences("splash_skip", Context.MODE_PRIVATE)


@Suppress("UNCHECKED_CAST")
fun <T> Context.getPreference(key: String, default: T): T = with(sharedPreference) {
    val res: Any = when (default) {
        is String -> getString(key, default)
        is Long -> getLong(key, default)
        is Int -> getInt(key, default)
        is Boolean -> getBoolean(key, default)
        is Float -> getFloat(key, default)
        else -> throw IllegalArgumentException("This type of data cannot be get!")
    }
    return res as T
}


fun <T> Context.savePreference(key: String, value: T) = with(sharedPreference.edit()) {
    when (value) {
        is String -> putString(key, value)
        is Long -> putLong(key, value)
        is Int -> putInt(key, value)
        is Boolean -> putBoolean(key, value)
        is Float -> putFloat(key, value)
        else -> throw IllegalArgumentException("This type of data cannot be get!")
    }.apply()
}

fun Context.installedPackages(withoutSystem: Boolean): List<SkipEntity> {
    return packageManager.getInstalledPackages(0)
            .asSequence()
            .filter {
                if (withoutSystem) {
                    it.applicationInfo.flags.and(ApplicationInfo.FLAG_SYSTEM) != 0
                }
                true
            }.map { packageInfo ->
                val icon = packageInfo.applicationInfo.loadIcon(packageManager)
                val appName = packageInfo.applicationInfo.loadLabel(packageManager)
                SkipEntity(
                        packageName = packageInfo.packageName,
                        appName = appName as String,
                        appIcon = icon
                )
            }
            .toList()
}