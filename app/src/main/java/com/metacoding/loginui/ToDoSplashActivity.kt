package com.metacoding.loginui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

/* 앱이 켜지자마자 나와서 확인해야 한다. */
class ToDoSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_splash)

        /* shared preference 불러오기 */
        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "empty")

        Log.d("todo",token!!)
        when (token) {
            "empty" -> {
                //로그인이 되어있지 않은 경우
                startActivity(Intent(this, SignInActivity::class.java))
            }
            else -> {
                //로그인이 되어있는 경우 ( 토큰이 있는 경우)
                startActivity(Intent(this, ToDoListActivity::class.java))
            }
        }
    }
}