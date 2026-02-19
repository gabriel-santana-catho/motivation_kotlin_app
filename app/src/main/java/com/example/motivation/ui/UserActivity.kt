package com.example.motivation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motivation.helpers.MotivationConstants
import com.example.motivation.R
import com.example.motivation.databinding.ActivityUserBinding
import com.example.motivation.repository.SecurePreference

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var securityPreference: SecurePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        securityPreference = SecurePreference(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        verifyUserName()
    }

    override fun onClick(v: View) {
        if(v.id == R.id.button_save_name) {
            handleSaveButton()
        }
    }

    private fun handleSaveButton() {
        val name = binding.edittextName.text.toString()

        if (name.isEmpty()) {
            Toast.makeText(this, "Informe seu nome!", Toast.LENGTH_SHORT).show()
        } else {
            securityPreference.storeString(MotivationConstants.KEY.PERSON_NAME, name)
            navigateToMain()
        }
    }

    private fun setListeners() {
        binding.buttonSaveName.setOnClickListener(this)
    }

    private fun verifyUserName() {
        val name = securityPreference.getString(MotivationConstants.KEY.PERSON_NAME)

        if(name.isNotEmpty()){
            navigateToMain()
        } else {
            return;
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}