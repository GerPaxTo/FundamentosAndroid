package com.example.fundamentos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.fundamentos.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var email: String = ""
    var password: String = ""
    var token: String = ""
    var listaHeroes = listOf<Hero>()

    private val internetViewModel: InternetViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       token = loadFromPreferences() ?: ""
       internetViewModel.token = token

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (token.isEmpty()) {
            binding.btLogin.setOnClickListener {
                email = binding.txUser.text.toString()
                password = binding.txPassword.text.toString()

                lifecycleScope.launch {
                    internetViewModel.logIn(email.toString(), password.toString())
                    internetViewModel.uiState.collect {
                        when (it) {
                            is InternetViewModel.UiState.OnTokenReceived -> {
                                token = it.text
                                saveFromPreferences(token)
                                internetViewModel.token = token
                                muestraHeroes(token)
                            }

                            is InternetViewModel.UiState.OnHeroReceived ->
                                listaHeroes = it.heroes

                            is InternetViewModel.UiState.Error ->
                                token = ""

                            else ->
                                Unit
                        }
                    }
                }
            }
        } else {
            internetViewModel.token = token
            muestraHeroes(token)
        }

    }

    fun muestraHeroes(token: String){
        AplicationActivity.launch(this, token)
    }

    private fun loadFromPreferences(): String? {
        getPreferences(Context.MODE_PRIVATE).apply {
            return  getString("Token", "")
        }
    }

    private fun saveFromPreferences(token: String) {
        getPreferences((Context.MODE_PRIVATE)).edit().apply {
            putString("Token", token)
            apply()
        }
    }
}