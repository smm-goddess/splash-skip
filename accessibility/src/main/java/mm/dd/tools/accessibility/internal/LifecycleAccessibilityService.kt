package mm.dd.tools.accessibility.internal

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleService
import android.arch.lifecycle.ServiceLifecycleDispatcher
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.annotation.CallSuper
import android.view.accessibility.AccessibilityEvent

@SuppressLint("Registered")
open class LifecycleAccessibilityService : AccessibilityService(), LifecycleOwner {

    override fun onInterrupt() {
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    private val mDispatcher = ServiceLifecycleDispatcher(this)

    @CallSuper
    override fun onCreate() {
        mDispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    @CallSuper
    override fun onServiceConnected() {
        mDispatcher.onServicePreSuperOnCreate()
        mDispatcher.onServicePreSuperOnStart()
        super.onServiceConnected()
    }

    @CallSuper
    override fun onStart(intent: Intent, startId: Int) {
        super.onStart(intent, startId)
    }

    // this method is added only to annotate it with @CallSuper.
    // In usual service super.onStartCommand is no-op, but in LifecycleService
    // it results in mDispatcher.onServicePreSuperOnStart() call, because
    // super.onStartCommand calls onStart().
    @CallSuper
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @CallSuper
    override fun onDestroy() {
        mDispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        return mDispatcher.lifecycle
    }

}