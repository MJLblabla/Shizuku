package moe.shizuku.manager

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.topjohnwu.superuser.Shell
import moe.shizuku.manager.ktx.logd
import org.lsposed.hiddenapibypass.HiddenApiBypass
//import rikka.core.util.BuildUtils.atLeast30
//import rikka.material.app.LocaleDelegate

lateinit var application: Application

object ShizukuApplication {

    init {
        logd("ShizukuApplication", "init")

        Shell.setDefaultBuilder(Shell.Builder.create().setFlags(Shell.FLAG_REDIRECT_STDERR))
        if (Build.VERSION.SDK_INT >= 28) {
            HiddenApiBypass.setHiddenApiExemptions("")
        }
        if ( Build.VERSION.SDK_INT >= 30) {
            System.loadLibrary("adb")
        }
    }

    fun init(context: Application) {
        application = context
        ShizukuSettings.initialize(context)
       // LocaleDelegate.defaultLocale = ShizukuSettings.getLocale()
       // AppCompatDelegate.setDefaultNightMode(ShizukuSettings.getNightMode())
    }


}
