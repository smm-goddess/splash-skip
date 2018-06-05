package mm.dd.tools.accessibility.db

data class SkipEntity(var id: Long,
                      var packageName: String,
                      var appName: String,
                      var target: String,
                      var type: Long,
                      var enable: Boolean)