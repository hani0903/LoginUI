package com.metacoding.loginui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.metacoding.loginui.databinding.ActivityToDoBinding

class ToDoListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityToDoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.write.setOnClickListener {
            startActivity(Intent(this,ToDoEditActivity::class.java))
        }
    }
}