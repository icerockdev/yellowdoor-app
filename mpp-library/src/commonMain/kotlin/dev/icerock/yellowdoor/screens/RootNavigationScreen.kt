/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.yellowdoor.screens

import dev.icerock.moko.widgets.screen.Args
import dev.icerock.moko.widgets.screen.Screen
import dev.icerock.moko.widgets.screen.TypedScreenDesc
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.NavigationScreen

class RootNavigationScreen<T>(
    initialScreen: TypedScreenDesc<Args.Empty, T>,
    router: Router
) : NavigationScreen<T>(initialScreen, router) where T : Screen<Args.Empty>, T : NavigationItem
