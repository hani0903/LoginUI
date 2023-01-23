package com.metacoding.loginui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.metacoding.loginui.databinding.ActivitySignUpBinding
import com.metacoding.loginui.request.MemberSignUpRequestDto
import com.metacoding.loginui.response.SignUpResultDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class SignUpActivity : AppCompatActivity() {

    //바인딩 선언
    lateinit var binding: ActivitySignUpBinding

    var memberId: String = ""
    var password: String = ""
    var name: String = ""
    var email: String = ""
    var age: Int = 0
    var role: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //1. retrofit 만들기
        val retrofit = Retrofit.Builder()
            .baseUrl(URL("https://eg-todo.inuappcenter.kr"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. retrofit 서비스 등록하기
        val retrofitService = retrofit.create(RetrofitService::class.java)

        //뷰 바인딩 초기화
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //id가 입력된 경우
        binding.idInput.doAfterTextChanged {
            memberId = it.toString()
        }

        binding.pwInput.doAfterTextChanged {
            password = it.toString()
        }

        binding.nameInput.doAfterTextChanged {
            name = it.toString()
        }

        binding.emailInput.doAfterTextChanged {
            email = it.toString()
        }

        binding.ageInput.doAfterTextChanged {
            age = it.toString().toInt()
        }

        binding.roleInput.doAfterTextChanged {
            role = it.toString()
        }

        //취소 버튼이 눌린 경우
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        //가입 버튼이 눌린 경우
        binding.signUpBtn.setOnClickListener {

            val member = MemberSignUpRequestDto(memberId, password, name, email, age, role)

            retrofitService.todoSignUp(member).enqueue(object : Callback<SignUpResultDto> {

                override fun onResponse(
                    call: Call<SignUpResultDto>,
                    response: Response<SignUpResultDto>
                ) {
                    if (response.isSuccessful) {
                        val token = response.body()!!
                        Log.d("retrofit", token.msg.toString())


                    }
                }

                override fun onFailure(call: Call<SignUpResultDto>, t: Throwable) {
                }
            })


        }
    }
}