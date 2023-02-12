package com.alfikri.rizky.authentications.presentation.deeplink

import android.content.Context
import android.content.Intent
import com.alfikri.rizky.authentications.presentation.AuthenticationActivity
import com.alfikri.rizky.core.constant.DeeplinkConstant
import com.alfikri.rizky.core.navigation.DeeplinkProcessor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationDeeplinkProcessor, v 0.1 12/25/2022 5:53 AM by Rizky Alfikri Rachmat
 */
@Singleton
class AuthenticationDeeplinkProcessor @Inject constructor(
    private val context: Context
): DeeplinkProcessor {

    override fun matches(deeplink: String): Boolean {
        return deeplink.contains(DeeplinkConstant.AUTHENTICATION_DEEPLINK)
    }

    override fun execute(deeplink: String) {
        val intent = Intent(context, AuthenticationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}