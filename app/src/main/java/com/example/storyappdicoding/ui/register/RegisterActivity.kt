package com.example.storyappdicoding.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyappdicoding.R
import com.example.storyappdicoding.databinding.ActivityRegisterBinding
import com.example.storyappdicoding.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playAnimation()
        binding.toLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun playAnimation() {
        val img = ObjectAnimator.ofFloat(binding.imgRegister, View.ALPHA, 1f).setDuration(1000)
        val desc = ObjectAnimator.ofFloat(binding.txtDescRegister, View.ALPHA, 1f).setDuration(1000)

        val txtName = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(1000)
        val inputName =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editName =
            ObjectAnimator.ofFloat(binding.nameEditText, View.ALPHA, 1f).setDuration(1000)

        val txtEmail =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(1000)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editEmail =
            ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(1000)

        val txtPassword =
            ObjectAnimator.ofFloat(binding.txtPassword, View.ALPHA, 1f).setDuration(1000)
        val inputPassword =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editPassword =
            ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(1000)

        val btn = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(1000)
        val toLogin = ObjectAnimator.ofFloat(binding.toLogin, View.ALPHA, 1f).setDuration(1000)

        val togetherName = AnimatorSet().apply {
            playTogether(txtName, inputName, editName)
        }

        val togetherEmail = AnimatorSet().apply {
            playTogether(txtEmail, inputEmail, editEmail)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(txtPassword, inputPassword, editPassword)
        }

        AnimatorSet().apply {
            playSequentially(img, desc, togetherName, togetherEmail, togetherPassword, btn, toLogin)
            start()
        }
    }
}