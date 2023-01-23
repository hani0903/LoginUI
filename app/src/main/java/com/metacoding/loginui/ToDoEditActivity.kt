package com.metacoding.loginui

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doAfterTextChanged
import com.metacoding.loginui.databinding.ActivityToDoEditBinding
import com.metacoding.loginui.request.TodoCreateRequestDto
import com.metacoding.loginui.response.TodoCreateResultDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ToDoEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoEditBinding
    var title = ""
    var content = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //1. retrofit 만들기
        val retrofit = Retrofit.Builder()
            .baseUrl(URL("https://eg-todo.inuappcenter.kr"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //2. retrofit 서비스 등록하기
        val retrofitService = retrofit.create(RetrofitService::class.java)

        binding.todoEditText.doAfterTextChanged {
            title = binding.todoEditText.text.toString()
        }

        binding.deadlineEditText.doAfterTextChanged {
            content = binding.deadlineEditText.text.toString()
        }

        binding.dateLayer.setOnClickListener {
            //날짜를 선택할 수 있는 다이얼로그 추가하기 -DatePickerDialog()
            //1. listener 선언 (date는 안써서 _로 대체!)
            val listner = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                binding.deadlineEditText.text = "$year-${month.inc()}-$dayOfMonth"
            }
            DatePickerDialog(
                this,
                listner,
                2000,
                1,
                1
            ).show()
            //show를 안하면 그냥 data picker만 만들어놓은게 된다.
        }

        binding.okBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)

            val body = TodoCreateRequestDto(title, content)

            //token 넣어주기 -> 이 부분은 맞는지는 몰겠음
//            val header = hashMapOf<String, String>()
//            val sharedPreferences = this.getSharedPreferences(
//                "user_info",
//                Context.MODE_PRIVATE
//            )
            val token = sharedPreferences.getString("token", "").toString()

            retrofitService.makeToDo(body).enqueue(object : Callback<TodoCreateResultDto> {
                override fun onResponse(
                    call: Call<TodoCreateResultDto>,
                    response: Response<TodoCreateResultDto>
                ) {
                    Log.d("hello", response.code().toString())
                    Log.d("hello", token)
                }

                override fun onFailure(call: Call<TodoCreateResultDto>, t: Throwable) {

                }
            })
        }
    }
}