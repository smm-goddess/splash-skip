package mm.dd.tools.accessibility.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.graphics.drawable.Drawable

@Entity(tableName = "package_info")
data class SkipEntity(
        @PrimaryKey var id: Long = 0,
        @ColumnInfo(name = "packageName") var packageName: String = "",
        @ColumnInfo(name = "appName") var appName: String = "",
        @ColumnInfo(name = "target") var target: String = "",
        @ColumnInfo(name = "type") var type: Int = 0, // type:1 为id, type:0 为字符串
        @ColumnInfo(name = "enable") var enable: Boolean = true,
        @Ignore var appIcon: Drawable? = null
)