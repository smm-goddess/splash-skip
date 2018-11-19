package mm.dd.tools.accessibility

import android.arch.lifecycle.Observer
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import mm.dd.tools.accessibility.db.SkipDatabaseObject
import mm.dd.tools.accessibility.db.SkipEntity
import mm.dd.tools.accessibility.db.skipDao
import mm.dd.tools.accessibility.internal.LifecycleAccessibilityService


class SplashJumper : LifecycleAccessibilityService() {

    //packageName:List<Pair<type,String>>
    private var targetMap: Map<String, List<Pair<Int, String>>> = emptyMap()

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.source != null) {
            try {
                targetMap[event.packageName]
                        ?.firstOrNull { pair ->
                            when (pair.first) {
                                0 -> return@firstOrNull tryFindTextAndClick(event.source, pair.second)
                                1 -> return@firstOrNull tryFindIdAndClick(event.source, pair.second)
                                else -> return@firstOrNull false
                            }
                        }
            } catch (e: Exception) {

            }
        }
    }

    private fun tryFindTextAndClick(parent: AccessibilityNodeInfo, targetText: String): Boolean {
        return parent.findAccessibilityNodeInfosByText(targetText)
                .firstOrNull()
                .run {
                    if (this != null) {
                        performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        true
                    } else {
                        false
                    }
                }
    }

    private fun tryFindIdAndClick(parent: AccessibilityNodeInfo, targetIds: String): Boolean {
        return parent.findAccessibilityNodeInfosByViewId(targetIds)
                .firstOrNull()
                .run {
                    if (this != null) {
                        performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        true
                    } else {
                        false
                    }
                }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        SkipDatabaseObject.initDB(this)
        skipDao.getAllUsableRules().observe(this, Observer<List<SkipEntity>> { skipEntityList ->
            targetMap = skipEntityList?.groupBy { entity -> entity.packageName }
                    ?.mapValues { entry -> entry.value.map { it.type to it.target } }
                    ?: emptyMap()
        })
    }

}