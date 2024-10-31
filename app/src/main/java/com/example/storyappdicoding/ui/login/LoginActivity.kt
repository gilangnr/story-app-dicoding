package com.example.storyappdicoding.ui.login

import android.animation.Animator
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
        binding.apply {
            imgLogin.visibility = View.VISIBLE
            msgLogin.visibility = View.VISIBLE
            emailTextView.visibility = View.VISIBLE
            emailEditTextLayout.visibility = View.VISIBLE
            edLoginEmail.visibility = View.VISIBLE
            passwordTextView.visibility = View.VISIBLE
            PasswordEditTextLayout.visibility = View.VISIBLE
            edLoginPassword.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            toRegister.visibility = View.VISIBLE
        }

        val img = ObjectAnimator.ofFloat(binding.imgLogin, View.ALPHA, 1f).setDuration(1000)
        val desc = ObjectAnimator.ofFloat(binding.msgLogin, View.ALPHA, 1f).setDuration(1000)

        val txtEmail =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(1000)
        val inputEmail =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editEmail =
            ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(1000)

        val txtPassword =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(1000)
        val inputPassword =
            ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f).setDuration(1000)
        val editPassword =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(1000)

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
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.edLoginEmail.isClickable = true
                    binding.edLoginEmail.isFocusableInTouchMode = true
                    binding.edLoginPassword.isClickable = true
                    binding.edLoginPassword.isFocusableInTouchMode = true
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }

            })
            start()
        }
    }


}