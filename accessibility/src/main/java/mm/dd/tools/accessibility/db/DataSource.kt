package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.getPreference
import mm.dd.tools.accessibility.ext.installedPackages
import org.jetbrains.anko.doAsync

object DataSource {
    private var installedPackages: List<SkipEntity>? = null
    private var dataSourceReady = false
    fun initSource(ctx: Context) {
        doAsync {
            installedPackages = ctx.installedPackages(ctx.getPreference("withoutSystem", true))
            dataSourceReady = true
        }
    }

    fun reload(ctx: Context) {
        dataSourceReady = false
        initSource(ctx)
    }

    fun isDataReady() = dataSourceReady

}