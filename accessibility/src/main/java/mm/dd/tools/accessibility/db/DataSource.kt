package mm.dd.tools.accessibility.db

import android.content.Context
import mm.dd.tools.accessibility.ext.getPreference
import mm.dd.tools.accessibility.ext.installedPackages
import org.jetbrains.anko.doAsync

object DataSource {
    private lateinit var installedPackages: List<SkipEntity>
    private var dataSourceReady = false
    fun initSource(ctx: Context) {
        doAsync {
            installedPackages = ctx.installedPackages(ctx.getPreference("withoutSystem", true))
            dataSourceReady = true
        }
    }

}