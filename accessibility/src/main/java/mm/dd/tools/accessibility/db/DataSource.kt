package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.getPreference
import mm.dd.tools.accessibility.ext.installedPackages
import org.jetbrains.anko.doAsync

object DataSource {
    private var packages: List<SkipEntity>? = null
    private var adaptedPackages: List<SkipEntity>? = null
    private var dataSourceReady = false
    fun initSource(ctx: Context) {
        doAsync {
            packages = ctx.installedPackages(ctx.getPreference("withoutSystem", true))
            adaptedPackages = ctx.skipPackages()
            packages!!.map { skipEntity ->
                val adapted = adaptedPackages!!.firstOrNull { it.packageName == skipEntity.packageName }
                if (adapted != null) {
                    skipEntity.run {
                        id = adapted.id
                        target = adapted.target
                        type = adapted.type
                        enable = adapted.enable
                    }
                }
            }
            dataSourceReady = true
        }
    }

    fun reload(ctx: Context) {
        dataSourceReady = false
        initSource(ctx)
    }

    fun isDataReady() = dataSourceReady

    fun adapted(): List<SkipEntity>? {
        return packages?.filter { it.id != null }
    }

    fun unadapted(): List<SkipEntity>? {
        return packages?.filter { it.id == null }
    }

}