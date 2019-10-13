package com.hoang.deeplinkdemo

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_bg.setOnClickListener {
           openEmailApp()
        }
    }

    private fun openEmailApp() {
        val emailAppLauncherIntents = ArrayList<Intent>()

        //Intent that only email apps can handle:
        val emailAppIntent = Intent(Intent.ACTION_SENDTO)
        emailAppIntent.data = Uri.parse("mailto:")
        emailAppIntent.putExtra(Intent.EXTRA_EMAIL, "")
        emailAppIntent.putExtra(Intent.EXTRA_SUBJECT, "")

        val packageManager = packageManager

        //All installed apps that can handle email intent:
        val emailApps = packageManager.queryIntentActivities(emailAppIntent, PackageManager.MATCH_ALL)

        for (resolveInfo in emailApps) {
            val packageName = resolveInfo.activityInfo.packageName
            val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
            emailAppLauncherIntents.add(launchIntent)
        }

        //Create chooser
        val chooserIntent = Intent.createChooser(Intent(), "Select email app:")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, emailAppLauncherIntents.toTypedArray())
        startActivity(chooserIntent)
    }
}
