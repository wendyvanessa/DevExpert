package com.example.devexpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.devexpert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MediaAdapter(getItems())

        binding.recycler.setOnClickListener {
            toast("Hello this is function of extension")
        }

       // this.startActivity<MainActivity>()

    }
}