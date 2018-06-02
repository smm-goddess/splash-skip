package mm.dd.tools.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class SplashJumper : AccessibilityService() {
    val targetIdMap = mapOf(
            "com.nfyg.hsbb" to listOf("com.nfyg.hsbb:id/start_skip_count_down"),
            "com.taobao.movie.android" to listOf("com.taobao.movie.android:id/splash_timer"),
            "com.sinovatech.unicom.ui" to listOf("com.sinovatech.unicom.ui:id/close"),
            "com.sankuai.movie" to listOf("com.sankuai.movie:id/jf")
    )

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.source != null) {
            val targetIds = targetIdMap[event.packageName]
            if (targetIds != null)
                tryFindAndClick(event.source, targetIds)
        }
    }

    private fun tryFindAndClick(parent: AccessibilityNodeInfo, targetIds: List<String>) {
        targetIds.map {
            parent.findAccessibilityNodeInfosByViewId(it)
                    .firstOrNull()
                    ?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100
        info.packageNames = targetIdMap.keys.toTypedArray()
        serviceInfo = info
    }

}