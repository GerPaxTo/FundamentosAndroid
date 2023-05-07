package com.example.fundamentos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fundamentos.databinding.ItemHeroeBinding
import com.squareup.picasso.Picasso

interface HeroClicked {
    fun heroClicked(hero: Hero)
}

class AplicationActivityAdapter(
                private val listaHeroes: List<Hero>,
                private val callback: HeroClicked,
                ): RecyclerView.Adapter<AplicationActivityAdapter.ApAcViewHolder>() {

    class ApAcViewHolder(private var item: ItemHeroeBinding, private val callback: HeroClicked) : RecyclerView.ViewHolder(item.root) {

        fun showHeroe(hero: Hero) {
            item.tvNombre.text = hero.name
            item.pbVida.progress = hero.vida
            item.pbDamage.progress= hero.damage
            Picasso.get().load(hero.photo).into(item.imageHero)

            item.lBackground.setOnClickListener {
                Toast.makeText(item.root.context, "Pulsado sobre ${hero.name}", Toast.LENGTH_LONG).show()
                callback.heroClicked (hero)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApAcViewHolder {
        val binding = ItemHeroeBinding.inflate(
            LayoutInflater.from(parent.context)
            , parent
            , false
        )
        return ApAcViewHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return listaHeroes.size
    }

    override fun onBindViewHolder(holder: ApAcViewHolder, position: Int) {
        holder.showHeroe(listaHeroes[position])
    }
}