package com.alfikri.rizky.core.navigation

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DeeplinkHandler, v 0.1 12/25/2022 5:31 AM by Rizky Alfikri Rachmat
 */
interface DeeplinkHandler {
    fun process(deeplink: String): Boolean
}