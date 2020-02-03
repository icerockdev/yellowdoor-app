/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.app

import dev.icerock.moko.widgets.screen.BaseApplication
import dev.icerock.moko.widgets.screen.HostActivity

class MainActivity : HostActivity() {
    override val application: BaseApplication
        get() {
            return MainApplication.mppApplication
        }
}
