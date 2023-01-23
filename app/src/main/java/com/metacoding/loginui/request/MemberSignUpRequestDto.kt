package com.metacoding.loginui.request

import android.provider.ContactsContract.CommonDataKinds.Email

data class MemberSignUpRequestDto (
    val memberId : String,
    val password : String,
    val name : String,
    val email: String,
    val age : Int,
    val role : String
)