package mm.dd.tools.accessibility

import android.accessibilityservice.AccessibilityServiceInfo
import android.arch.lifecycle.Observer
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import mm.dd.tools.accessibility.db.SkipDatabaseObject
import mm.dd.tools.accessibility.db.SkipEntity
import mm.dd.tools.accessibility.ext.loge
import mm.dd.tools.accessibility.internal.LifecycleAccessibilityService


class SplashJumper : LifecycleAccessibilityService() {

    //packageName:List<Pair<type,String>>
    private var targetMap: Map<String, List<Pair<Int, String>>> = emptyMap()

    override fun onInterrupt() {
        loge("service on interrupt")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.source != null) {
            loge("packageName:${event.packageName},activityName:${event.className}")
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
        loge("className:${parent.className}")
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

    private fun updateConfig() {
        val info = AccessibilityServiceInfo()

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100
        info.packageNames = arrayOf("com.taobao.movie.android")
        serviceInfo = info
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        SkipDatabaseObject.initDB(this)
        SkipDatabaseObject.skipDatabase.skipDao().getAllUsableRules().observe(this, Observer<List<SkipEntity>> { skipEntityList ->
            targetMap = skipEntityList?.groupBy { entity -> entity.packageName }
                    ?.mapValues { entry -> entry.value.map { it.type to it.target } }
                    ?: emptyMap()
            loge("targetMap:$targetMap")
            updateConfig()
        })
    }

}