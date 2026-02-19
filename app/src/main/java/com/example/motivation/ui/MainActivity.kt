package com.example.motivation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.motivation.helpers.MotivationConstants
import com.example.motivation.repository.PhraseRepository
import com.example.motivation.R
import com.example.motivation.databinding.ActivityMainBinding
import com.example.motivation.repository.SecurePreference

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var securityPreference: SecurePreference
    private val phraseRepository = PhraseRepository()
    private var filter : Int = MotivationConstants.PHRASEFILTER.ALL


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        securityPreference = SecurePreference(this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setListeners()
        getUserName()

        handleFilter(R.id.image_all)
        refreshPhrase()
    }

    override fun onClick(v: View) {

        val listId = listOf(
            R.id.image_all, R.id.image_happy, R.id.image_sunny
        )

        if(v.id == R.id.button_new_phrase) {
            refreshPhrase()
        } else if (v.id in listId) {
            handleFilter(v.id)
        }
    }

    private fun refreshPhrase(){
        binding.textviewPhase.text = phraseRepository.getPhrases(filter)
    }

    private fun handleFilter(id: Int){

        // Resetar todos para a cor padrão (ex: branco)
        binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.black))
        binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.black))

        when (id) {
            R.id.image_all -> {
                filter = MotivationConstants.PHRASEFILTER.ALL
                binding.imageAll.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }

            R.id.image_happy -> {
                filter = MotivationConstants.PHRASEFILTER.HAPPY
                binding.imageHappy.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }

            R.id.image_sunny -> {
                filter = MotivationConstants.PHRASEFILTER.SUNNY
                binding.imageSunny.setColorFilter(ContextCompat.getColor(this, R.color.white))
            }
        }

        refreshPhrase()
    }



    private fun getUserName() {
       val name = securityPreference.getString(MotivationConstants.KEY.PERSON_NAME)
        println(name)
        binding.textviewName.text = name
    }
    private fun setListeners() {
        binding.buttonNewPhrase.setOnClickListener(this)
        binding.imageAll.setOnClickListener(this)
        binding.imageHappy.setOnClickListener(this)
        binding.imageSunny.setOnClickListener(this)
    }

}