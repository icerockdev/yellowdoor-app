/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

object Versions {
    object Android {
        const val compileSdk = 28
        const val targetSdk = 28
        const val minSdk = 16
    }

    const val kotlin = "1.3.61"

    private const val mokoResources = "0.7.0"
    private const val mokoNetwork = "0.3.0"
    private const val mokoUnits = "0.2.2"
    private const val mokoWidgets = "0.1.0-dev-10"

    object Plugins {
        const val kotlin = Versions.kotlin
        const val serialization = Versions.kotlin
        const val androidExtensions = Versions.kotlin
        const val mokoResources = Versions.mokoResources
        const val mokoNetwork = Versions.mokoNetwork
        const val mokoUnits = Versions.mokoUnits
        const val mokoWidgets = Versions.mokoWidgets
    }

    object Libs {
        object Android {
            const val kotlinStdLib = Versions.kotlin
            const val appCompat = "1.1.0"
            const val material = "1.0.0"
            const val constraintLayout = "1.1.3"
            const val lifecycle = "2.0.0"
            const val recyclerView = "1.0.0"
        }

        object MultiPlatform {
            const val kotlinStdLib = Versions.kotlin

            const val coroutines = "1.3.3"
            const val serialization = "0.14.0"
            const val ktorClient = "1.2.6"
            const val ktorClientLogging = ktorClient

            const val mokoParcelize = "0.2.0"
            const val mokoTime = "0.2.0"
            const val mokoGraphics = "0.2.0"
            const val mokoMvvm = "0.4.0"
            const val mokoResources = Versions.mokoResources
            const val mokoNetwork = Versions.mokoNetwork
            const val mokoFields = "0.2.0"
            const val mokoPermissions = "0.4.0"
            const val mokoMedia = "0.2.0"
            const val mokoUnits = Versions.mokoUnits
            const val mokoWidgets = Versions.mokoWidgets

            const val napier = "1.1.0"
            const val settings = "0.5"
        }
    }
}
