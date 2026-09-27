package com.hackathon.gametracker

import android.app.Application
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // 🐛 Helpful for debugging in Android Studio Logcat
        Purchases.logLevel = LogLevel.DEBUG
        
        // 🚀 Initialize RevenueCat with your specific API Key
        Purchases.configure(
            PurchasesConfiguration.Builder(this, "test_UxkGCMqzBgMFJKxpNRmiUlrFfGC").build()
        )
    }
}