package mm.dd.tools.accessibility

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import mm.dd.tools.accessibility.db.enabledPackages
import mm.dd.tools.accessibility.ext.loge

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enabledPackages().map {
//            loge(it.toString())
//        }
    }
}