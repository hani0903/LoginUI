package com.metacoding.loginui

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class MyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var original : Request
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                original = chain.request()
                if (original.url.encodedPath.equals("members/sign-in", true)
                    || original.url.encodedPath.equals("members/sign-up", true)
                // || original.url.encodedPath.equals("/users/login", true)
                ) {
                    chain.proceed(original)
                } else {

                    chain.proceed(original.newBuilder().apply {
                        addHeader("X-Auth-Token", "application/json")
                        addHeader("Content-Type", TOKEN_VAL)
                        addHeader("accept", "*/*")
                    }.build())
                }
            }.build()
        return chain.proceed(original)
    }

}

