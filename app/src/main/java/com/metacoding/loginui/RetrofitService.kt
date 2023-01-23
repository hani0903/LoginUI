package com.metacoding.loginui

import android.util.Xml
import androidx.compose.ui.input.key.Key.Companion.H
import com.metacoding.loginui.request.MemberSignInRequestDto
import com.metacoding.loginui.request.MemberSignUpRequestDto
import com.metacoding.loginui.request.TodoCreateRequestDto
import com.metacoding.loginui.response.SignInResultDto
import com.metacoding.loginui.response.SignUpResultDto
import com.metacoding.loginui.response.TodoCreateResultDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.http.*

//API 만들기
interface RetrofitService {

    //@Headers("Content-Type: application/json")
    /*로그인*/
    @POST("members/sign-in")
    fun todoLogin(
        @Body params: MemberSignInRequestDto
    ): Call<SignInResultDto>

    /*회원가입*/
    @POST("members/sign-up")
    fun todoSignUp(
        @Body params: MemberSignUpRequestDto
    ): Call<SignUpResultDto>

    /*todo 추가*/
    @POST("todos")
    fun makeToDo(
       // @Header("X-AUTH-TOKEN") accessToken: String,//내가 추가
        //@Header("authorization") accessToken: String,//내가 추가
        //@HeaderMap headers : Map<String,String>,
        @Body params: TodoCreateRequestDto
    ): Call<TodoCreateResultDto>
}


