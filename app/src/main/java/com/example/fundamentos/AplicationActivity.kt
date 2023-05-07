package com.example.fundamentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fundamentos.databinding.ActivityAplicationBinding
import kotlinx.coroutines.launch

class AplicationActivity() : AppCompatActivity(), HeroClicked {
    private val internetViewModel: InternetViewModel by viewModels()
    private val aplicationActivityViewModel: AplicationActivityViewModel by viewModels()

    private lateinit var binding: ActivityAplicationBinding

    private var listaHeroes = listOf<Hero>()

    companion object {

        const val TOKEN = "TOKEN"

        fun launch(context: Context, token: String) {
            val intent = Intent(context, AplicationActivity::class.java)
            intent.putExtra(TOKEN, token)

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent.getStringExtra(TOKEN)
        lifecycleScope.launch {
            internetViewModel.downloadHeros(token)
            internetViewModel.uiState.collect {
                when (it) {
                    is InternetViewModel.UiState.OnHeroReceived ->{
                        listaHeroes = it.heroes
                        iniciaAdapter(listaHeroes)
                    }

                    else ->
                        Unit
                }
            }
        }
    }

    private fun iniciaAdapter(listaHeroes: List<Hero>) {
        val adapter = AplicationActivityAdapter(listaHeroes, this)
        binding.rvListaHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvListaHeroes.adapter = adapter
    }

    override fun heroClicked(hero: Hero) {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = FightFragment()
        val mBundle = Bundle()
        mBundle.putString("mText",hero.name)
        mBundle.putString("photo",hero.photo)
        mBundle.putInt("vida",hero.vida)
        mBundle.putInt("damage",hero.damage)
        mFragment.arguments = mBundle
        mFragmentTransaction.add(binding.fFragment.id, mFragment).commit()
    }
}
