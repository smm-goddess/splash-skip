package mm.dd.tools.accessibility.db

import android.graphics.drawable.Drawable

data class SkipEntity(var id: Long? = null,
                      var packageName: String,
                      var appName: String,
                      var target: String? = null,
                      var type: Long? = null,
                      var enable: Boolean? = null,
                      var version: String? = null,
                      var icon: Drawable? = null)