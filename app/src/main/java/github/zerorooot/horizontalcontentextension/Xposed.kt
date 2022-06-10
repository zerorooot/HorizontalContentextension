package github.zerorooot.horizontalcontentextension

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class Xposed : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.miui.contentextension") {
           return
        }
        val clazz = XposedHelpers.findClass(
            "com.miui.contentextension.services.TextContentExtensionService",
            lpparam.classLoader
        )
        val method = clazz.getDeclaredMethod("isScreenPortrait")
        method.isAccessible = true
        XposedBridge.hookMethod(method, object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                param.result = true
            }
        })

    }
}