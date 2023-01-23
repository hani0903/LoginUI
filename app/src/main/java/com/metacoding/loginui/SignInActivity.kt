package com.metacoding.loginui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.metacoding.loginui.databinding.ActivitySignInBinding
import com.metacoding.loginui.request.MemberSignInRequestDto
import com.metacoding.loginui.response.SignInResultDto
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class SignInActivity : AppCompatActivity() {

    //바인딩 선언
    lateinit var binding: ActivitySignInBinding

    var username: String = ""
    var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        //1. retrofit 만들기
        val retrofit = Retrofit.Builder()
            .baseUrl(URL("https://eg-todo.inuappcenter.kr"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. retrofit 서비스 등록하기
        val retrofitService = retrofit.create(RetrofitService::class.java)


        //binding init
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //id가 입력된 경우
        binding.idInput.doAfterTextChanged {
            username = it.toString()
        }

        //pw가 입력된 경우
        binding.pwInput.doAfterTextChanged {
            password = it.toString()
        }

        /* 로그인 버튼이 눌린 경우 */
        binding.loginBtn.setOnClickListener {

            var member = MemberSignInRequestDto(username, password)
            //인텐트 생성
            val intent = Intent(this, ToDoEditActivity::class.java)  // 인텐트를 생성해줌

            retrofitService.todoLogin(member).enqueue(object : Callback<SignInResultDto> {

                override fun onResponse(
                    call: Call<SignInResultDto>,
                    response: Response<SignInResultDto>
                ) {
                    //성공한 경우 -> 토큰 받아올 수 있음
                    if (response.isSuccessful) {
                        val signInResponse = response.body()!!
                        Log.d("retrofit", signInResponse.msg.toString())

                        //로그인한 경우 토큰을 shared preference에 저장하기
                        val sharedPreferences =
                            getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString(
                            TOKEN,
                            signInResponse.token
                        )  //token이라는 속성만 shared preference에 키를 'token'으로 저장

                        TOKEN_VAL = signInResponse.token
                        editor.commit()
                        startActivity(intent)

                    }

                }

                override fun onFailure(call: Call<SignInResultDto>, t: Throwable) {

                }
            })
        }

        /*가입 버튼*/
        binding.signUpBtn.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))

        }

    }


}