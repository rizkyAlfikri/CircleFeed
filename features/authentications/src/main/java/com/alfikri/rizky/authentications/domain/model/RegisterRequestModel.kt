package com.alfikri.rizky.authentications.domain.model

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version RegisterRequestModel, v 0.1 12/26/2022 10:23 AM by Rizky Alfikri Rachmat
 */
data class RegisterRequestModel(
    val name: String,
    val email: String,
    val password: String
)
