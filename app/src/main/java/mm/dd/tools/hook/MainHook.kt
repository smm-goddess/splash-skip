package mm.dd.tools.hook

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    val ALI_APPS = listOf("com.eg.android.AlipayGphone")
    val TAG = "DISABLE_ALI_DETECT"

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        val packageName = lpparam?.packageName
        if (lpparam != null && packageName in ALI_APPS) {
            XposedBridge.log("$TAG:$packageName")
            XposedHelpers.findAndHookMethod("java.lang.ClassLoader",
                    lpparam.classLoader, "loadClass", String::class.java,
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam?) {
                            val className = param?.args?.get(0) as String
                            XposedBridge.log("$TAG:loadClass:$className")
                            if (className.startsWith("de.robv.android.xposed"))
                                param.args[0] = "a.b.c.d.e.f"
                            super.beforeHookedMethod(param)
                        }
                    })

        }
    }
}