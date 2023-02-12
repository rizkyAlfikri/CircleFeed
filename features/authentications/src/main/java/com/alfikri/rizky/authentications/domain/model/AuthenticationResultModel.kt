package com.alfikri.rizky.authentications.domain.model

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version AuthenticationResultModel, v 0.1 12/26/2022 10:18 AM by Rizky Alfikri Rachmat
 */
data class AuthenticationResultModel <T> (
    var error: Boolean? = null,
    var message: String? = null,
    var data: T? = null
)