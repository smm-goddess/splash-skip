package mm.dd.tools.accessibility

import android.app.Application
import mm.dd.tools.accessibility.db.DataSource
import mm.dd.tools.accessibility.db.SkipDatabaseObject
import mm.dd.tools.accessibility.db.copyDbFromAssetsToDatabaseIfNecessary

class AccessibilityApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        copyDbFromAssetsToDatabaseIfNecessary(this, "skip_info.db")
        SkipDatabaseObject.initDB(this)
        DataSource.initSource(this)
    }

}