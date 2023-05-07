package com.example.fundamentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.fundamentos.databinding.FragmentFightBinding
import com.squareup.picasso.Picasso


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
        binding.tvHero.text = aplicationActivityViewModel.name.toString()
        Picasso.get().load(aplicationActivityViewModel.photo.toString()).into(binding.imageHero)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}