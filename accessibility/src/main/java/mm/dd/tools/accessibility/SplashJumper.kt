package mm.dd.tools.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import mm.dd.tools.accessibility.db.SkipEntity
import mm.dd.tools.accessibility.db.ensureDbExist
import mm.dd.tools.accessibility.db.skipById
import mm.dd.tools.accessibility.db.skipByText
import mm.dd.tools.accessibility.ext.loge

class SplashJumper : AccessibilityService() {
    private var targetIdMap = emptyMap<String, MutableList<String>>()

    private var targetTextMap = emptyMap<String, MutableList<String>>()

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

    private fun tryFindTextAndClick(parent: AccessibilityNodeInfo, targetText: List<String>) {
        targetText.map {
            parent.findAccessibilityNodeInfosByText(it)
                    .firstOrNull()
                    .apply {
                        if (this != null) {
                            performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            return
                        }
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

    private fun loadPackageInfos(source: List<SkipEntity>): Map<String, MutableList<String>> {
        return source
                .groupBy { it.packageName }
                .mapValues { entry ->
                    entry.value.fold(mutableListOf<String>()) { acc, entity ->
                        acc.add(entity.target)
                        acc
                    }
                }
    }

    private fun updateConfig() {
        val info = AccessibilityServiceInfo()

        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        info.notificationTimeout = 100
        targetIdMap = loadPackageInfos(skipById())
        targetTextMap = loadPackageInfos(skipByText())
        info.packageNames = targetIdMap.keys.plus(targetTextMap.keys).toTypedArray()
        serviceInfo = info
    }

    override fun onCreate() {
        super.onCreate()
        ensureDbExist(this, "skip_info.db")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        updateConfig()
    }

}