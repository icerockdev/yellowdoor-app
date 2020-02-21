/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)

    dexOptions {
        javaMaxHeapSize = "2g"
    }

    defaultConfig {
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)

        applicationId = "dev.icerock.yellowdoor"

        versionCode = 1
        versionName = "0.1.0"

        multiDexEnabled = true

        vectorDrawables.useSupportLibrary = true

        val url = "https://newsapi.org/v2/"
        buildConfigField("String", "BASE_URL", "\"$url\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}

dependencies {
    implementation(Deps.Libs.Android.kotlinStdLib.name)

    implementation(Deps.Libs.Android.appCompat.name)
    implementation(Deps.Libs.Android.material.name)
    implementation(Deps.Libs.Android.recyclerView.name)
    implementation(Deps.Libs.Android.multidex.name)

    implementation(Deps.Libs.MultiPlatform.napier.android!!)

    implementation(project(":mpp-library"))
}
