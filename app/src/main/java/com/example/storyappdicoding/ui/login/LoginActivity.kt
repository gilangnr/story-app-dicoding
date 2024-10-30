package com.example.storyappdicoding.ui.login

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
import com.example.storyappdicoding.databinding.ActivityLoginBinding
import com.example.storyappdicoding.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playAnimation()
        binding.toRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun playAnimation() {
        val img = ObjectAnimator.ofFloat(binding.imgLogin, View.ALPHA, 1f).setDuration(1000)
        val desc = ObjectAnimator.ofFloat(binding.msgLogin, View.ALPHA, 1f).setDuration(1000)

        val txtEmail = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(1000)
        val inputEmail = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editEmail = ObjectAnimator.ofFloat(binding.emailEditText, View.ALPHA, 1f).setDuration(1000)

        val txtPassword = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(1000)
        val inputPassword = ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editPassword = ObjectAnimator.ofFloat(binding.passwordEditText, View.ALPHA, 1f).setDuration(1000)

        val btn = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(1000)
        val toReg = ObjectAnimator.ofFloat(binding.toRegister, View.ALPHA, 1f).setDuration(1000)

        val emailGroup = AnimatorSet().apply {
            playTogether(txtEmail, inputEmail, editEmail)
        }

        val passwordGroup = AnimatorSet().apply {
            playTogether(txtPassword, inputPassword, editPassword)
        }

        AnimatorSet().apply {
            playSequentially(img, desc, emailGroup, passwordGroup, btn, toReg)
            start()
        }
    }

}