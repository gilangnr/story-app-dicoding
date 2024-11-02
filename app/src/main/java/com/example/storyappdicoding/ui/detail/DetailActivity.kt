package com.example.storyappdicoding.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyappdicoding.R
import com.example.storyappdicoding.databinding.ActivityDetailBinding
import com.example.storyappdicoding.pref.SessionManager

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val storyId = intent.getStringExtra("STORY_ID")
        val token = sessionManager.getAuthToken()
        binding.storyId.text = "your id is $storyId"
    }
}