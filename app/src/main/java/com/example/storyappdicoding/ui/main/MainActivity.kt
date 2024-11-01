package com.example.storyappdicoding.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappdicoding.R
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.databinding.ActivityMainBinding
import com.example.storyappdicoding.pref.SessionManager
import com.example.storyappdicoding.ui.detail.DetailActivity
import com.example.storyappdicoding.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: MainAdapter
    private val mainViewModel: MainViewModel by viewModels {
        MainFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        sessionManager = SessionManager(this)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkAuthentication()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MainAdapter { story ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("STORY_ID", story.id)
            }
            startActivity(intent)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@MainActivity.adapter
        }
    }


    private fun checkAuthentication() {
        val token = sessionManager.getAuthToken()
        if (token == null) {
            navigateToLogin()
        } else {
            mainViewModel.getAllStories(token)
        }
    }

    private fun observeViewModel() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        mainViewModel.stories.observe(this) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    adapter.submitList(result.data)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun logout() {
        sessionManager.clearAuthToken()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
