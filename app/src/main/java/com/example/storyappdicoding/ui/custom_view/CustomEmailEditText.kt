package com.example.storyappdicoding.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomEmailEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputEditText(context, attrs, defStyleAttr){

    private var isInitialized = false

}