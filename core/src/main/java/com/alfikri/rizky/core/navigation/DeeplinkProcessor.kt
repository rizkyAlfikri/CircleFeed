package com.alfikri.rizky.core.navigation

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DeeplinkProcessor, v 0.1 12/25/2022 5:31 AM by Rizky Alfikri Rachmat
 */
interface DeeplinkProcessor {
    fun matches(deeplink: String): Boolean

    fun execute(deeplink: String)
}