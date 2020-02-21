/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.app

import android.app.Application
import dev.icerock.yellowdoor.App

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        mppApplication = App()
            .apply { initialize() }
    }

    companion object {
        lateinit var mppApplication: App
    }
}
