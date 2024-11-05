    package com.example.storyappdicoding.ui.add_story

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyappdicoding.R
import com.example.storyappdicoding.data.Result
import com.example.storyappdicoding.databinding.ActivityAddStoryBinding
import com.example.storyappdicoding.pref.SessionManager
import com.example.storyappdicoding.ui.main.MainActivity
import com.example.storyappdicoding.utils.getImageUri
import com.example.storyappdicoding.utils.reduceFileImage
import com.example.storyappdicoding.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

    class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding
    private lateinit var sessionManager: SessionManager
    private val viewModel: AddStoryViewModel by viewModels {
        AddStoryFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sessionManager = SessionManager(this)
        val token = sessionManager.getAuthToken().toString()

        observeViewModel()

        binding.btnGallery.setOnClickListener { startGallery() }
        binding.btnCamera.setOnClickListener { startCamera() }
        binding.buttonAdd.setOnClickListener { uploadImage(token) }

    }

        private fun observeViewModel() {
            viewModel.isLoading.observe(this) { isLoading ->
                showLoading(isLoading)
            }
            viewModel.uploadResult.observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        Toast.makeText(this, result.data.message, Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        private fun showLoading(isLoading: Boolean) {
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        private fun uploadImage(token: String) {
            val description = binding.edAddDescription.text.toString().trim()

            if (description.isEmpty()) {
                Toast.makeText(this, "Please add description", Toast.LENGTH_SHORT).show()
                return
            }

            if (currentImageUri == null) {
                Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show()
                return
            }

            val file = uriToFile(currentImageUri!!, this).reduceFileImage()

            val descriptionRequestBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            viewModel.uploadStory(
                token,
                descriptionRequestBody,
                multipartBody,
                null,
                null
            )

        }


        private fun startGallery() {
            launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        private val launcherGallery = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                Toast.makeText(this, getString(R.string.no_media_selected), Toast.LENGTH_SHORT).show()
            }
        }

        private fun startCamera() {
            currentImageUri = getImageUri(this)
            launcherIntentCamera.launch(currentImageUri!!)
        }
        private val launcherIntentCamera = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                showImage()
            } else {
                currentImageUri = null
            }
        }


        private fun showImage() {
            currentImageUri?.let {
                binding.imgPreview.setImageURI(it)
            }
        }

    }