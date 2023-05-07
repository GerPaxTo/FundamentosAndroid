package com.example.fundamentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fundamentos.databinding.FragmentFightBinding
import com.squareup.picasso.Picasso
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt


class FightFragment(): Fragment() {
    private lateinit var binding: FragmentFightBinding

    private val aplicationActivityViewModel: AplicationActivityViewModel by viewModels()
    private val viewModel: ViewModelFightFragment by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFightBinding.inflate(inflater)

        Picasso.get().load(aplicationActivityViewModel.photo.toString()).into(binding.imageHero)

        // Gets the data from the passed bundle
        val bundle = arguments
        val name = bundle!!.getString("mText")
        val photo = bundle!!.getString("photo")
        val damage = bundle!!.getInt("damage")
        val vida = bundle!!.getInt("vida")

        binding.tvHero.text = name
        binding.pbDamage.progress = damage
        binding.pbVida.progress = vida
        Picasso.get().load(photo).into(binding.imageHero)

        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btFight.setOnClickListener {
            val damage = nextInt(60 - 10 +1 )

            binding.pbDamage.progress += damage
            binding.pbVida.progress -=  damage

        }

        binding.btLive.setOnClickListener {
            binding.pbDamage.progress -= 20
            binding.pbVida.progress +=  20

        }

        binding.bnBack.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
    }


}