package com.metacoding.loginui.response

import com.google.gson.annotations.SerializedName

class SignInResultDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("token")
    val token: String
)