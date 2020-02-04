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
        }
    }
}