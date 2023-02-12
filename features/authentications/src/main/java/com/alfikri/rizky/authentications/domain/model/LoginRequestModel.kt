package com.alfikri.rizky.authentications.domain.model

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version LoginRequestModel, v 0.1 12/26/2022 10:22 AM by Rizky Alfikri Rachmat
 */
data class LoginRequestModel(
    val email: String,
    val password: String
)