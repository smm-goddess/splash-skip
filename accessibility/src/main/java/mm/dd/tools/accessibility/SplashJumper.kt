package mm.dd.tools.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class SplashJumper : AccessibilityService() {
    private val targetIdMap = mapOf(
            "com.nfyg.hsbb" to listOf("com.nfyg.hsbb:id/start_skip_count_down"),
            "com.taobao.movie.android" to listOf("com.taobao.movie.android:id/splash_timer"),
            "com.sinovatech.unicom.ui" to listOf("com.sinovatech.unicom.ui:id/close"),
            "com.sankuai.movie" to listOf("com.sankuai.movie:id/jf"),
            "com.sunrise.scmbhc" to listOf("com.sunrise.scmbhc:id/pop_image_close"),
            "com.xiaomi.hm.health" to listOf("com.xiaomi.hm.health:id/skip_text", "com.xiaomi.hm.health:id/ad_close"),
            "com.miui.systemAdSolution" to listOf("com.miui.systemAdSolution:id/skip"),
            "com.jd.jrapp" to listOf("com.jd.jrapp:id/btn_jump"),
            "com.douguo.recipe" to listOf("com.douguo.recipe:id/ad_jump"),
            "com.taou.maimai" to listOf("com.taou.maimai:id/skip_btnEx"),
            "com.youdao.dict" to listOf("com.youdao.dict:id/skin_text")
    )

    private val targetTextMap = mapOf(
            "com.zhihu.android" to "跳过广告"
    )

    override fun onInterrupt() {
        Log.e("event", "interrupted")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event != null && event.source != null) {
            val targetIds = targetIdMap[event.packageName]
            if (targetIds != null)
                tryFindIdAndClick(event.source, targetIds)
            else {
                val targetText = targetTextMap[event.packageName]
                if (targetText != null) {
                    tryFindTextAndClick(event.source, targetText)
                }
            }
        }
    }

    private fun tryFindTextAndClick(parent: AccessibilityNodeInfo, targetText: String) {
        parent.findAccessibilityNodeInfosByText(targetText)
                .firstOrNull()
                .apply {
                    if (this != null) {
                        performAction(AccessibilityNodeInfo.ACTION_CLICK)
                        return
                    }
                }
    }

    private fun tryFindIdAndClick(parent: AccessibilityNodeInfo, targetIds: List<String>) {
        targetIds.map {
            parent.findAccessibilityNodeInfosByViewId(it)
                    .firstOrNull()
                    .apply {
                        if (this != null) {
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            return
                        }
                    }
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val info = AccessibilityServiceInfo()

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100
        info.packageNames = targetIdMap.keys.plus(targetTextMap.keys).toTypedArray()
        serviceInfo = info
    }

}