package mm.dd.tools.accessibility

import android.app.Application
import mm.dd.tools.accessibility.db.ensureDbExist

class AccessibilityApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ensureDbExist(this, "skip_info.db")
    }

}