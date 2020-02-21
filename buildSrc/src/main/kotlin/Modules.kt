object Modules {
    object MultiPlatform {
        val domain: MultiPlatformModule = MultiPlatformModule(
            name = ":mpp-library:domain",
            exported = true
        )

        object Feature {
            val auth: MultiPlatformModule = MultiPlatformModule(
                name = ":mpp-library:feature:auth",
                exported = true
            )
            val forgotPassword: MultiPlatformModule = MultiPlatformModule(
                name = ":mpp-library:feature:forgot-password",
                exported = true
            )
            val profile: MultiPlatformModule = MultiPlatformModule(
                name = ":mpp-library:feature:profile",
                exported = true
            )
            val selection: MultiPlatformModule = MultiPlatformModule(
                name = ":mpp-library:feature:selection",
                exported = true
            )
        }
    }
}