package com.alfikri.rizky.core.navigation

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DefaultDeepLinkHandler, v 0.1 12/25/2022 5:31 AM by Rizky Alfikri Rachmat
 */
class DefaultDeepLinkHandler constructor(
    private val processor: Set<@JvmSuppressWildcards DeeplinkProcessor>
): DeeplinkHandler{

    override fun process(deeplink: String): Boolean {
        processor.forEach {
            if (it.matches(deeplink)) {
                it.execute(deeplink)
                return true
            }
        }

        return false
    }
}